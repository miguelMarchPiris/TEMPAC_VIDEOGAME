package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.graphics.Canvas
import android.widget.Toast
import edu.ub.pis2019.pis_16.tempac.Model.Ghost
import edu.ub.pis2019.pis_16.tempac.Model.LevelTutorial
import edu.ub.pis2019.pis_16.tempac.Model.Player
import edu.ub.pis2019.pis_16.tempac.View.GameActivity
import java.util.logging.Level

class GameEngineTutorial(context : Context) : Engine(context){
    override var level = LevelTutorial() as edu.ub.pis2019.pis_16.tempac.Model.Level
    override var scrollSpeed: Float = 0f
    override var baseScrollSpeed: Float = 0f

    var currentLevel = 0
    private var tutorialLevel : LevelTutorial
    init {
        player.direction = Player.Direction.STATIC
        player.setPosition(550f,1500f)
        player.speed = 2.5f

        temperatureBar.temperature = 50f

        tutorialLevel = level as LevelTutorial
    }

    override fun update() {
        //Changes the boolean extreme temperature and changes the screen speed
        gameState(temperatureBar.temperature)
        //actualizes the temperature of the game
        level.temperature = temperatureBar.temperature

        if(currentLevel >= 2) {
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
        if(currentLevel >= 1)
            spawnGhost()

        //Process physics (cheking collisions)
        processPhysics()
        //display tutorial message
        displayMessage()
        //if player reached the top of tutorial level
        changeTutorialPart()
    }


    fun playerReachedTop(): Boolean{
        return  player.y < playingField.top + (playingField.bottom-playingField.top)*0.05f
    }

    fun displayMessage(){
        val tutorialMessage = tutorialLevel.getMessage(player.y)
        if(tutorialMessage != null)
            Toast.makeText(context.applicationContext, tutorialMessage, Toast.LENGTH_LONG).show()
    }

    fun changeTutorialPart(){
        if (!playerReachedTop()) return

        currentLevel++
        tutorialLevel.changeTutorialPart(currentLevel)
        reinitializeVariables()
        if(currentLevel > 2)
            dead = true
    }

    fun reinitializeVariables(){
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