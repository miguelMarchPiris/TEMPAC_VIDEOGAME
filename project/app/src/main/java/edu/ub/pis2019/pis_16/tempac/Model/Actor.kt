package edu.ub.pis2019.pis_16.tempac.Model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF

//An actor is an object that moves (not just by the displacement of the screen
abstract class Actor() : Object()  {

    var rectangle = RectF(x,y,x+10f,y+10f)
    var imageList : List<Bitmap> = listOf()


    constructor(list : List<Bitmap>) : this(){
        imageList  = list
    }

    //process inputs
    open fun update(scroll: Float){
        y+=scroll
    }
}