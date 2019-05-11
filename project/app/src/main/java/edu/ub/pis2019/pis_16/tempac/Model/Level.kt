package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.util.Log
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameEngine
import java.util.*

//clase colisionable (los objetos con los que chocas i no pasa nada) i class no colisionable (los objetos no colisionables que no pasa nada cuando xocan.)
class Level(blockImg : List<Bitmap>) : Drawable {
    companion object{
        const val MAX_ORBS = 4
        const val MAX_BLOCKS = 50
        const val MAX_LINES = 0
    }
    //ORBS
    var ofactory : OrbFactory = OrbFactory()
    var orbs : MutableList<Orb> = mutableListOf<Orb>()
    var orbInLastLine : Boolean = false
    var temperature = 0f

    //MATRIX
    //This is tha matrix of all the blocks
    var filasA : MutableList<Array<Block?>>
    //This hashmap relates every array (line of blocks) with the positionY of the blocks
    val positionYArray= hashMapOf<Array<Block?>,Float>()
    var nBlocksInLine: Int = 0
    var nLinesToDraw : Int=0

    //PROBABILITIES OF THE BLOCK GENERATION ALGORITHM
    //Probability that each place in the first line has a block
    var probPrimeros : Float=0.3F
    //Probability that one HOLE is created afterwards the obligatory HOLES are created
    var probRandomHole : Float=0.3F
    //Probability that ONE line is repeated
    var probRepeatLine : Float=0.5F
    //Probability that one block is breakable
    var probBreakable : Float= 0.3F

    var r : Random=Random()
    var blockImages : List<Bitmap>

    init{
        filasA= mutableListOf<Array<Block?>>()

        blockImages=blockImg
        nBlocksInLine= GameEngine.PLAYFIELD_WIDTH.div(Block.blockSide).toInt()
        //nLinesToDraw = GameEngine.PLAYFIELD_HEIGTH.div(Block.blockSide).toInt().plus(3)
        nLinesToDraw = (GameEngine.bottomPlayingField-GameEngine.topPlayingField).div(Block.blockSide).toInt()+4
        createLevelBlocks(nBlocksInLine,nLinesToDraw)
    }

    override fun draw(canvas: Canvas?) {
        for (array in filasA){
            for (block in array){
                if (block!=null){
                    block.draw(canvas)
                }
            }
        }
        for (orb:Orb in orbs) { orb.draw(canvas) }
    }
    fun update(scroll : Float){
        for(orb in orbs){orb.update(scroll)}
        for (array in filasA){
            //update the float position Y of array
            updateArrayPositionY(scroll,array)
            for (block in array){
                if (block!=null){
                    block.update(scroll)
                }
            }
        }
        spawnOrbs()

    }
    fun updateArrayPositionY(scroll: Float, array: Array<Block?>){
        //This is the equivalent to the method update, but for the array positionY
        positionYArray.set(array,positionYArray.getValue(array)+scroll)
    }
    fun getLastArray(): Array<Block?> {
        return filasA.get(filasA.lastIndex)
    }
    fun getFirstArray(): Array<Block?>? {
        return filasA.get(0)
    }
    fun getFirstPositiveArray(): Array<Block?>? {
        for (array in filasA){
            if (positionYArray.get(array)!! >=0){
                return array
            }
        }
        return null
    }
    //Given an array of blocks, returns the positionY of that array
    //and the index of the holes in it

    fun getPositionHoles(array: Array<Block?>): Pair<Float?, MutableList<Int>> {
        val positionY:Float?=positionYArray.get(array)
        val indexList= mutableListOf<Int>()
        for (i in 0 until array.size){
            if (array.get(i)==null){
                indexList.add(i)
            }
        }
        return Pair(positionY,indexList)
    }


    fun spawnOrbs(){
        val arrayBlocks=getFirstPositiveArray()
        if(arrayBlocks==null || orbs.size == MAX_ORBS || orbInLastLine){
            return
        }
        val par=getPositionHoles(arrayBlocks)
        val positionY=par.first as Float
        val indexOfHoles=par.second
        //We get any hole of the line.
        var indiceDeLaLista=r.nextInt(indexOfHoles.size)
        var indice=indexOfHoles.get(indiceDeLaLista)

        var newOrb : Orb=ofactory.create(temperature)
        newOrb.setPosition(Block.blockSide.times(indice.plus(0.5f)),positionY)
        for (o in orbs){
            if(RectF.intersects(o.rectangle,newOrb.rectangle)){
                if (indiceDeLaLista==0){ indiceDeLaLista++ }
                else{ indiceDeLaLista-- }
                indice=indexOfHoles.get(indiceDeLaLista)
                newOrb.setPosition(Block.blockSide.times(indice.plus(0.5f)),positionY)
            }
        }
        orbs.add(newOrb)
        orbInLastLine=true
        //TODO FUNCTION TO DECIDE WHERE THE orbs SHOULD SPAWN
        //we could make the function return a Par<Float, Float> and pass each one for parameter or we could change the set position to redive a par.

    }

