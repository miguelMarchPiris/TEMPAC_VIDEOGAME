package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import java.util.*

//clase colisionable (los objetos con los que chocas i no pasa nada) i class no colisionable (los objetos no colisionables que no pasa nada cuando xocan.)
class Level(blockImg : List<Bitmap>) : Drawable {
    companion object{
        const val MAX_ORBS = 4
        const val MAX_BLOCKS = 50
        const val MAX_LINES = 0
    }
    var ofactory : OrbFactory = OrbFactory()
    var orbs : MutableList<Orb> = mutableListOf<Orb>()

    var temperature = 0f

    //TODO hay que ver que hacemos con blocks, pq tiene mas sentido que trabajemos con lines
    //hay que hacer el update de cada fila para saber cuando los bloques estÁN FUERA
    var filasA : MutableList<Array<Block?>>

    //El hashmap está para relacionar cada fila con la posición Y de los bloques de la fila
    val positionYArray= hashMapOf<Array<Block?>,Float>()

    var lines : MutableList <MutableList<Block?>> = mutableListOf<MutableList<Block?>>()
    var blocks : MutableList<Block> = mutableListOf<Block>()
    var breakableBlocks : MutableList<Block?> = mutableListOf<Block?>()
    var r : Random=Random()
    var blockImages : List<Bitmap>

    init{
        filasA= mutableListOf<Array<Block?>>()



        blockImages=blockImg
        var nBlocksInLine: Int= 1080.div(Block.blockSide).toInt()
        var nLinesToDraw : Int = 10
        createLevelBlocks(nBlocksInLine,nLinesToDraw)


        //Instanciamos bloques para hacer pruebas
        //Los bloques tienen un ancho de 80 (se puede modificar en classe block)

        //blocks.add(Block(300f, 300f, false, blockImages))
        //blocks.add(Block(380f, 300f, true, blockImages))


    }

    override fun draw(canvas: Canvas?) {
        for (orb:Orb in orbs) {
            orb.draw(canvas)
        }
        for (array in filasA){
            for (block in array){
                if (block!=null){
                    block.draw(canvas)
                }
            }
        }



        /*
        for (block in blocks){
            block.draw(canvas)
        }
        */

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

        for(orb in orbs){
            orb.update(scroll)
        }

        for (block in blocks){
            block.update(scroll)
        }
    }

    fun updateArrayPositionY(scroll: Float, array: Array<Block?>){
        //This is the equivalent to the method update, but for the array positionY
        positionYArray.set(array,positionYArray.getValue(array)+scroll)
    }

    fun getLastArray(): Array<Block?> {
        return filasA.get(filasA.lastIndex)
    }

    fun spawnOrbs(){
        if(orbs.size <= MAX_ORBS) {
            orbs.add(ofactory.create(temperature))

            //TODO FUNCTION TO DECIDE WHERE THE GHOST SHOULD SPAWN
            //we could make the function return a Par<Float, Float> and pass each one for parameter or we could change the set position to redive a par.
            orbs[(orbs.size - 1)].setPosition(250f, 250f)
        }
    }

    fun removeBreakableBlock(b : Block){
        breakableBlocks.remove(b)
    }

    fun deleteLine(array : Array<Block?>){
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
    }
    fun deleteBreakableBlock(b : Block){
        //todo mirar la manera más eficiente(con array)
        //if breakable block is a son class of block, we can make that it saves its array
        //and the position it has in the array, so that way it goes directly to delete itself
        //instead of running over the whole matrix

        //por ahora un remove cutrillo
        breakableBlocks.remove(b)
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
        createArrayLevel(ancho,alto)
    }



    fun createArrayLevel(ancho: Int,alto: Int){
        //todo mirar esta cosa maravillosa
        var arrayIntermitenteBool: BooleanArray = BooleanArray(ancho)
        var arrayVacio = BooleanArray(ancho)

        for(i in 0 until arrayIntermitenteBool.size){
            arrayIntermitenteBool.set(i,i%2==0)
            arrayVacio.set(i,false)
        }

        for(i in 0 until alto){
            if(i%6==0){
                createNewBlockLine(arrayVacio.copyOf(), i)
            }
            else{
                createNewBlockLine(arrayIntermitenteBool.copyOf(),i)
            }
        }

    }



    fun createNewBlockLine(arrayBooleanos : BooleanArray,indexLine : Int){
        var anchoBloque : Float= Block.blockSide
        var desplazamiento : Float
        val positionY : Float=anchoBloque.times(indexLine.times(-1))
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

        //Probability that each place in the first line has a block
        var probPrimeros : Float=0.3F
        //Probability that one block is created afterwards the obligatory blocks are created
        var probRandomHole : Float=0.3F
        //Probability that once one line is created it reapeats itselve just after
        var probRepetLine : Float=0.7F


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

