package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import edu.ub.pis2019.pis_16.tempac.Model.HomeWatcher
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Model.OnHomePressedListener
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.GameActivity
import edu.ub.pis2019.pis_16.tempac.View.HighScoreActivity
import edu.ub.pis2019.pis_16.tempac.View.SettingsActivity

class MainMenuPresenter(val activity: AppCompatActivity) : Presenter {

    override fun onResume() {
        MusicService.resumeMusic()
    }

    override fun onPause() {
        //MusicService.pauseMusic()
    }

    override fun onRestart() {
        //MusicService.resumeMusic()
    }
    override fun onDestroy() {
        MusicService.buttonPlayerDestroyer()
    }

    override fun onCreate() {

        //Home Button Watcher
        val mHomeWatcher = HomeWatcher(this.activity)

        mHomeWatcher.setOnHomePressedListener(object : OnHomePressedListener {
            override fun onHomePressed() {
                MusicService.pauseMusic()
            }

            override fun onHomeLongPressed() {
                MusicService.pauseMusic()
            }
        })
        mHomeWatcher.startWatch()

        activity.findViewById<Button>(R.id.btn_play).setOnClickListener {
            MusicService.buttonSoundPlay(this.activity)
            MusicService.destroyReproducer()
            changeActivityPlay()
        }

        activity.findViewById<Button>(R.id.btn_highscores).setOnClickListener {
            MusicService.buttonSoundPlay(this.activity)
            changeActivityHighscores()
        }

        activity.findViewById<Button>(R.id.btn_settings).setOnClickListener {
            MusicService.buttonSoundPlay(this.activity)
            changeActivitySettings()
        }
    }

    private fun changeActivityPlay(){
        val intent = Intent(this.activity, GameActivity::class.java)
        activity.startActivity(intent)
    }

    private fun changeActivitySettings(){
        val intent = Intent(this.activity, SettingsActivity::class.java)
        activity.startActivity(intent)
    }

    private fun changeActivityHighscores(){
        val intent = Intent(this.activity, HighScoreActivity::class.java)
        activity.startActivity(intent)
    }
}
