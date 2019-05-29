package edu.ub.pis2019.pis_16.tempac.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.R


//
class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        MusicService.startMusicGame(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicService.destroyReproducer()
        MusicService.startMusicMenu(this)
    }

    override fun onBackPressed() {

    }

}
