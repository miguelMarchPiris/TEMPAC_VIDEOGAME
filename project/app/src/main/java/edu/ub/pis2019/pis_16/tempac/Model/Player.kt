package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.*

/*IMPORTANT COMMENTS
* PacMan hitbox is rectangular and varies depending the image that is insered
* */

class Player(posx: Float, posy: Float, imageList: List<Bitmap>) : Actor(imageList) {
    enum class Direction { STATIC, UP, LEFT, RIGHT, DOWN }
    var direction = Direction.STATIC
    var speed = 1.25f
    //private var speed = 5f
    private var h : Float = 0f
    private var w : Float = 0f
    private var image = imageList[0] //default: we put the first image because yes.

    //private var paint = Paint()



    init {
        x=posx
        y=posy
        h = image.height.toFloat()/2
        w = image.width.toFloat()/2
        rectangle.set(x-w,y-h,x+w,y+h)

        //paint.color = Color.RED

    }

    override fun draw(canvas: Canvas?) {
        //canvas?.drawRect(rectangle, paint)

        //depending the direction pacman will face one way or another
        when(direction){
            Direction.UP -> image=super.imageList[0]
            Direction.RIGHT -> image=super.imageList[1]
            Direction.DOWN -> image=super.imageList[2]
            Direction.LEFT -> image=super.imageList[3]
        }
        canvas?.drawBitmap(image,rectangle.left,rectangle.top,null)

    }

    fun update(scroll: Float,screenCatchUp : Boolean){
        super.update(scroll)
        when(direction){
            Direction.UP -> {
                if (screenCatchUp) {
                    y -= scroll
                } else
                    y -= scroll + speed
            }
            Direction.LEFT -> x-=speed+scroll
            Direction.RIGHT -> x+=speed+scroll
            Direction.DOWN -> y+=scroll+speed
        }
        //these two lines are used if the image change his size. Are just a way to prevent random things to happen (the hitbox goes acord the image size)
        h = image.height.toFloat()
        w = image.width.toFloat()
        rectangle.set(x-w,y-h,x,y)
    }
}