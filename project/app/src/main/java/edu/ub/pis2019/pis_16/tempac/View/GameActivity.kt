package edu.ub.pis2019.pis_16.tempac.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.ub.pis2019.pis_16.tempac.R
import kotlinx.android.synthetic.main.activity_game.*


//
class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        btnPause.visibility = View.GONE

        //This Part crashes the activity
        /*var animBounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
        var text : TextView = findViewById(R.id.playAgainText)
        text.startAnimation(animBounce)*/

    }

    //connects with another activity passed by parameter
    fun changeActivityMenu(){
        val intent = Intent(this,  MainMenuActivity::class.java)
        startActivity(intent)
    }


}