package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Block(posx : Float, posy : Float) : Actor(){
    private var paint = Paint()
    private val width = blockSide
    private val height  = blockSide
    var rectangle:RectF
    companion object {
        //Here to change block size
        val blockSide:Float=90f;
    }

    init{
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f

        super.setPosition(posx, posy)
        rectangle = RectF(x-width/2f,y-height/2f,x+width/2f,y+height/2f)
    }
    override fun draw(canvas: Canvas?){
        canvas?.drawRect(rectangle,paint)
        rectangle.set(RectF(x-width/2f,y-height/2f,x+width/2f,y+height/2f))
    }
    fun dime(){

    }
}