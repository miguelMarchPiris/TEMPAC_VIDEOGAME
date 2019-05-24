package edu.ub.pis2019.pis_16.tempac.Model
import android.util.Log
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

    override fun createArrayLevel(ancho: Int,alto: Int){
        var emptyArray = BooleanArray(ancho)
        var arrayIntermitenteBool = BooleanArray(ancho)

        for(i in 0 until alto){
            if(i!=alto-1){
                createNewBlockLine(emptyArray.copyOf(), i)
            }else{
                createNewBlockLine(arrayIntermitenteBool,i)
            }
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




}