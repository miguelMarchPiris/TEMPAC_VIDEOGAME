package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.*
import android.support.constraint.solver.widgets.Rectangle

//operand is what the orb is going to do (add, multiply, divide or substract) and _number is the value that is gonna act.
class Orb (posx : Float, posy : Float,operand : Operand, number : Int) : Actor(), Colisionable {

    private var paint = Paint()
    val operand = operand
    val number = number
    enum class Operand{ ADD, MUL, DIV, SUB }

    init{
        super.setPosition(posx, posy)
        rectangle = RectF(x-30,y-30,x+30,y+30)
        paint.color = Color.YELLOW

    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(rectangle,paint)

        //després farem servir el drawBitamp, de moment el paint per provar oof.
    }

    override fun update(scroll: Float) {
        super.update(scroll)
        rectangle.set(x-30,y-30,x+30,y+30)

    }
}