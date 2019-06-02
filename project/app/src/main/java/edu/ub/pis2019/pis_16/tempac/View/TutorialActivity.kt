package edu.ub.pis2019.pis_16.tempac.View

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.support.v7.app.AppCompatActivity
import androidx.navigation.Navigation
import edu.ub.pis2019.pis_16.tempac.Model.Game.TutorialEngine
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameView
import edu.ub.pis2019.pis_16.tempac.R
import kotlinx.android.synthetic.main.activity_game.*
import edu.ub.pis2019.pis_16.tempac.Model.HomeWatcher
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Model.OnHomePressedListener

class TutorialActivity : GameActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var nav = Navigation.findNavController(this, nav_host_fragment.id)
        var bundle = Bundle()
        bundle.putInt("endGameFragmentId", R.id.gameOverTutorialFragment )
        bundle.putBoolean("isTutorial", true)
        nav.navigate(R.id.inGameFragment, bundle)

        //Home Button Watcher
        val mHomeWatcher = HomeWatcher(this)

        mHomeWatcher.setOnHomePressedListener(object : OnHomePressedListener {
            override fun onHomePressed() {
                MusicService.pauseMusic()
            }

            override fun onHomeLongPressed() {
                MusicService.pauseMusic()
            }
        })
        mHomeWatcher.startWatch()
    }

    override fun onResume() {
        super.onResume()
        MusicService.resumeMusic()
    }

    override fun onPause() {
        super.onPause()
        val pm = this.getSystemService(Context.POWER_SERVICE) as PowerManager?
        var isScreenOn = false
        if (pm != null) {
            isScreenOn = pm.isScreenOn
        }

        if (!isScreenOn) {
            MusicService.pauseMusic()
        }
    }

}
