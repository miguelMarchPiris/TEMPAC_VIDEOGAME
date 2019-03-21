package edu.ub.pis2019.pis_16.tempac

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

    private fun play(it: View){
        TODO("not implemented")
    }

    private fun highscores(it: View){
        TODO("not implemented")
    }

    private fun settings(it: View){
        TODO("not implemented")
    }
}