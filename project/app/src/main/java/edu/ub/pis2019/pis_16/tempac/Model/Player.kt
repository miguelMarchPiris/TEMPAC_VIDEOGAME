package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class Player : Actor() {
    enum class Direction { STATIC, UP, LEFT, RIGHT, DOWN }
    private var paint = Paint()
    var direction = Direction.STATIC
    var speed = 5f

    init {
        rectangle = RectF(x-20,y-20,x+20,y+20)
        paint.color = Color.RED
        x = 500f
        y = 1000f
    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(rectangle, paint)
    }
    override fun update(scroll: Float){
        super.update(scroll)
        when(direction){
            Direction.UP -> y-=scroll + speed
            Direction.LEFT -> x-=speed
            Direction.RIGHT -> x+=speed
            Direction.DOWN -> y+=scroll+speed
        }
        rectangle.set(x-20,y-20,x+20,y+20)
    }
}