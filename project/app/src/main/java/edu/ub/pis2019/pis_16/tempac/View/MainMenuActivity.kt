package edu.ub.pis2019.pis_16.tempac.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import edu.ub.pis2019.pis_16.tempac.Presenter.MainMenuPresenter
import edu.ub.pis2019.pis_16.tempac.R

class MainMenuActivity : AppCompatActivity() {

    private val presenter = MainMenuPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        presenter.onCreate()
    }

    override fun onBackPressed() {
        val intent = Intent(this, ConfirmExitActivity::class.java)
        startActivity(intent)
    }
}