package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.util.Log
import edu.ub.pis2019.pis_16.tempac.Model.Game.Engine
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameEngine
import java.util.*

//clase colisionable (los objetos con los que chocas i no pasa nada) i class no colisionable (los objetos no colisionables que no pasa nada cuando xocan.)
open class Level(blockImg : List<Bitmap>) : Drawable {
    companion object{
        const val MAX_ORBS = 7
    }
    //IMAGE
    var blockImages : List<Bitmap>

    //ORBS
    var ofactory : OrbFactory = OrbFactory()
    var orbs : MutableList<Orb> = mutableListOf<Orb>()
    var orbInLastLine : Boolean = false
    var temperature = 0f

    //MATRIX
    //This is tha matrix of all the blocks
    var matrixBlocks : MutableList<Array<Block?>>
    //This hashmap relates every array (line of blocks) with the positionY of the blocks
    val positionYArray= hashMapOf<Array<Block?>,Float>()
    var nBlocksInLine: Int = 0
    var nLinesToDraw : Int = 0

    //PROBABILITIES OF THE BLOCK GENERATION ALGORITHM
    val r : Random=Random()
    //Probability that one HOLE is created afterwards the obligatory HOLES are created
    var probRandomHole : Float=0.2F
    //Probability that ONE line is repeated
    var probRepeatLine : Float=0.1F
    //Probability that one block is breakable
    var probBreakable : Float= 0.3F

    var messages  = mutableListOf<Pair<Float,String>>()

    init{
        matrixBlocks= mutableListOf<Array<Block?>>()

        blockImages=blockImg
        //How many lines, and how many blocks in one line.
        nBlocksInLine= Engine.PLAYFIELD_WIDTH.div(Block.blockSide).toInt()
        nLinesToDraw = (Engine.bottomPlayingField-Engine.topPlayingField).div(Block.blockSide).toInt()+4
        createLevelBlocks(nBlocksInLine,nLinesToDraw)
    }
    //Its just a shitty method yikes
    fun createLevelBlocks(ancho : Int, alto: Int){
        createArrayLevel(ancho,alto)
    }
    //Fills ths matrix with lines of nulls(so its empty), the top line is created with some blocks.
    internal open fun createArrayLevel(ancho: Int,alto: Int){
        var emptyArray = BooleanArray(ancho)
        var arrayIntermitenteBool: BooleanArray = BooleanArray(ancho)
        for(i in 0 until ancho){
            emptyArray[i] = false
            arrayIntermitenteBool[i] = i%2==0
        }
        for(i in 0 until alto){
            if(i!=alto-1){
                createNewBlockLine(emptyArray.copyOf(), i)
            }else{
                createNewBlockLine(arrayIntermitenteBool,i)
            }
        }
    }
    //IT CREATES A LINE WHEN GIVEN A BOOLEAN ARRAY. JUST IN THE INIT
    fun createNewBlockLine(arrayBooleanos : BooleanArray,indexLine : Int){
        //first we add this so only one orb spawns in one line
        orbInLastLine=false

        var anchoBloque : Float= Block.blockSide
        var desplazamiento : Float
        val positionY : Float=anchoBloque.times(indexLine.times(-1))+(Engine.bottomPlayingField+Block.blockSide.times(1.5F))
        val arrayBlocks = arrayOfNulls<Block?>(arrayBooleanos.size)

        for (k in 0 until arrayBooleanos.size){
            if(arrayBooleanos.get(k)){
                //Calcular la posición que hay que pasarle al bloque
                desplazamiento=anchoBloque.times(k).plus(anchoBloque.div(2))

                //HOW WE CHOOSE BREAKABLE BLOCKS HERE

                var b =Block(desplazamiento,positionY, true, blockImages)
                arrayBlocks.set(k,b)
            }
        }
        //We add the array in the fist position of FilasA
        matrixBlocks.add(0,arrayBlocks)
        //We introduce the value of the positionY of the array
        positionYArray.put(arrayBlocks,positionY)
    }
    //IT CREATES A LINE WHEN GIVEN A BOOLEAN ARRAY. WHEN CREATING A NEW LINE AFTER THE INIT
    fun createNewBlockLine(arrayBooleanos : BooleanArray,posY : Float){
        //first we add this so only one orb spawns in one line
        orbInLastLine=false

        var anchoBloque : Float= Block.blockSide
        var desplazamiento : Float
        val arrayBlocks = arrayOfNulls<Block?>(arrayBooleanos.size)

        for (k in 0 until arrayBooleanos.size){
            if(arrayBooleanos[k]){
                //Calcular la posición que hay que pasarle al bloque
                desplazamiento=anchoBloque.times(k).plus(anchoBloque.div(2))
                //Todo, see how we choose breakable blocks
                arrayBlocks[k] = Block(desplazamiento,posY, r.nextFloat()<probBreakable, blockImages)
            }
        }
        //We add the array in the fist position of FilasA
        matrixBlocks.add(0,arrayBlocks)
        //We introduce the value of the positionY of the array
        positionYArray.put(arrayBlocks,posY)
    }

