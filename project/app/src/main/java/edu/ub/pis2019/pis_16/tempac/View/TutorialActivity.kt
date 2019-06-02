package edu.ub.pis2019.pis_16.tempac.View

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import edu.ub.pis2019.pis_16.tempac.Model.Game.TutorialEngine
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameView
import edu.ub.pis2019.pis_16.tempac.R
import kotlinx.android.synthetic.main.activity_game.*

class TutorialActivity : GameActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var nav = Navigation.findNavController(this, nav_host_fragment.id)
        var bundle = Bundle()
        bundle.putInt("endGameFragmentId", R.id.gameOverTutorialFragment )
        bundle.putBoolean("isTutorial", true)
        nav.navigate(R.id.inGameFragment, bundle)
    }
}
