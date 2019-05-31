package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import edu.ub.pis2019.pis_16.tempac.Model.GameLevel
import edu.ub.pis2019.pis_16.tempac.Model.Level


class GameEngine(context: Context) : Engine(context) {
    override var level: Level = GameLevel(initBlockImages())

    override fun update() {
        //Changes the boolean extreme temperature and changes the screen speed
        gameState(temperatureBar.temperature)

        //actualizes the temperature of the game
        level.temperature = temperatureBar.temperature
        //makes the level move downwards
        level.update(scrollSpeed)
        //makes the player not catching the top of the screen
        playerCatchingTop()

        //manages how the score is added
        scoreManagement(extremeWeather)

        //Process inputs
        player.update(scrollSpeed, screenCatchUp)

        //Process AI
        //choose WHERE have to spawn the ghosts.
        spawnGhost()

        //Process physics (cheking collisions)
        processPhysics()
        //Process animations

        //Process sound

        //Process video
    }
 }

