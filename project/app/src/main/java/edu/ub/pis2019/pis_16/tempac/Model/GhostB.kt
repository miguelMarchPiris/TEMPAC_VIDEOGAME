package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class GhostB : Ghost() {
    private var paint = Paint()
    init {
        paint.color = Color.BLUE
    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(rectangle, paint)
    }
}