package edu.ub.pis2019.pis_16.tempac

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class GameOverActivity : AppCompatActivity() {

    lateinit var menuButton: Button
    lateinit var shareButton: Button
    lateinit var backgroundLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        var animBounce = AnimationUtils.loadAnimation(this, R.anim.bounce)
        var text : TextView = findViewById(R.id.playAgainText)
        text.startAnimation(animBounce)

        menuButton = findViewById(R.id.menuButton)
        shareButton = findViewById(R.id.shareButton)
        backgroundLayout = findViewById(R.id.backgroundLayout)

        menuButton.setOnClickListener{
            //Menu Button Action
            Toast.makeText(baseContext,"Menu Button Action",Toast.LENGTH_SHORT).show()
        }

        shareButton.setOnClickListener{
            //Menu Button Action
            Toast.makeText(baseContext,"Share Button Action",Toast.LENGTH_SHORT).show()
        }

        backgroundLayout.setOnClickListener{
            //Menu Button Action
            Toast.makeText(baseContext,"Background Layout Action",Toast.LENGTH_SHORT).show()
        }
    }
}
