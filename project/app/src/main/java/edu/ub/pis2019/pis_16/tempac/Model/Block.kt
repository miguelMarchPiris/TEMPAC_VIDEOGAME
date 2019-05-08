package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Block(posx : Float, posy : Float, breakable : Boolean) : Actor(){
    private var paint = Paint()
    private var paintInside = Paint()
    private val width = 80f
    private val height  = 80f
    private var rectangleInside = RectF(x-width/2f+5,y-height/2f+5f,x+width/2f-5,y+height/2f-5)
    var breakable : Boolean = breakable
    init{
        if(breakable)
            paint.color = Color.CYAN
        else
            paint.color = Color.BLUE

        paint.style = Paint.Style.FILL


            paintInside.color = Color.BLACK
        super.setPosition(posx, posy)
        rectangle = RectF(x-width/2f,y-height/2f,x+width/2f,y+height/2f)
    }

    override fun draw(canvas: Canvas?){
        canvas?.drawRect(rectangle,paint)
        canvas?.drawRect(rectangleInside,paintInside)

        /*
        if(breakable){
            canvas?.draw("BreakableBlock")
        }else{
            canvas?.draw("Block")
        }
        */
    }

    override fun update(scroll: Float) {
        super.update(scroll)
        rectangle.set(x-width/2f,y-height/2f,x+width/2f,y+height/2f)
        rectangleInside = RectF(x-width/2f+5,y-height/2f+5f,x+width/2f-5,y+height/2f-5)

    }
}