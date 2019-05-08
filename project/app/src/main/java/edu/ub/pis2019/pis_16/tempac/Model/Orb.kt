package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.*

//operand is what the orb is going to do (add, multiply, divide or substract) and _number is the value that is gonna act.
class Orb (posx : Float, posy : Float, operand : Operand, number : Int) : Actor(),
    Colisionable {

    private var paint = Paint()
    private var textPaint = Paint()
    val operand = operand
    val number = number
    enum class Operand(val text:String){ ADD("+"), MUL("x"), DIV(":"), SUB("-") }

    init{
        super.setPosition(posx, posy)
        rectangle = RectF(x-25,y-25,x+25,y+25)
        android.graphics.RectF()
        paint.color = Color.WHITE
        textPaint.color = Color.BLACK
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.textSize = 22f
        textPaint.style = Paint.Style.FILL_AND_STROKE
        textPaint.strokeWidth = 2f
        //textPaint.isFakeBoldText = true

    }

    override fun draw(canvas: Canvas?) {
        canvas?.drawCircle(x,y,(rectangle.right-rectangle.left)/2f,paint)
        canvas?.drawText(operand.text+" "+number.toString(),x,y+7.5f,textPaint)
    }

    override fun update(scroll: Float) {
        super.update(scroll)
        rectangle.set(x-25,y-25,x+25,y+25)

    }
}