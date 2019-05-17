package edu.ub.pis2019.pis_16.tempac.Presenter

import android.app.Application
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.GameOverFragment

class GameOverPresenter(private val fragment:GameOverFragment) : Presenter {
    override fun onResume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {
        val inflatedView = fragment.inflatedView
        var animBounce = AnimationUtils.loadAnimation(fragment.context, R.anim.bounce)
        var text : TextView = inflatedView.findViewById(R.id.playAgainText)
        text.startAnimation(animBounce)

        var menuButton = inflatedView.findViewById<Button>(R.id.menuButton)
        var shareButton = inflatedView.findViewById<Button>(R.id.shareButton)
        var backgroundLayout = inflatedView.findViewById<ConstraintLayout>(R.id.backgroundLayout)

        menuButton.setOnClickListener{
            //Menu Button Action
            changeActivityMenu()
        }

        shareButton.setOnClickListener{
            //Menu Button Action
            share()
        }

        backgroundLayout.setOnClickListener{
            //Menu Button Action
            playAgain()
        }
    }
    fun changeActivityMenu(){
        fragment.activity?.finish()
    }
    fun share(){

    }
    fun playAgain(){
        var nav = Navigation.findNavController(fragment.view!!)
        nav.navigate(R.id.inGameFragment)
    }
    fun processArgs(arguments: Bundle?){
        val user = (fragment.activity!!.application as TempacApplication).user


        val score = arguments?.getInt("score")
        if(score!=null) {
            //Score display
            val scoreText = fragment.inflatedView.findViewById<TextView>(R.id.yourScoreResultText)
            scoreText?.text = score.toString()

            //We add the highscore to the user (if the new score is lower it wont change)
            user.setHighscore(score)
            FirestoreHandler.updateUser(user)

            //Display highscore
            val highScoreText = fragment.inflatedView.findViewById<TextView>(R.id.youtHighScoreResultText)
            highScoreText.text = user.getHighscore().toString()
            //Display overall highscore
            val overallHighScoreText = fragment.inflatedView.findViewById<TextView>(R.id.overallHighScoreResultText)
            //TODO("Display overall highscore")


        }
    }
}