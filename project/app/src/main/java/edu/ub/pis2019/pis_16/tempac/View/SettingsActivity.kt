package edu.ub.pis2019.pis_16.tempac.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import edu.ub.pis2019.pis_16.tempac.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        replay_tutorial_button.setOnClickListener {
            changeTutorial()
        }

        credits_button.setOnClickListener{
            changeActivityCredits()
        }

    }
    fun changeActivityCredits(){
        val intent = Intent(this, CreditsActivity::class.java)
        startActivity(intent)
    }
    fun changeTutorial(){
        var T: Toast =Toast.makeText(this, "Ahora llamaríamos al tutorial", Toast.LENGTH_LONG)
        T.show()
    }
}



