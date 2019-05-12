package edu.ub.pis2019.pis_16.tempac.Model

import android.content.res.Resources
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameEngine
import edu.ub.pis2019.pis_16.tempac.R

class Block(posx : Float, posy : Float, breakable : Boolean, imageBlockList : List<Bitmap>) : Actor(imageBlockList){
    private var paint = Paint()
    private var paintInside = Paint()
    private val width = blockSide
    private val height  = blockSide
    private var w = 0f
    private var h = 0f
    private var rectangleInside = RectF(x-width/2f+15f,y-height/2f+15f,x+width/2f-15,y+height/2f-15)
    private val blockColor = Color.parseColor("#7c3cc9")
    //private var image = imageBlockList[0] //when we have block images this will have to be activated

    var breakable : Boolean = breakable
    companion object {
        //Here to change block size
        const val blockSide:Float=120f
    }
    init{
        paint.color = blockColor
        paint.style = Paint.Style.FILL

        paintInside.color = Color.BLACK
        super.setPosition(posx, posy)
        rectangle = RectF(x-width/2f+10f,y-height/2f+10f,x+width/2f-10,y+height/2f-10)
        //h = image.height.toFloat()
        //w = image.width.toFloat()
    }

    override fun draw(canvas: Canvas?){
        canvas?.drawRect(rectangle,paint)
        canvas?.drawRoundRect(rectangleInside,7f,7f,paintInside)

        /*
        if(breakable){
            image=imageBlockList[0]
        }else{
            image=imageBlockList[1]
        }
        canvas?.draw(image,rectangle.left,rectangle.top,null)
        */
    }

    fun update(scroll: Float, temperature:Float) {
        super.update(scroll)
        if(breakable && temperature>=GameEngine.breakableTempeature){
            paint.color = Color.RED
        }
        else{
            paint.color = blockColor
        }
        rectangle.set(x-width/2f,y-height/2f,x+width/2f,y+height/2f)
        rectangleInside = RectF(x-width/2f+10f,y-height/2f+10f,x+width/2f-10,y+height/2f-10)

        //will be missing to actualize the rectangle to match the image if it changes.

    }
}