    fun deleteLine(array : Array<Block?>){
        //First we delete the line
        var block: Block?
        for(i in 0 until array.size){
            block = array.get(i)
            if (block!=null){
                if (block.breakable){
                    deleteBreakableBlock(block)
                }
                array.set(i,null)
            }
        }
        //We remove it from the hasmap positionY
        positionYArray.remove(array)
        //We remove the whole array from filasA
        filasA.remove(array)

        //Then we create a new line on top
        newLineOnTop()
    }
    fun newLineOnTop() {
        val first=getFirstArray()
        val positionY=positionYArray.get(first)as Float
        if(first==null){
            Log.println(Log.VERBOSE,"ERROR", "NULL first array of Blocks")
        }
        var firstBArray= createBooleanArray(first as Array<Block?>)
        //Here we decide if the last line of blocks gets repeted
        if(!(r.nextFloat()<probRepeatLine)){
            //getNewBooleanArray is the method that creates new lines(now is trivial)
            firstBArray = getNewBooleanArray(firstBArray)
        }
        createNewBlockLine(firstBArray,positionY.minus(Block.blockSide))
    }

    fun getNewBooleanArray(booleanArray: BooleanArray): BooleanArray{
        //todo AQUÍ puedo poder el algoritmo para crear lineas y caminos
        val diffLines= mutableListOf<BooleanArray>()
        diffLines.add(0,BooleanArray(booleanArray.size))
        diffLines.add(1,BooleanArray(booleanArray.size))
        for (i in 0 until booleanArray.size){
            diffLines.get(0).set(i,i%2==0)
            diffLines.get(1).set(i,false)
        }
        //return diffLines.get(r.nextInt(2))
        if(r.nextFloat()<0.18f){
            return diffLines.get(1)
        }
        return diffLines.get(0)
    }
    fun createBooleanArray(array: Array<Block?>):BooleanArray{
        val booleanArray : BooleanArray= BooleanArray(array.size)
        for (i in 0 until array.size){
            booleanArray.set(i,!(array.get(i)==null))
        }
        return booleanArray
    }

    fun deleteBreakableBlock(b : Block){
        //todo mirar la manera más eficiente(con array)
        //if breakable block is a son class of block, we can make that it saves its array
        //and the position it has in the array, so that way it goes directly to delete itself
        //instead of running over the whole matrix

        //por ahora un remove cutrillo
        var block : Block?
        for( array in filasA){
            for (i in 0 until array.size){
                block=array.get(i)
                if(block !=null){
                    if (block.equals(b)){
                        array.set(i,null)
                        return
                    }
                }
            }
        }
    }
    fun createLevelBlocks(ancho : Int, alto: Int){
        //createTrivialLevelBlocks(ancho,alto)
        //generateNewLevel(ancho,alto)
        //createArrayLevel(ancho,alto)
        createArrayLevel(ancho,alto)
    }
    fun createArrayLevel(ancho: Int,alto: Int){
        var arrayVacio = BooleanArray(ancho)
        var arrayIntermitenteBool: BooleanArray = BooleanArray(ancho)
        for(i in 0 until ancho){
            arrayVacio.set(i,false)
            arrayIntermitenteBool.set(i,i%2==0)
        }
        for(i in 0 until alto){
            if(i!=alto-1){
                createNewBlockLine(arrayVacio.copyOf(), i)
            }else{
                createNewBlockLine(arrayIntermitenteBool,i)
            }
        }
    }

