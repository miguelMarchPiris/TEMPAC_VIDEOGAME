package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.*
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameEngine

class Block(posx : Float, posy : Float, breakable : Boolean, imageBlockList : List<Bitmap>) : Actor(imageBlockList){
    private var paint = Paint()
    private var paintInside = Paint()
    private var paintShade = Paint()
    private val width = blockSide +15f
    private val height  = blockSide + 15f
    private lateinit var rectangleInside: RectF
    private lateinit var rectangleInsideBorder: RectF
    private val blockColor = Color.parseColor("#7c3cc9")
    private val blockShadeColor = Color.parseColor("#4A148C")
    private val breakableBlockColor = Color.RED
    private val breakableBlockShadeColor = Color.parseColor("#990000")
    //private var image = imageBlockList[0] //when we have block images this will have to be activated

    var breakable : Boolean = breakable
    companion object {
        //Here to change block size
        const val blockSide:Float=120f
    }
    init{
        paint.color = blockColor
        paint.style = Paint.Style.FILL
        paintShade.color = blockShadeColor
        paintShade.style = Paint.Style.FILL
        paintInside.color =  Color.BLACK
        super.setPosition(posx, posy)
        updateRects()

        //h = image.height.toFloat()
        //w = image.width.toFloat()
    }
    //Draws the block shade
    fun drawShade(canvas: Canvas?){
        canvas?.drawRect(rectangle,paintShade)
    }
    //Draws the blocks borders
    override fun draw(canvas: Canvas?){
        canvas?.drawRect(rectangleInsideBorder,paint)
        canvas?.drawRoundRect(rectangleInside,6f,6f,paintInside)
    }

    fun update(scroll: Float, temperature:Float) {
        super.update(scroll)
        if(breakable && temperature>=GameEngine.BLOCK_BREAKABLE_TEMPERATURE){
            paint.color = breakableBlockColor
            paintShade.color = breakableBlockShadeColor
        }
        else{
            paintShade.color = blockShadeColor
            paint.color = blockColor
        }
        updateRects()
    }
    private fun updateRects(){
        val shadowThickness = 10f
        val borderThickness = 8f
        rectangle.set(x-width/2f,y-height/2f,x+width/2f,y+height/2f)
        rectangleInsideBorder = RectF(rectangle.left, rectangle.top,
            rectangle.right-shadowThickness,rectangle.bottom-shadowThickness)
        rectangleInside =  RectF(rectangle.left+borderThickness,rectangle.top+borderThickness,
            rectangle.right-borderThickness - shadowThickness,rectangle.bottom-borderThickness -shadowThickness)
    }

}