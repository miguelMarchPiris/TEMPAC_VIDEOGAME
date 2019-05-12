package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.RectF
import edu.ub.pis2019.pis_16.tempac.Model.OrbType.Companion.r
import java.text.FieldPosition
import java.util.*

abstract class Ghost(image : Bitmap) : Actor(){
    private var w : Float = 0f
    private var h : Float = 0f
    var speed = 0.75f
    var im = image
    init {
        w = im.width.toFloat()
        h = im.height.toFloat()
        rectangle = RectF(x-w,y-h,x,y)
        r= Random()
    }

    fun getH(): Float {
        return h
    }
    fun getW(): Float {
        return w
    }

    fun update(scroll: Float, playerPosition: Pair<Float,Float>,rows: Triple<Array<Block?>?,Array<Block?>?,Array<Block?>?>){
        super.update(scroll)

        //Chasing algorithm:
        //Based on calculating the distance to the player with every valid movement and then performing the move that gets
        //the ghost closer to the player
        var row: Array<Block?>?
        //distancia al jugador en funcion del movimiento 0-up, 1-left, 2-right, 3-down
        var distances = arrayOf(Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE)

        /***************************************
         *           CHECK UP
         **************************************/
        row = rows.third
        //presuponemos que nos podemos mover
        moveUp(scroll)
        updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row==null || !collidesWithBlock(row)){
            distances[0] = calculateDistanceToPlayer(playerPosition)
        }
        //Deshacemos el movimiento
        moveDown(scroll)

        /***************************************
         *           CHECK LEFT
         **************************************/
        row = rows.second
        //presuponemos que nos podemos mover izq
        moveLeft(scroll)
        updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row==null || !collidesWithBlock(row)){
            distances[1] = calculateDistanceToPlayer(playerPosition)
        }
        //deshacemos movement
        moveRight(scroll)
        /***************************************
         *           CHECK RIGHT
         **************************************/
        moveRight(scroll)
        updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row==null || !collidesWithBlock(row)){
            distances[2] = calculateDistanceToPlayer(playerPosition)
        }
        //deshacemos el movimiento
        moveLeft(scroll)

        /***************************************
         *           CHECK DOWN
         **************************************/
        row = rows.first
        //presuponemos que nos podemos mover
        moveDown(scroll)
        updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row == null || !collidesWithBlock(row)){
            distances[3] = calculateDistanceToPlayer(playerPosition)
        }
        //deshacemos el movimiento
        moveUp(scroll)

        /***************************************
         *        SEARCH MIN DISTANCE
         **************************************/
        //Buscamos la distancia mas pequeña y en funcion de eso nos movemos
        var min = Float.MAX_VALUE
        var minIndex = 0
        for((index,distance) in distances.withIndex()){
            if(distance < min) {
                min = distance
                minIndex = index
            }
        }
        when(minIndex){
            0 -> moveUp(scroll)
            1 -> moveLeft(scroll)
            2 -> moveRight(scroll)
            3 -> moveDown(scroll)
        }
        updateRect()
    }
    private fun calculateDistanceToPlayer(playerPosition: Pair<Float, Float>):Float{
        return Math.hypot((playerPosition.first - x).toDouble(), (playerPosition.second - y).toDouble()).toFloat()
    }
    private fun moveUp(scroll: Float){
        y-=speed+scroll
    }
    private fun moveLeft(scroll: Float){
        x-=speed+scroll*0.5f
    }
    private fun moveRight(scroll: Float){
        x+=speed+scroll*0.5f
    }
    private fun moveDown(scroll: Float){
        y+=speed+scroll
    }
    private fun collidesWithBlock(row:Array<Block?>):Boolean{
        var collides = false
        for (block in row) {
            if(block!= null && RectF.intersects(rectangle,block.rectangle)){
                collides = true
                break
            }
        }
        return collides
    }
    private fun updateRect(){
        rectangle.set(x-w,y-h,x,y)
    }
    override fun draw(canvas: Canvas?) {
        canvas?.drawBitmap(im, rectangle.left, rectangle.top, null)
    }
}