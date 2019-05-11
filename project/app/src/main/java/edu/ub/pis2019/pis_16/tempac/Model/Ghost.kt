package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF

abstract class Ghost(image : Bitmap) : Actor(){
    private var w : Float = 0f
    private var h : Float = 0f
    var im = image
    init {
        w = im.width.toFloat()
        h = im.height.toFloat()
        rectangle = RectF(x-w,y-h,x,y)
    }

    fun follow(){
        TODO("Chasing algorithm")
    }

    override fun update(scroll: Float){
        super.update(scroll)
        rectangle.set(x-w,y-h,x,y)
    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(im, rectangle.left, rectangle.top, null)
    }
}