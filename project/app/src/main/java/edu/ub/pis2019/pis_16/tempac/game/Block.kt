package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Block(posx : Float, posy : Float) : Actor(){
    private var paint = Paint()
    private val width = 80f
    private val height  = 80f
    var rectangle:RectF
    init{
        paint.color = Color.BLUE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f

        super.setPosition(posx, posy)
        rectangle = RectF(x-width/2f,y-height/2f,x+width/2f,y+height/2f)
    }
    override fun draw(canvas: Canvas?){
        canvas?.drawRect(rectangle,paint)
    }

    override fun update(scroll: Float) {
        super.update(scroll)
        rectangle.set(x-width/2f,y-height/2f,x+width/2f,y+height/2f)

    }
}