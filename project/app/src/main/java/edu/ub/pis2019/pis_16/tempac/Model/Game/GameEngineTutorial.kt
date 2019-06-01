package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.graphics.Canvas
import edu.ub.pis2019.pis_16.tempac.Model.LevelTutorial
import java.util.logging.Level

class GameEngineTutorial(context : Context) : GameEngine(context){


    override var level = LevelTutorial() as edu.ub.pis2019.pis_16.tempac.Model.Level

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }


}