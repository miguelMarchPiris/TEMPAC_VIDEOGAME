package edu.ub.pis2019.pis_16.tempac.Model
import android.graphics.Bitmap
import android.graphics.RectF
import android.util.Log
import edu.ub.pis2019.pis_16.tempac.Model.Game.Engine

class TutorialLevel() : Level(){



    fun changeTutorialPart(part: Int){
        matrixBlocks= mutableListOf()
        when(part){
            0 -> generateFirstTutorialPart(nBlocksInLine, nLinesToDraw)
            1 -> generateSecondTutorialPart(nBlocksInLine, nLinesToDraw)
            2 -> generateThirdTutorialPart(nBlocksInLine, nLinesToDraw)
        }
    }

    override fun createArrayLevel(width: Int, height: Int){
        generateFirstTutorialPart(width, height)
    }

    override fun newLineOnTop() {
        val first=getFirstArray()
        val positionY= positionYArray[first] as Float
        if(first==null){ Log.println(Log.VERBOSE,"ERROR", "NULL first array of Blocks") }
        var firstBArray= createBooleanArray(first as Array<Block?>)

        createNewBlockLine(firstBArray,positionY.minus(Block.blockSide))
    }


    override fun spawnOrbs() {
        val arrayBlocks=getFirstPositiveArray()
        if(arrayBlocks==null || orbs.size >= MAX_ORBS || orbInLastLine){
            return
        }
        val par= Pair(positionYArray[arrayBlocks], mutableListOf(4))
        val positionY=par.first as Float
        val indexOfHoles=par.second
        //We get any hole of the line.
        var indiceDeLaLista=r.nextInt(indexOfHoles.size)
        var indice=indexOfHoles[indiceDeLaLista]

        var newOrb = OrbAdd(2)
        newOrb.setPosition(Block.blockSide.times(indice.plus(0.5f)),positionY)
        for (o in orbs){
            if(RectF.intersects(o.rectangle,newOrb.rectangle)){
                if (indiceDeLaLista==0){ indiceDeLaLista++ }
                else{ indiceDeLaLista-- }
                indice=indexOfHoles[indiceDeLaLista]
                newOrb.setPosition(Block.blockSide.times(indice.plus(0.5f)),positionY)
            }
        }
        orbs.add(newOrb)
        orbInLastLine=true

    }


    private fun generateFirstTutorialPart(width: Int, height: Int){
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

        messages.add(Pair(RectF(400f,1400f,620f,1700f), "Swipe up!"))
        messages.add(Pair(RectF(400f,200f,620f,1090f), "Swipe left!"))
        messages.add(Pair(RectF(0f,200f,40f,1090f), "Swipe up!"))
        messages.add(Pair(RectF(0f,710f,40f,730f), "Swipe right!"))
        messages.add(Pair(RectF(540f,710f,620f,780f), "Swipe up!"))


     }

     private fun generateSecondTutorialPart(width: Int, height: Int){
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

         messages.clear()
         messages.add(Pair(RectF(0f,1400f,1080f,1500f), "Watch out for the ghosts!"))
    }

     private fun generateThirdTutorialPart(width: Int, height: Int){
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

         messages.clear()
         messages.add(Pair(RectF(0f,1400f,1080f,1500f), "Picking orbs changes the temperature"))
         messages.add(Pair(RectF(0f,1200f,1080f,1400f), "Certain ghosts can only survive at certain temperatures"))
         messages.add(Pair(RectF(0f,1000f,1080f,1200f), "Have fun!"))

    }

    fun readNewLine() : BooleanArray{
        //Lee una nueva linea

        val newLine= BooleanArray(nBlocksInLine)
        for( i in 0 until newLine.size){
            newLine[i]=r.nextBoolean()
        }
        return newLine
    }

    fun getMessage(rectPlayer: RectF): String? {
        if(messages.isEmpty())
            return null

        var message = messages.first()
        if(RectF.intersects(message.first,rectPlayer)) {
            messages.remove(message)
            return message.second
        }
        return null
    }


}