package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import edu.ub.pis2019.pis_16.tempac.Model.HomeWatcher
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Model.OnHomePressedListener
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.MainMenuActivity

class ConfirmExitPresenter(val activity: AppCompatActivity) : Presenter {
    override fun onResume() {
        MusicService.resumeMusic()
    }

    override fun onPause() {
        val pm = activity.getSystemService(Context.POWER_SERVICE) as PowerManager?
        var isScreenOn = false
        if (pm != null) {
            isScreenOn = pm.isScreenOn
        }

        if (!isScreenOn) {
            MusicService.pauseMusic()
        }
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

        activity.findViewById<Button>(R.id.btn_Yes).setOnClickListener {
            MusicService.buttonSoundPlay(this.activity)
            MusicService.destroyReproducer()
            ActivityCompat.finishAffinity(this.activity)
        }

        activity.findViewById<Button>(R.id.btn_No).setOnClickListener {
            MusicService.buttonSoundPlay(this.activity)
            activity.finish()
        }
    }


    fun changeActivityMainMenu(){
        val intent = Intent(this.activity, MainMenuActivity::class.java)
        activity.startActivity(intent)
    }
}