package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import android.util.Log
import edu.ub.pis2019.pis_16.tempac.Model.Game.Engine
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameEngine
import java.util.*

//clase colisionable (los objetos con los que chocas i no pasa nada) i class no colisionable (los objetos no colisionables que no pasa nada cuando xocan.)
open class GameLevel(blockImg : List<Bitmap>) : Level(blockImg) {

    override fun createArrayLevel(ancho: Int,alto: Int){
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
}

