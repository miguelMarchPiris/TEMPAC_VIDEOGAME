package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class OrbAdd(number: Int) : Orb(number) {


    init{
        //setPosition(100f,250f)
        paint.color = Color.parseColor("#ffffff")
        operand = Orb.Operand.ADD
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(x,y,(rectangle.right-rectangle.left)/2f,paint)
        canvas?.drawText(operand.text+" "+number.toString(),x,y+7.5f,textPaint)
    }


}