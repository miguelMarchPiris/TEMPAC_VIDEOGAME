package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent


class GameEngine:Drawable{

    private var scrollSpeed = 1.5f
    private var temperatureBar = TemperatureBar()

    private var gfactory : GhostFactory=GhostFactory()
    private var ghosts : List<Ghost> = mutableListOf<Ghost>()
    private var player : Player = Player()
    private var level : Level= Level()

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
        //temperatureBar.temperature+=0.1f //Just to test the temperature bar
        //Process inputs
        player.update(scrollSpeed)
        level.update(scrollSpeed)
        //Process AI
        //checks if the playes collides with a block
        checkCollisionsBlock()
        checkCollisionsOrb()
        //breakableState() //This function should check when the temperature is appropiate to change the Breakable to true: Problem? WhichBlocks?

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
            level.draw(canvas)

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

    fun checkCollisionsBlock(){
        for(block in level.blocks) {
            //if they collide and is not breakable
            if (RectF.intersects(block.rectangle, player.rectangle) && !block.breakable) {
                //If we change the player image we may change the numbers for the collisions
                when (player.direction) {
                    Player.Direction.UP -> player.setPosition(player.x, player.y + 5 + scrollSpeed)
                    Player.Direction.DOWN -> player.setPosition(player.x, player.y - 5 - scrollSpeed)
                    Player.Direction.LEFT -> player.setPosition(player.x + 5, player.y)
                    Player.Direction.RIGHT -> player.setPosition(player.x - 5, player.y)
                    Player.Direction.STATIC -> player.setPosition(player.x, player.y + scrollSpeed)
                }
                player.direction = Player.Direction.STATIC

            }else if(RectF.intersects(block.rectangle, player.rectangle) && block.breakable){
                level.blocks.remove(block)
            }
        }

        /* Maybe this is faster? idk
        if(block.breakable){
            if (RectF.intersects(block.rectangle, player.rectangle)) {
                //If we change the player image we may change the numbers for the collisions
                when (player.direction) {
                    Player.Direction.UP -> player.setPosition(player.x, player.y + 5 + scrollSpeed)
                    Player.Direction.DOWN -> player.setPosition(player.x, player.y - 5 - scrollSpeed)
                    Player.Direction.LEFT -> player.setPosition(player.x + 5, player.y)
                    Player.Direction.RIGHT -> player.setPosition(player.x - 5, player.y)
                }
                player.direction = Player.Direction.STATIC

        }else{
            if(RectF.intersects(block.rectangle, player.rectangle)) level.blocks.remove(block)
        }

        */
    }

    fun checkCollisionsOrb(){
        //Maybe the deleting is the lag (i dont think so) but i really suspect the problem is the thermometer
        //var orbsChecked : MutableList<Int> = mutableListOf<Int>()
        for(orb in level.orbs){
            if(RectF.intersects(orb.rectangle,player.rectangle)){
                temperatureBar.changeTemperature(orb)
                level.orbs.remove(orb)
            }
        }
    }

    fun breakableState(){
        //TODO() //probably we should add a list in level that marks the possible changeable blocks (Ask Miguel)
    }
}