package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class GhostR(red : Bitmap) : Ghost(red) {
    //private var paint = Paint()
    init {
        //paint.color = Color.RED
        setPosition(460f, 820f)

    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(im, rectangle.left, rectangle.top, null)
    }
}