package edu.ub.pis2019.pis_16.tempac.Model
import android.util.Log
import edu.ub.pis2019.pis_16.tempac.Model.Game.Engine
import java.util.*

class LevelTutorial : Level(listOf()) {

    override fun newLineOnTop() {
        //FIRST WE GET THE TOP ARRAY AND ITS Y POSITION
        val first=getFirstArray()
        val positionY= positionYArray[first] as Float
        if(first==null){ Log.println(Log.VERBOSE,"ERROR", "NULL first array of Blocks") }

        //READ THE NEW LINE FROM WHEREVER AND CALL createNewBlockLine
        var firstBArray= readNewLine()


        //dejalo tal cual y solo haz cosas con firstBArray
        createNewBlockLine(firstBArray,positionY.minus(Block.blockSide))
    }

    fun changeTutorialPart(part: Int){
        matrixBlocks= mutableListOf()
        when(part){
            1 -> generateSecondTutorialPart(nBlocksInLine, nLinesToDraw)
            2 -> generateThirdTutorialPart(nBlocksInLine, nLinesToDraw)
        }
    }
    override fun createArrayLevel(width: Int, height: Int){
        generateFirstTutorialPart(width, height)
    }


    fun generateFirstTutorialPart(width: Int, height: Int){
         var fullLine = BooleanArray(width)
        fullLine.fill(true)

        var pathLength = height/4
        var turnWidht = 4
        var startX = width/2

        for(i in 0 until height){
            val index = i/pathLength
            var line = fullLine.copyOf()
            if(index == 2)
                line[startX-4] = false
            else
                line[startX] = false


            if(i == pathLength*2 || i == pathLength * 3)
                for (j in startX - 4 until startX + 1)
                    line[j] = false

            createNewBlockLine(line, i)
        }


     }

     fun generateSecondTutorialPart(width: Int, height: Int){
        var fullLine = BooleanArray(width)
        fullLine.fill(true)
        var emptyLine = BooleanArray(width)

        var pathLength = height/3
        var startX = width/2

        for(i in 0 until height){
            var line = fullLine.copyOf()
            when {
                i < pathLength -> line[startX] = false
                i % pathLength != 0 -> for(j in 0 until width) {
                    if ((j ) % 2 == 0)
                        line[j] = false
                }
                else -> line = emptyLine
            }

            createNewBlockLine(line, i)
        }

    }

     fun generateThirdTutorialPart(width: Int, height: Int){
        var fullLine = BooleanArray(width)
        fullLine.fill(true)
        var startX = width/2

        for(i in 0 until height){
            var line = fullLine.copyOf()
            line[startX-1] = false
            line[startX] = false
            line[startX+1] = false
            createNewBlockLine(line, i)
        }
    }

    fun readNewLine() : BooleanArray{
        //Lee una nueva linea

        val newLine= BooleanArray(nBlocksInLine)
        for( i in 0 until newLine.size){
            newLine[i]=r.nextBoolean()
        }
        return newLine
    }

    fun getMessage(y: Float): String? {
        if(messages.isEmpty())
            return null

        var message = messages.first()
        if(message.first >= y) {
            messages.remove(message)
            return message.second
        }
        return null
    }


}