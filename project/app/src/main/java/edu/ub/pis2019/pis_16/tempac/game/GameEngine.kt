package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.MotionEvent


class GameEngine:Drawable{

    private var scrollSpeed = 1.5f
    private var temperatureBar = TemperatureBar()

    private var gfactory : GhostFactory=GhostFactory()
    private var ghosts : List<Ghost> = mutableListOf<Ghost>()
    private var player : Player = Player()
    private var level : Level= Level()

    /*TEMPORALY TO TEST BEGIN*/
    private var orb : Orb = Orb(530f, 800f, "add", 4)
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
    }
    fun update(){
        //Process state of the game
        temperatureBar.temperature+=0.1f //Just to test the temperature bar
        //Process inputs
        player.update(scrollSpeed)
        //Process AI

        //Process physics

        //Process animations

        //Process sound

        //Process video

        //temporaly:

    }
    override fun draw(canvas: Canvas?){
        if (canvas != null) {
            canvas.drawColor(Color.BLACK)

            player.draw(canvas)
            temperatureBar.draw(canvas)
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
}