package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Player () : Actor() {
    private var paint = Paint()
    init {
        paint.color = Color.RED
    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(y-20,x+20,y+20,x-20,paint)
    }
    fun update(){
        y++
        x++
    }
}