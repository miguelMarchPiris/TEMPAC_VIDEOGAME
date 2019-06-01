package edu.ub.pis2019.pis_16.tempac.Presenter

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Presenter.database.DatabaseCallback
import edu.ub.pis2019.pis_16.tempac.Presenter.database.FirestoreHandler
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.GameOverFragment
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent



class GameOverPresenter(private val fragment:GameOverFragment) : Presenter,
    DatabaseCallback {

    override fun onResume() {
        MusicService.resumeMusic()
    }

    override fun onPause() {
        //MusicService.pauseMusic()
    }

    override fun onRestart() {
        //MusicService.resumeMusic()
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
            MusicService.buttonSoundPlay(fragment.context)
            //Menu Button Action
            changeActivityMenu()
        }

        shareButton.setOnClickListener{
            MusicService.buttonSoundPlay(fragment.context)
            //Share Button Action
            share()
        }

        backgroundLayout.setOnClickListener{
            //Background Button Action
            playAgain()
        }
    }
    fun changeActivityMenu(){
        fragment.activity?.finish()
    }
    fun share(){
        val scoreText = fragment.inflatedView.findViewById<TextView>(R.id.yourScoreResultText).text.toString()
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, "My new score in Tempac is "+scoreText+" can you beat it?")
        sendIntent.type = "text/plain"
        fragment.activity?.startActivity(sendIntent)
    }
    fun playAgain(){
        var nav = Navigation.findNavController(fragment.view!!)
        nav.navigate(R.id.inGameFragment)
    }
    fun processArgs(arguments: Bundle?){
        val app = (fragment.activity!!.application as TempacApplication)
        val user = app.user

        val score = arguments?.getInt("score")
        if(score!=null) {
            //Score display
            val scoreText = fragment.inflatedView.findViewById<TextView>(R.id.yourScoreResultText)
            scoreText?.text = score.toString()

            //We add the highscore to the user (if the new score is lower it wont change)
            user.setHighscore(score)
            //Save user locally
            app.saveLocalUser()
            //Save user in database
            FirestoreHandler.updateUser(user)

            //Display highscore
            val highScoreText = fragment.inflatedView.findViewById<TextView>(R.id.youtHighScoreResultText)
            highScoreText.text = user.getHighscore().toString()
            //Display overall highscore
            val overallHighScoreText = fragment.inflatedView.findViewById<TextView>(R.id.overallHighScoreResultText)
            overallHighScoreText.text = "Loading. . ."
            FirestoreHandler.getOverallHighsscore(this)
            //Displau name
            val nameText = fragment.inflatedView.findViewById<TextView>(R.id.yourNameResultText)
            nameText.text = user.username
        }
    }

    override fun handleHighscore(highscore: Pair<String, Int>) {
        fragment.inflatedView.findViewById<TextView>(R.id.overallHighScoreResultText).text = highscore.second.toString()
        fragment.inflatedView.findViewById<TextView>(R.id.overallHighScoreResultText).invalidate()
    }
}