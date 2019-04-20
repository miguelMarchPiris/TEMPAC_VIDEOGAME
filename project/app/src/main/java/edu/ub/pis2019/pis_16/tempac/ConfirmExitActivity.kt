package edu.ub.pis2019.pis_16.tempac

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_confirm_exit.*

class ConfirmExitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btn_Yes.setOnClickListener {
            //finishAffinity()  ;TODO ckeck this please
            System.exit(0)
        }

        btn_No.setOnClickListener {
            changeActivityMainMenu()
        }

        setContentView(R.layout.activity_confirm_exit)

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        var width = dm.widthPixels
        var height = dm.heightPixels

        window.setLayout((width*.8).toInt(), (height*.6).toInt())
    }

    fun changeActivityMainMenu(){
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }
}
