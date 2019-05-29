package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Intent
import android.media.MediaPlayer
import android.provider.MediaStore
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.GameActivity
import edu.ub.pis2019.pis_16.tempac.View.HighScoreActivity
import edu.ub.pis2019.pis_16.tempac.View.SettingsActivity

class MainMenuPresenter(val activity: AppCompatActivity) : Presenter {

    private lateinit var app : TempacApplication

    override fun onResume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        app = (activity.application as TempacApplication)

        var buttonPlayer : MediaPlayer? = MediaPlayer.create(this.activity, R.raw.btn_sound)

        activity.findViewById<Button>(R.id.btn_play).setOnClickListener {
            buttonPlayer?.start()
            changeActivityPlay()
        }

        activity.findViewById<Button>(R.id.btn_highscores).setOnClickListener {
            buttonPlayer?.start()
            changeActivityHighscores()
        }

        activity.findViewById<Button>(R.id.btn_settings).setOnClickListener {
            buttonPlayer?.start()
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