    fun createNewBlockLine(arrayBooleanos : BooleanArray,posY : Float){
        //first we add this so only one orb spawns in one line
        orbInLastLine=false

        var anchoBloque : Float= Block.blockSide
        var desplazamiento : Float
        val arrayBlocks = arrayOfNulls<Block?>(arrayBooleanos.size)

        for (k in 0 until arrayBooleanos.size){
            if(arrayBooleanos.get(k)){
                //Calcular la posición que hay que pasarle al bloque
                desplazamiento=anchoBloque.times(k).plus(anchoBloque.div(2))
                //Todo, see how we choose breakable blocks
                var b =Block(desplazamiento,posY, r.nextFloat()<probBreakable, blockImages)
                arrayBlocks.set(k,b)
            }
        }
        //We add the array in the fist position of FilasA
        filasA.add(0,arrayBlocks)
        //We introduce the value of the positionY of the array
        positionYArray.put(arrayBlocks,posY)
    }
    fun createNewBlockLine(arrayBooleanos : BooleanArray,indexLine : Int){
        //first we add this so only one orb spawns in one line
        orbInLastLine=false

        var anchoBloque : Float= Block.blockSide
        var desplazamiento : Float
        val positionY : Float=anchoBloque.times(indexLine.times(-1))+(GameEngine.bottomPlayingField+Block.blockSide)
        val arrayBlocks = arrayOfNulls<Block?>(arrayBooleanos.size)
        
        for (k in 0 until arrayBooleanos.size){
            if(arrayBooleanos.get(k)){
                //Calcular la posición que hay que pasarle al bloque
                desplazamiento=anchoBloque.times(k).plus(anchoBloque.div(2))
                //Todo, see how we choose breakable blocks
                var b =Block(desplazamiento,positionY, true, blockImages)
                arrayBlocks.set(k,b)
            }
        }
        //We add the array in the fist position of FilasA
        filasA.add(0,arrayBlocks)
        //We introduce the value of the positionY of the array 
        positionYArray.put(arrayBlocks,positionY)
    }
    //todo adaptar a array
    fun generateNewLevel(ancho: Int, alto: Int){
        var fila: MutableList<Boolean>? = null
        for (i in 0 until alto) {
            fila=generateNewLine(fila, ancho)
            if (fila != null) { createNewBlockLine(fila,i) }
        }
    }
    fun generateNewLine(filaAnterior : MutableList<Boolean>?, ancho : Int): MutableList<Boolean>? {
        var nueva : MutableList<Boolean> = mutableListOf()
        var holes : MutableList<Pair<Int,Int>>

        var prob : Float

        var n: Int
        //Si
        if (filaAnterior == null) {
            for (i in 0 until ancho){
                prob = r.nextFloat()
                if (prob <= probPrimeros) { nueva.add(i, true)}
                else {                      nueva.add(i, false)}
            }
        }
        else{
            holes = getHoles(filaAnterior.toMutableList<Boolean>())
            for (i in 0 until ancho) {nueva.add(i, true)}//inicializamos la fila llena de bloques
            var tupla: Pair<Int,Int>
            var totalhuecos : Int

            //Recorremos la lista y metemos huecos según los huecos de la fila anterior
            for(i in 0 until holes.size){
                tupla = holes.get(i)
                totalhuecos = tupla.second
                //Si el hueco
                if (totalhuecos == 3) {
                    nueva.set(tupla.first, false)
                    nueva.set(tupla.first + (tupla.second.minus(1) ), false)
                } else {
                    n = (r.nextInt(tupla.second.minus(1)))
                    nueva.set((tupla.first + n), false)
                }

            }

            //Ponemos huecos aleatoriamente
            for (i in 0 until ancho){
                if (r.nextFloat()<=probRandomHole){ nueva.set(i,false)}
            }

        }

        return nueva
    }
    fun getHoles(line: MutableList<Boolean>): MutableList<Pair<Int, Int>> {
        var holeList : MutableList<Pair<Int,Int>> = mutableListOf<Pair<Int,Int>>()
        var prev : Boolean = false
        var position : Int=0
        var withHole : Int=0
        var actual : Boolean
        for (i in 0 until line.size){
            actual=line.get(i)

            if (prev){
                // (XX)
                if(actual){}
                // (X_) Empezamos a contar
                else{
                    position=i
                    withHole=0
                }
            }
            else{
                //(_X)
                if (actual){holeList.add(Pair(position, withHole+1)) }
                //(__)
                else{
                    withHole++
                    if(i==line.size-1){holeList.add(Pair(position, withHole+1))}
                }
            }
        }
        return holeList
    }

     fun createNewBlockLine(listaBooleanos : MutableList<Boolean>,indexLine : Int){

    }

}

