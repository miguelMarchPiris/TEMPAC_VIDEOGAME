package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import edu.ub.pis2019.pis_16.tempac.Model.HomeWatcher
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Model.OnHomePressedListener
import edu.ub.pis2019.pis_16.tempac.Presenter.database.FirestoreHandler
import edu.ub.pis2019.pis_16.tempac.View.CreditsActivity
import edu.ub.pis2019.pis_16.tempac.View.LogInActivity
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.ChooseUsernameActivity
import edu.ub.pis2019.pis_16.tempac.View.TutorialActivity
import java.lang.Exception
import android.os.PowerManager





class SettingsPresenter(val activity: AppCompatActivity) : Presenter {

    private val RC_USERNAME = 420
    private lateinit var app:TempacApplication

    override fun onResume() {
        MusicService.resumeMusic()
    }

    override fun onPause() {
        val pm = activity.getSystemService(Context.POWER_SERVICE) as PowerManager?
        var isScreenOn = false
        if (pm != null) {
            isScreenOn = pm.isScreenOn
        }

        if (!isScreenOn) {
            MusicService.pauseMusic()
        }
    }

    override fun onRestart() {
        //MusicService.resumeMusic()
    }

    override fun onCreate() {
        app = (activity.application as TempacApplication)

        //Home Button Watcher
        val mHomeWatcher = HomeWatcher(this.activity)

        mHomeWatcher.setOnHomePressedListener(object : OnHomePressedListener {
            override fun onHomePressed() {
                MusicService.pauseMusic()
            }

            override fun onHomeLongPressed() {
                MusicService.pauseMusic()
            }
        })
        mHomeWatcher.startWatch()

        activity.findViewById<Button>(R.id.replay_tutorial_button).setOnClickListener {
            MusicService.buttonSoundPlay(this.activity)
            MusicService.destroyReproducer()
            changeTutorial()
        }

        activity.findViewById<Button>(R.id.credits_button).setOnClickListener{
            MusicService.buttonSoundPlay(this.activity)
            changeActivityCredits()
        }
        val signOutButton = activity.findViewById<Button>(R.id.signOut_button)
        signOutButton.setOnClickListener{
            MusicService.buttonSoundPlay(this.activity)
            MusicService.destroyReproducer()
            signOut()
        }
        val changeUsernameButton = activity.findViewById<Button>(R.id.changeUsername_button)
        changeUsernameButton.setOnClickListener{
            MusicService.buttonSoundPlay(this.activity)
            changeUsername()
        }
        if(!app.user.isGoogleUser())
        {
            changeUsernameButton.visibility = View.VISIBLE
            changeUsernameButton.invalidate()
        }

        val audioManager : AudioManager = activity.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val volumeSeekbar : SeekBar = activity.findViewById(R.id.sB_volume)

        volumeSeekbar.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        volumeSeekbar.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        volumeSeekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(arg0 : SeekBar) {}
            override fun onStartTrackingTouch(arg0: SeekBar) {}
            override fun onProgressChanged(arg0: SeekBar, progress: Int, arg2: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }
        })
    }

    private fun changeUsername(){
        val intent = Intent(activity, ChooseUsernameActivity::class.java)
        activity.startActivityForResult(intent, RC_USERNAME)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Result returned form launching intent ChooseUsername
        var customUsername = ""
        if (requestCode == RC_USERNAME) {
            if (data?.getStringExtra("username") != null)
                customUsername = data?.getStringExtra("username")
            //Afegim usuari amb ID la del dispositiu
            if (customUsername != "") {
                app.user.username = customUsername
                app.saveLocalUser()
                FirestoreHandler.updateUser(app.user)
                Toast.makeText(
                    activity,
                    "Succesfully changed username to: " + app.user.username,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun signOut(){
        var msg = "Succesfully logged out "
        try {
            FirebaseAuth.getInstance().signOut()
            msg += "cloud "
        }catch (e:Exception){
            e.printStackTrace()
        }

        if((activity.application as TempacApplication).signOutLocalUser())
        {
            msg += "local "
        }
        Toast.makeText(activity,
            msg + "user: " + app.user.username,
            Toast.LENGTH_SHORT
        ).show()
        //Restart app
        val intent = Intent(activity,LogInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)

    }

    private fun changeActivityCredits(){
        val intent = Intent(activity, CreditsActivity::class.java)
        activity.startActivity(intent)
    }

    private fun changeTutorial(){
        val intent = Intent(activity,TutorialActivity::class.java)
        activity.startActivity(intent)
    }
}