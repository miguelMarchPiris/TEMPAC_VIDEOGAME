package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.*

class Block(posx : Float, posy : Float, breakable : Boolean, imageBlockList : List<Bitmap>) : Actor(imageBlockList){
    private var paint = Paint()
    private var paintInside = Paint()
    private val width = 80f
    private val height  = 80f
    private var w = 0f
    private var h = 0f
    private var rectangleInside = RectF(x-width/2f+5,y-height/2f+5f,x+width/2f-5,y+height/2f-5)
    //private var image = imageBlockList[0] //when we have block images this will have to be activated

    var breakable : Boolean = breakable
    init{
        if(breakable)
            paint.color = Color.CYAN
        else
            paint.color = Color.BLUE

        paint.style = Paint.Style.FILL


            paintInside.color = Color.BLACK
        super.setPosition(posx, posy)
        rectangle = RectF(x-width/2f,y-height/2f,x+width/2f,y+height/2f)
        //h = image.height.toFloat()
        //w = image.width.toFloat()
    }

    override fun draw(canvas: Canvas?){
        canvas?.drawRect(rectangle,paint)
        canvas?.drawRect(rectangleInside,paintInside)

        /*
        if(breakable){
            image=imageBlockList[0]
        }else{
            image=imageBlockList[1]
        }
        canvas?.draw(image,rectangle.left,rectangle.top,null)
        */
    }

    override fun update(scroll: Float) {
        super.update(scroll)
        rectangle.set(x-width/2f,y-height/2f,x+width/2f,y+height/2f)
        rectangleInside = RectF(x-width/2f+5,y-height/2f+5f,x+width/2f-5,y+height/2f-5)

        //will be missing to actualize the rectangle to match the image if it changes.

    }
}