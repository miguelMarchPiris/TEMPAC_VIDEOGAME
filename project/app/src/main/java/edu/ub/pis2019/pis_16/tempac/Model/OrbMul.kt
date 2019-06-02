package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.*

class OrbMul(number: Int) : Orb(number){

    init{
        //setPosition(500f,250f)
        paint.color = Color.parseColor("#999999")
        operand = Orb.Operand.MUL
    }



    override fun draw(canvas: Canvas?) {
        drawPolygon(canvas,x,y,(rectangle.right-rectangle.left)/1.9f, 8f, 0f, false, paint)
        canvas?.drawText(operand.text+" "+number.toString(),x,y+7.5f,textPaint)
    }

    //draws any type of regular poligon.
    private fun drawPolygon(
        mCanvas: Canvas?,
        x: Float,
        y: Float,
        radius: Float,
        sides: Float,
        startAngle: Float,
        anticlockwise: Boolean,
        paint: Paint
    ) {

        if (sides < 3) {
            return
        }

        val a = Math.PI.toFloat() * 2 / sides * if (anticlockwise) -1 else 1
        mCanvas?.save()
        mCanvas?.translate(x, y)
        mCanvas?.rotate(startAngle)
        val path = Path()
        path.moveTo(radius, 0f)
        var i = 1
        while (i < sides) {
            path.lineTo(
                radius * Math.cos((a * i).toDouble()).toFloat(),
                radius * Math.sin((a * i).toDouble()).toFloat()
            )
            i++
        }
        path.close()
        mCanvas?.drawPath(path, paint)
        mCanvas?.restore()
    }
}