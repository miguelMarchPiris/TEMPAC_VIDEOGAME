package edu.ub.pis2019.pis_16.tempac.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import edu.ub.pis2019.pis_16.tempac.Model.HomeWatcher
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Model.OnHomePressedListener
import edu.ub.pis2019.pis_16.tempac.Presenter.GamePresenter
import edu.ub.pis2019.pis_16.tempac.R
import kotlinx.android.synthetic.main.activity_game.*


open class GameActivity : AppCompatActivity() {

    private val presenter = GamePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        var nav = Navigation.findNavController(this, nav_host_fragment.id)
        var bundle = Bundle()
        bundle.putInt("endGameFragmentId",R.id.gameOverFragment)
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

        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicService.destroyReproducer()
        MusicService.startMusicMenu(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.onRestart()
    }

    override fun onBackPressed() {

    }

}
