package edu.ub.pis2019.pis_16.tempac.View

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import edu.ub.pis2019.pis_16.tempac.Presenter.ConfirmExitPresenter
import edu.ub.pis2019.pis_16.tempac.R

class ConfirmExitActivity : AppCompatActivity() {

    private val presenter = ConfirmExitPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = ""

        setContentView(R.layout.activity_confirm_exit)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        var width = dm.widthPixels
        var height = dm.heightPixels

        window.setLayout((width*.8).toInt(), (height*.6).toInt())

        presenter.onCreate()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        presenter.onRestart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
