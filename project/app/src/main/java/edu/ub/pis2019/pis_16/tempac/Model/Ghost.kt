package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import edu.ub.pis2019.pis_16.tempac.Model.OrbType.Companion.r
import java.util.*

abstract class Ghost(image : Bitmap) : Actor(){
    private var w : Float = 0f
    private var h : Float = 0f
    var speed : Float
    var im = image
    init {
        w = im.width.toFloat()
        h = im.height.toFloat()
        rectangle = RectF(x-w,y-h,x,y)
        r= Random()
        //speed=r.nextFloat().minus(0.5f)
        speed=-0.5F
    }

    fun getH(): Float {
        return h
    }
    fun getW(): Float {
        return w
    }

    fun follow(){
        TODO("Chasing algorithm")
    }

    override fun update(scroll: Float){
        //todo
        //super.update(scroll)

        super.update(speed)
        rectangle.set(x-w,y-h,x,y)
    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(im, rectangle.left, rectangle.top, null)
    }
}