    //GET ARRAYS OF BLOCKS
    fun getLastArray(): Array<Block?>? {
        if(matrixBlocks.lastIndex!=-1)
            return matrixBlocks.get(matrixBlocks.lastIndex)
        else
            return null
    }
    fun getFirstArray(): Array<Block?>? {
        return matrixBlocks.get(0)
    }
    fun getFirstPositiveArray(): Array<Block?>? {
        for (array in matrixBlocks){
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

    //DELETES THE LINE FROM THE MATRIX AND THE HASHMAP
    fun deleteLine(array : Array<Block?>){
        //First we fill the array with nulls
        for(i in 0 until array.size){
            array.set(i,null)
        }
        //We remove it from the hasmap positionY
        positionYArray.remove(array)
        //We remove the whole array from matrixBlocks
        matrixBlocks.remove(array)

        //Then we create a new line on top
        newLineOnTop()
    }
    //ITS CALLED WHEN A LINE IS DELETED.
    internal open fun newLineOnTop() {
        val first=getFirstArray()
        val positionY= positionYArray[first] as Float
        if(first==null){ Log.println(Log.VERBOSE,"ERROR", "NULL first array of Blocks") }

        var firstBArray= createBooleanArray(first as Array<Block?>)
        //Here we decide if the last line of blocks gets repeted
        if(!(r.nextFloat()<probRepeatLine)){
            //algorithmNewBooleanArray is the method that creates new lines using the ALGORITHM
            firstBArray = algorithmNewBooleanArray(firstBArray)
        }
        createNewBlockLine(firstBArray,positionY.minus(Block.blockSide))
    }
    //IT CONTAINS THE ALGORITHM FOR THE LEVEL GENERATION
    fun algorithmNewBooleanArray(booleanArray: BooleanArray): BooleanArray{
        var nueva : BooleanArray= BooleanArray(booleanArray.size)
        for (i in 0 until nueva.size){nueva.set(i,true)} //We init the array to true

        var holes : MutableList<Pair<Int,Int>> = getHoles(booleanArray)
        var firstPositionHole : Int
        var totalHuecos : Int
        var tupla : Pair<Int,Int>

        for ( i in 0 until holes.size){
            tupla = holes[i]
            firstPositionHole=tupla.first
            totalHuecos=tupla.second

            //This is to make 2 paths from a large hole
            if(totalHuecos>=3){
                nueva.set(firstPositionHole,false)
                nueva.set(firstPositionHole+(totalHuecos-1), false)
            }

            else{
                if(totalHuecos==1){
                    nueva.set((firstPositionHole),false)
                }
                else{
                    //We put a false in any random place of the hole
                    nueva.set((firstPositionHole+(r.nextInt(totalHuecos))),false)
                }

            }

            //We create random holes
            var left : Boolean?
            var right : Boolean?
            for (i in 0 until nueva.size){
                //So it can make holes beneath holes
                left=nueva.getOrNull(i-1)
                right=nueva.getOrNull(i+1)
                if(left==null){left=true}
                if(right==null){right=true}

                if(r.nextFloat()<probRandomHole && (! booleanArray.get(i) || !left || !right )  ){
                    nueva.set(i,false)
                }
            }
        }
        return nueva
    }
    //ITS USED FOR THE ALGORITHM
    fun getHoles(line : BooleanArray) : MutableList<Pair<Int, Int>> {
        //Returns a MutableList of Pairs.
        // The first is the position (index) where its begins,
        // the second is the width of the hole
        // Examenple    XXX__XX would be (3,2)
        var holeList : MutableList<Pair<Int,Int>> = mutableListOf<Pair<Int,Int>>()
        var prev : Boolean = true
        var position : Int=0
        var widthHole : Int=0
        var actual : Boolean

        for (i in 0 until line.size){
            actual=line.get(i)

            if (prev){
                // (XX)
                if(actual){}
                // (X_) Empezamos a contar
                else{
                    position=i
                    widthHole=0
                    if(i==line.size-1){holeList.add(Pair(position, widthHole+1))}
                }
            }
            else{
                //(_X)
                if (actual){holeList.add(Pair(position, widthHole+1)) }
                //(__)
                else{
                    widthHole++
                    if(i==line.size-1){holeList.add(Pair(position, widthHole+1))}
                }
            }
            prev=actual
        }
        return holeList
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
        for( array in matrixBlocks){
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

    //Method to give the ghost info about where he can move next
    //With the ghost's Y we return the row above, below and in the middle
    fun get3RowsAtY(ghostY: Float):Triple<Array<Block?>?,Array<Block?>?,Array<Block?>?>{
        var aboveRow : Array<Block?>? = null
        var middleRow : Array<Block?>? = null
        var downRow : Array<Block?>? = null
        val lastArrayPosition = positionYArray.get(getLastArray())
        if(lastArrayPosition!=null){
            var rowPosition : Float
            //Si lo haces con el índice es muuuucho más facil luego sacar las otras dos
            val halfBlock=Block.blockSide/2
            for (i in 0 .. matrixBlocks.size){
                rowPosition=lastArrayPosition-Block.blockSide.times(i)
                //hay que mirar la lista por el final
                val j = matrixBlocks.size - (i + 1)
                if (ghostY in rowPosition-halfBlock .. rowPosition+halfBlock){
                    //el matrixBlocks.get(índice) es el del medio

                    //Cuidado, revisa los casos en los extremos
                    aboveRow=matrixBlocks.getOrNull(j-1)
                    middleRow=matrixBlocks.getOrNull(j)
                    downRow=matrixBlocks.getOrNull(j+1)
                    break
                }
            }
        }
        return Triple(
            downRow,
            middleRow,
            aboveRow
        )

    }

    //SPAWNER OF ORBS
    fun spawnOrbs(){
        val arrayBlocks=getFirstPositiveArray()
        if(arrayBlocks==null || orbs.size >= MAX_ORBS || orbInLastLine){
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

    }

    //DRAW AND UPDATE
    override fun draw(canvas: Canvas?) {
        for (array in matrixBlocks){
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
        for (array in matrixBlocks){
            //update the float position Y of array
            updateArrayPositionY(scroll,array)
            for (block in array){
                if (block!=null){
                    block.update(scroll,temperature)
                }
            }
        }
        spawnOrbs()

    }
    fun updateArrayPositionY(scroll: Float, array: Array<Block?>){
        //This is the equivalent to the method update, but for the array positionY
        positionYArray.set(array,positionYArray.getValue(array)+scroll)
    }
}

