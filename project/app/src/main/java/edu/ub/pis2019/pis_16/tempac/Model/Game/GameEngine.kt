package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import edu.ub.pis2019.pis_16.tempac.Model.*
import edu.ub.pis2019.pis_16.tempac.R
import java.util.*
import edu.ub.pis2019.pis_16.tempac.Model.GameLevel
import edu.ub.pis2019.pis_16.tempac.Model.Level

open class GameEngine(context : Context) : Engine(context) {

    //Actors
    override var level : Level = GameLevel()
    override var dead = false

    init {


    }

    override fun update(){

        //UPDATE GAME STATE
        //Changes the boolean extreme temperature and changes the screen speed
        gameState(temperatureBar.temperature)
        //updates the temperature of the game
        level.temperature = temperatureBar.temperature
        //makes the level move downwards
        level.update(scrollSpeed)
        //makes the player not catch up to the top of the screen
        playerCatchingTop()
        //manages how the score is added
        scoreManagement(extremeWeather)
        //Process inputs
        player.update(scrollSpeed, screenCatchUp)

        //PROCESS IA
        //spawn the ghosts
        spawnGhost()
        //Update ghosts
        for(ghost in ghosts){
            //this push the ghosts up til are visible for the player.
            val belowTheLine=ghost.y > BOTTOM_PLAYING_FIELD
            ghost.update(scrollSpeed, Pair(player.x,player.y), level.get3RowsAtY(ghost.y+scrollSpeed),belowTheLine, temperatureBar.temperature)
        }
        //Update dying ghosts
        for(ghost in dyingGhosts){
            ghost.update(scrollSpeed, Pair(player.x,player.y), level.get3RowsAtY(ghost.y+scrollSpeed),false, temperatureBar.temperature)
        }


        //PROCESS PHYSICS (checking collisions)
        processPhysics()


        //process animations
        processAnimations()


        //Process sound

        //Process video
    }

}