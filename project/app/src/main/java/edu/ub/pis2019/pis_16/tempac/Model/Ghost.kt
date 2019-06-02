package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import edu.ub.pis2019.pis_16.tempac.Model.OrbType.Companion.r
import java.util.*

abstract class Ghost(image : Bitmap) : Actor(){
    companion object {
        const val BASESPEED = 0.75F
    }
    private var w : Float = 0f
    fun getW() :Float{
        return w
    }
    private var h : Float = 0f
    fun getH(): Float {
        return h
    }
    var speed = 0.75f
    var dead = false
    //Tells if the spawn animation is over
    private var spawnAnimEnded = false
    //Tells if the death animation is over
    var deathAnimEnded = false
    private var alphaPaint = Paint()
    var im = image

    var behaviour : GhostBehaviour
    abstract var topCorrectTemperature : Float
    abstract var lowerCorrectTemperature : Float
    var belowTheLine : Boolean = true
    var tooHigh : Boolean = false
    abstract var onCorrectTemperature : Boolean
    var temperature : Float

    init {
        //We start with an invisible ghost
        alphaPaint.alpha = 0
        w = im.width.toFloat()
        h = im.height.toFloat()
        rectangle = RectF(x-w,y-h,x,y)
        r= Random()
        behaviour = BehaviourBelowTheLine
        temperature = 50F
    }

    fun update(scroll: Float, playerPosition: Pair<Float,Float>,rows: Triple<Array<Block?>?,Array<Block?>?,Array<Block?>?>,belowTheLine : Boolean, temperature : Float){
        super.update(scroll)
        if(!dead){
            this.temperature=temperature
            onCorrectTemperature=getIfCorrectTemperature()
            this.belowTheLine=belowTheLine
            if(belowTheLine) {
                behaviour=BehaviourBelowTheLine
            }
            else if (getIfCorrectTemperature()) {
                setSpecialBehaviour()
            }else{
                behaviour=BehaviourDefault
            }
            behaviour.chase(this,scroll,playerPosition,rows)
        }
        updateRect()

    }
    fun animate(){
        if(!dead && !spawnAnimEnded){
            //We animate the alpha channel in the paint
            if((alphaPaint.alpha+255/60) > 255)
                alphaPaint.alpha+= 255/60
            else {
                spawnAnimEnded = true
                alphaPaint.alpha = 255
            }
        }
        //If its dying we do death animation
        if(dead && !deathAnimEnded){
            //We animate the alpha channel in the paint
            //If the ghost is invisible the animation has ended
            if((alphaPaint.alpha-255/60) > 0)
                alphaPaint.alpha-= 255/60
            else
                deathAnimEnded = true
        }
    }
    abstract fun getIfCorrectTemperature() : Boolean
    //abstract fun getIfCorrectTemperature() : Boolean
    abstract fun setSpecialBehaviour()
    fun setNonHorizontalBehaviour(){
        if(getIfCorrectTemperature()){
            setSpecialBehaviour()
        }
        else{
            if(belowTheLine){
                behaviour=BehaviourBelowTheLine
            }else{
                behaviour=BehaviourDefault
            }
        }
    }
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
        canvas?.drawBitmap(im, rectangle.left, rectangle.top, alphaPaint)
    }
}