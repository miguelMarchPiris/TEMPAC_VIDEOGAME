package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Player : Actor() {
    enum class Direction {
        STATIC, UP, LEFT, RIGHT
    }
    private var paint = Paint()
    var direction = Direction.STATIC
    private var speed = 5f
    init {
        paint.color = Color.RED
        x = 500f
        y = 1000f
    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(x-20,y+20,x+20,y-20,paint)
    }
    override fun update(scroll: Float){
        super.update(scroll)
        when(direction){
            Direction.UP -> y-=scroll + speed
            Direction.LEFT -> x-=speed
            Direction.RIGHT -> x+=speed
        }
    }
}