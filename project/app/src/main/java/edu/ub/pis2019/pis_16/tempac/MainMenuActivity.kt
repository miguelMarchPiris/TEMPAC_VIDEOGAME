package edu.ub.pis2019.pis_16.tempac

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        btn_play.setOnClickListener {
            play(it)
        }

        btn_highscores.setOnClickListener {
            highscores(it)
        }

        btn_settings.setOnClickListener {
            settings(it)
        }
    }

    //connects with another activity passed by parameter
    fun changeActivityPlay(){
        val intent = Intent(this, GameOverActivity::class.java)
        startActivity(intent)
    }

    fun changeActivitySettings(){
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun changeActivityHighscores(){
        //val intent = Intent(this, activityName::class.java)
        startActivity(intent)
    }

    private fun play(it: View){
        changeActivityPlay()
        //TODO("not implemented")
    }

    private fun highscores(it: View){
        //changeActivity("HighscoresActivity")
        //TODO("not implemented")
    }

    private fun settings(it: View){
        changeActivitySettings()
        //TODO("not implemented")
    }
}