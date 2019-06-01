package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.graphics.Canvas
import android.widget.Toast
import edu.ub.pis2019.pis_16.tempac.Model.*

class TutorialEngine(context : Context) : Engine(context){

    override var scrollSpeed: Float = 0f
    override var baseScrollSpeed: Float = 0f

    var currentLevel = 0
    override var level: Level = TutorialLevel(initBlockImages())
    private var tutorialLevel : TutorialLevel

    init {
        player.direction = Player.Direction.STATIC
        player.setPosition(550f,1500f)
        player.speed = 2.5f

        temperatureBar.temperature = 0f

        tutorialLevel = level as TutorialLevel
    }

    override fun update() {
        //Changes the boolean extreme temperature and changes the screen speed
        gameState(temperatureBar.temperature)
        //actualizes the temperature of the game
        level.temperature = temperatureBar.temperature

        //Process inputs
        player.update(scrollSpeed, screenCatchUp)

        //Process physics (cheking collisions)
        processPhysics()
        //display tutorial message


        if(currentLevel >= 2) {
            //makes the level move downwards
            level.update(scrollSpeed)

            //makes the player not catching the top of the screen
            playerCatchingTop()
            //manages how the score is added
            scoreManagement(extremeWeather)
        }


        if(currentLevel >= 1) {
            spawnGhost()
        }

        //if player reached the top of tutorial level
        changeTutorialPart()

        displayMessage()
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        if(dead)
            canvas!!.drawText("Tutorial has finished!\nNow you are ready for real game!", 1080 / 2f, PLAYFIELD_HEIGTH/2f, textPaint)
        canvas!!.drawText(temporaryMessage, 1080 / 2f, PLAYFIELD_HEIGTH + 400f, textPaint)
    }


    private fun playerReachedTop(): Boolean{
        return  player.y < playingField.top + (playingField.bottom-playingField.top)*0.05f
    }

    var temporaryMessage : String = ""
    private fun displayMessage(){
        val tutorialMessage = tutorialLevel.getMessage(player.y)
        if(tutorialMessage != null) {
            Toast.makeText(context.applicationContext, tutorialMessage, Toast.LENGTH_LONG).show()
            temporaryMessage = temporaryMessage //only temporary(?) solution
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

}