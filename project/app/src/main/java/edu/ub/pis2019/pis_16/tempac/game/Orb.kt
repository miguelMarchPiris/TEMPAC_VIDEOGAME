package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

//operand is what the orb is going to do (add, multiply, divide or substract) and _number is the value that is gonna act.
class Orb (posx : Float, posy : Float,_operand : String, _number : Int) : Object() {

    private var paint = Paint()

    //COlisions related
    val position = RectF(x-30,y+30,x+30,y-30)

    init{
        paint.color = Color.YELLOW
        super.setPosition(posx, posy)
    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(x-30,y+30,x+30,y-30,paint)

        //després farem servir el drawBitamp, de moment el paint per provar oof.
    }

    var operand : String=_operand
    var number : Int=_number

    override fun update(scroll: Float) {

        super.update(scroll)

    }
}