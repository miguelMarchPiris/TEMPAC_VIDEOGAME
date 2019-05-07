package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import android.view.Display


class GameEngine:Drawable{

    private var scrollSpeed = 1.5f
    private var temperatureBar = TemperatureBar()

    private var gfactory : GhostFactory=GhostFactory()
    private var ghosts : List<Ghost> = mutableListOf<Ghost>()
    private var player : Player = Player()
    private var level : Level= Level()
    private val playingField = RectF(0f, 300f,1080f,1700f)
    private val fieldPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    /*TEMPORALY TO TEST BEGIN*/
    private var orb : Orb = Orb(500f, 800f, "add", 4)
    /*TEMPORALY TO TEST ENDS*/

    private var touchStartX = 0f
    private var touchStartY = 0f
    private var touchEndX = 0f
    private var touchEndY = 0f
    private val MIN_DISTANCE = 105f

    init {
        temperatureBar.x = 100f
        temperatureBar.y = 100f
        temperatureBar.temperature = 0f

        //fieldPaint.style = Paint.Style.STROKE
        fieldPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
    fun update(){
        //Process state of the game
        temperatureBar.temperature+=0.1f //Just to test the temperature bar
        //Process inputs
        player.update(scrollSpeed)
        level.update(scrollSpeed)
        //Process AI
        //checks if the playes collides with a block
        checkColisionsBlock()

        //Process physics


        //Process animations

        //Process sound

        //Process video

        //temporaly:
        orb.update(scrollSpeed)


    }
    override fun draw(canvas: Canvas?){
        if (canvas != null) {
            canvas.drawColor(Color.BLACK)
            temperatureBar.draw(canvas)
            player.draw(canvas)
            level.draw(canvas)
            orb.draw(canvas)
            //canvas.drawColor(Color.BLACK)
            canvas.drawRect(playingField,fieldPaint)
        }

    }
    fun processInput(event: MotionEvent){
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN ->{
                touchStartX = event.x
                touchStartY = event.y
            }
            MotionEvent.ACTION_UP ->{
                touchEndX = event.x
                touchEndY = event.y

                val deltaX = touchEndX-touchStartX
                val deltaY = touchEndY-touchStartY
                Log.println(Log.VERBOSE,"TOUCH INPUT", deltaX.toString() + " - " + deltaY.toString())
                if (Math.abs(deltaY) > MIN_DISTANCE && Math.abs(deltaY) > Math.abs(deltaX))//swipe up-down
                {
                    if(deltaY < 0)//UP
                        player.direction = Player.Direction.UP
                    else
                        player.direction = Player.Direction.DOWN //down

                }
                else if(Math.abs(deltaX) > MIN_DISTANCE){ //swipe left-right
                    if(deltaX< 0)    //LEFT
                        player.direction = Player.Direction.LEFT
                    else//right
                        player.direction = Player.Direction.RIGHT

                }
                else
                    player.direction = Player.Direction.STATIC

            }
        }
    }

    fun checkColisionsBlock(){
        for(block in level.blocks) {
            if (RectF.intersects(block.rectangle, player.rectangle)) {
                //When we use images we'll increase the rectangle hitbox to prevent the orb clipping the player.
                when (player.direction) {
                    Player.Direction.UP -> player.setPosition(player.x, player.y+6)
                    Player.Direction.DOWN -> player.setPosition(player.x, player.y - 6)
                    Player.Direction.LEFT -> player.setPosition(player.x + 6, player.y)
                    Player.Direction.RIGHT -> player.setPosition(player.x - 6, player.y)
                }
                player.direction = Player.Direction.STATIC

            }
        }
    }
}