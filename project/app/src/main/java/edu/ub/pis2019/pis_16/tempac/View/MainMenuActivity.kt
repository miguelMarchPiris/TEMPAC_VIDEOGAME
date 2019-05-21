package edu.ub.pis2019.pis_16.tempac.View

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.ub.pis2019.pis_16.tempac.R
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    private var buttonPlayer : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        buttonPlayer = MediaPlayer.create(this, R.raw.btn_sound)

        btn_play.setOnClickListener {
            play(it)
        }

        btn_highscores.setOnClickListener {
            buttonPlayer?.start()
            highscores(it)
        }

        btn_settings.setOnClickListener {
            settings(it)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, ConfirmExitActivity::class.java)
        startActivity(intent)
    }

    //connects with another activity passed by parameter
    fun changeActivityPlay(){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun changeActivitySettings(){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun changeActivityHighscores(){
        val intent = Intent(this, HighScoreActivity::class.java)
        startActivity(intent)
    }

    private fun play(it: View){
        changeActivityPlay()
        //TODO("not implemented")
    }

    private fun highscores(it: View){
        changeActivityHighscores()
        //TODO("not implemented")
    }

    private fun settings(it: View){
        changeActivitySettings()
        //TODO("not implemented")
    }
}