package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import edu.ub.pis2019.pis_16.tempac.Model.HomeWatcher
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Model.OnHomePressedListener
import edu.ub.pis2019.pis_16.tempac.Presenter.database.FirestoreHandler
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.GameActivity
import edu.ub.pis2019.pis_16.tempac.View.HighScoreActivity
import edu.ub.pis2019.pis_16.tempac.View.SettingsActivity
import edu.ub.pis2019.pis_16.tempac.View.TutorialActivity

class MainMenuPresenter(val activity: AppCompatActivity) : Presenter {

    private lateinit var app : TempacApplication

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

        app = activity.application as TempacApplication

        activity.findViewById<Button>(R.id.btn_play).setOnClickListener {
            //we check if the user is in the app.
            //if(app.loadLocalUser()){
                if(app.user.firstTry){
                    app.user.firstTry = false //we set the variable to false to skip the tutorial the second time
                    FirestoreHandler.updateUser(app.user)
                    app.saveLocalUser()
                    //changing activity
                    MusicService.buttonSoundPlay(this.activity)
                    MusicService.destroyReproducer()
                    changeTutorial()
                }else{

                    //changing activity
                    MusicService.buttonSoundPlay(this.activity)
                    MusicService.destroyReproducer()
                    changeActivityPlay()
                }
            /*}else{ //user error, this should never happen xD
                Toast.makeText(
                    activity,
                    "Problem with the user; the score will not be saved",
                    Toast.LENGTH_LONG
                ).show()
                MusicService.buttonSoundPlay(this.activity)
                MusicService.destroyReproducer()
                changeActivityPlay()
            }*/


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

    private fun changeTutorial(){
        val intent = Intent(activity, TutorialActivity::class.java)
        activity.startActivity(intent)
    }
}
