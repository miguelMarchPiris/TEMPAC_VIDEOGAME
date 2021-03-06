package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.*

//operand is what the orb is going to do (add, multiply, divide or substract) and _number is the value that is gonna act.
abstract class Orb(var number: Int): Actor() {

    protected var paint = Paint()
    protected var textPaint = Paint()
    enum class Operand(val text:String){ ADD("+"), MUL("x"), DIV(":"), SUB("-") }
    lateinit var operand : Operand

    init{
        textPaint.color = Color.BLACK
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 25f
        textPaint.style = Paint.Style.FILL_AND_STROKE
        //textPaint.strokeWidth = 1.5f
        textPaint.setTypeface(Typeface.create("Droid Sans Mono", Typeface.BOLD))
    }



    override fun update(scroll: Float) {
        super.update(scroll)
        rectangle.set(x-28,y-28,x+28,y+28)

    }
}