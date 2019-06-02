package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Context
import android.os.PowerManager
import android.support.v7.app.AppCompatActivity
import edu.ub.pis2019.pis_16.tempac.Model.HomeWatcher
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Model.OnHomePressedListener

class CreditsPresenter(val activity: AppCompatActivity) : Presenter {
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
    }
}