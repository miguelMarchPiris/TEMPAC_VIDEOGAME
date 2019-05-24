package edu.ub.pis2019.pis_16.tempac.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameEngineTutorial
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameView

class TutorialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val engine = GameEngineTutorial(this)
        setContentView(GameView(this, engine))
    }
}
