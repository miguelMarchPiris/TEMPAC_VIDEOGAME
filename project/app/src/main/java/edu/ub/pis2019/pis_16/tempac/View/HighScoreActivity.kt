package edu.ub.pis2019.pis_16.tempac.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import edu.ub.pis2019.pis_16.tempac.Presenter.HighScorePresenter
import edu.ub.pis2019.pis_16.tempac.R

class HighScoreActivity : AppCompatActivity() {
    private val presenter = HighScorePresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)
        presenter.onCreate()
    }
}

