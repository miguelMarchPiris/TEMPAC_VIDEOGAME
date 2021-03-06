package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.*

/*IMPORTANT COMMENTS
* PacMan hitbox is rectangular and varies depending the image inserted
* */

class Player(posx: Float, posy: Float, imageList: List<Bitmap>) : Actor(imageList) {
    enum class Direction { STATIC, UP, LEFT, RIGHT, DOWN }
    var direction = Direction.UP
    var ySpeed = 1.50f
    var xSpeed = 1.75f
    //private var speed = 5f
    private var h : Float = 0f
    private var w : Float = 0f
    private var image = imageList[0] //default: we put the first image because yes.
    private var frameCounter = 0
    //anim duration in frames
    private var animDuration  = 30
    //private var paint = Paint()



    init {
        x=posx
        y=posy
        h = image.height.toFloat()
        w = image.width.toFloat()
        rectangle.set(x-w,y-h,x,y)

        //paint.color = Color.RED

    }
    fun animate(){
        //depending the direction pacman will face one way or another
        //We also swap between open and closed
        frameCounter++
        if(frameCounter.rem(animDuration) > (1/3f)*animDuration) {
            when (direction) {
                Direction.UP -> image = super.imageList[0]
                Direction.RIGHT -> image = super.imageList[1]
                Direction.DOWN -> image = super.imageList[2]
                Direction.LEFT -> image = super.imageList[3]
            }
        }
        else
            image = super.imageList[4]
    }
    override fun draw(canvas: Canvas?) {

        canvas?.drawBitmap(image,rectangle.left,rectangle.top,null)
    }

    fun update(scroll: Float,screenCatchUp : Boolean){
        super.update(scroll)
        when(direction){
            Direction.UP -> {
                if (screenCatchUp) {
                    y -= scroll
                } else
                    y -= scroll +ySpeed
            }
            Direction.LEFT -> x-=xSpeed+scroll
            Direction.RIGHT -> x+=xSpeed+scroll
            Direction.DOWN -> y+=scroll+ySpeed
            Direction.STATIC -> y=y
        }
        //these two lines are used if the image change his size. Are just a way to prevent random things to happen (the hitbox goes acord the image size)
        h = image.height.toFloat()
        w = image.width.toFloat()
        rectangle.set(x-w,y-h,x,y)
    }
}