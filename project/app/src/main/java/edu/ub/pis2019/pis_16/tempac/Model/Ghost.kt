package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import edu.ub.pis2019.pis_16.tempac.Model.OrbType.Companion.r
import java.util.*

abstract class Ghost(image : Bitmap) : Actor(){
    companion object {
        const val BASESPEED = 0.75F
    }
    private var w : Float = 0f
    private var h : Float = 0f
    var speed = 0.75f
    var im = image

    var behaviour : GhostBehaviour
    abstract var topCorrectTemperature : Float
    abstract var lowerCorrectTemperature : Float
    var belowTheLine : Boolean = true
    abstract var onCorrectTemperature : Boolean

    init {
        w = im.width.toFloat()
        h = im.height.toFloat()
        rectangle = RectF(x-w,y-h,x,y)
        r= Random()
        behaviour = BehaviourBelowTheLine
    }

    fun update(scroll: Float, playerPosition: Pair<Float,Float>,rows: Triple<Array<Block?>?,Array<Block?>?,Array<Block?>?>,belowTheLine : Boolean, temperature : Float){
        super.update(scroll)
        onCorrectTemperature=getOnCorrectTemperature(temperature)
        this.belowTheLine=belowTheLine
        if(belowTheLine) {
            behaviour=BehaviourBelowTheLine
        }
        else if (getOnCorrectTemperature(temperature)) {
            setSpecialBehaviour()
        }else{
            behaviour=BehaviourDefault
        }
        behaviour.chase(this,scroll,playerPosition,rows)
    }
    abstract fun getOnCorrectTemperature(temperature : Float) : Boolean
    abstract fun setSpecialBehaviour()

    fun calculateDistanceToPlayer(playerPosition: Pair<Float, Float>):Float{
        return Math.hypot((playerPosition.first - x).toDouble(), (playerPosition.second - y).toDouble()).toFloat()
    }
    fun moveUp(scroll: Float, f : Float){
        y-=speed.times(f)+scroll
    }
    fun moveLeft(scroll: Float, f : Float){
        x-=speed.times(f)+scroll
    }
    fun moveRight(scroll: Float, f : Float){
        x+=speed.times(f)+scroll
    }
    fun moveDown(scroll: Float, f : Float){
        y+=speed.times(f)+scroll
    }
    fun collidesWithBlock(row:Array<Block?>):Boolean{
        var collides = false
        for (block in row) {
            if(block!= null && RectF.intersects(rectangle,block.rectangle)){
                collides = true
                break
            }
        }
        return collides
    }
    fun updateRect(){
        rectangle.set(x-w,y-h,x,y)
    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(im, rectangle.left, rectangle.top, null)
    }
}