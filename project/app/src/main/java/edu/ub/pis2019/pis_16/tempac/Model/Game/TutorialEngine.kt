package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import edu.ub.pis2019.pis_16.tempac.Model.Level
import edu.ub.pis2019.pis_16.tempac.Model.TutorialLevel
import edu.ub.pis2019.pis_16.tempac.Model.Player

class TutorialEngine(context : Context) : Engine(context){
    override var scrollSpeed: Float = 0f
    override var baseScrollSpeed: Float = 0f
    var currentLevel = 0
    private var waitingForInput = false
    override var level: Level = TutorialLevel()
    private var tutorialLevel : TutorialLevel
    private var displayMessage : String = ""
    private val transPaint = Paint()
    init {
        player.direction = Player.Direction.STATIC
        player.setPosition(550f,1500f)
        player.speed = 2.5f
        transPaint.color = Color.BLACK
        transPaint.alpha = 100
        temperatureBar.temperature = 50f
        tutorialLevel = level as TutorialLevel
    }

    override fun update() {
        if(!waitingForInput) {
            //Changes the boolean extreme temperature and changes the screen speed
            gameState(temperatureBar.temperature)
            //actualizes the temperature of the game
            level.temperature = temperatureBar.temperature

            if (currentLevel >= 2) {
                //makes the level move downwards
                level.update(scrollSpeed)

                //makes the player not catching the top of the screen
                playerCatchingTop()
                //manages how the score is added
                scoreManagement(extremeWeather)
            }

            //Process inputs
            player.update(scrollSpeed, screenCatchUp)

            //Process AI
            //choose WHERE have to spawn the ghosts.
            if (currentLevel >= 1)
                spawnGhost()

            //Process physics (cheking collisions)
            processPhysics()
            //display tutorial message
            displayMessage()
            //if player reached the top of tutorial level
            changeTutorialPart()
        }
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas!!.drawText(player.x.toString() + " - " + player.y.toString(), 1080 / 2f, PLAYFIELD_HEIGTH/2f, textPaint)
        if(dead)
            canvas!!.drawText("Tutorial has finished!\nNow you are ready for real game!", 1080 / 2f, PLAYFIELD_HEIGTH/2f, textPaint)
        if(waitingForInput && displayMessage!= ""){
            canvas!!.drawPaint(transPaint)
            canvas!!.drawText(displayMessage, 1080 / 2f, PLAYFIELD_HEIGTH/2f, textPaint)
        }
        else if(displayMessage!= "")
            canvas!!.drawText(displayMessage, 1080 / 2f, PLAYFIELD_HEIGTH + 370f, textPaint)


    }


    private fun playerReachedTop(): Boolean{
        return  player.y < playingField.top + (playingField.bottom-playingField.top)*0.05f
    }


    private fun displayMessage(){
        val tutorialMessage = tutorialLevel.getMessage(player.rectangle)
        //We got a new message!
        if(tutorialMessage != "" && tutorialMessage!=null) {
            waitingForInput = true
            displayMessage = tutorialMessage
        }
    }

    private fun changeTutorialPart(){
        if (!playerReachedTop()) return

        currentLevel++
        tutorialLevel.changeTutorialPart(currentLevel)
        reinitializeVariables()
        if(currentLevel > 2)
            dead = true
    }

    private fun reinitializeVariables(){
        when(currentLevel){
            0 -> {
                player.setPosition(550f,1500f)
                player.direction = Player.Direction.STATIC
            }
            1 -> {
                player.setPosition(550f,1500f)
                player.direction = Player.Direction.UP
            }
            2 -> {
                player.setPosition(550f,1500f)
                player.direction = Player.Direction.UP
                ghosts = mutableListOf()
            }
        }
    }
    override fun processInput(event: MotionEvent){
        super.processInput(event)
        waitingForInput = false
    }

}