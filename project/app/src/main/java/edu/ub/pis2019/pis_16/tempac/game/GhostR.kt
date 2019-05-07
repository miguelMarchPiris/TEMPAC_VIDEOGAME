package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class GhostR : Ghost() {
    private var paint = Paint()
    init {
        paint.color = Color.RED
    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawRect(rectangle, paint)
    }
}