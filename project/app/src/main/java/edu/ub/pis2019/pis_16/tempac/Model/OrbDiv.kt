package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class OrbDiv(number : Int) : Orb() {

    private var textPaint = Paint()
    private val operand = ":"

    init{
        setPosition(800f,250f)
        super.number=number
        rectangle = RectF(x-25,y-25,x+25,y+25)
        paint.color = Color.WHITE
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawText(operand+" "+number.toString(),x,y+7.5f,textPaint)
    }
}