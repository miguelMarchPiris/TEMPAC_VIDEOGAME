package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Presenter.database.FirestoreHandler
import edu.ub.pis2019.pis_16.tempac.View.CreditsActivity
import edu.ub.pis2019.pis_16.tempac.View.LogInActivity
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.ChooseUsernameActivity
import java.lang.Exception


class SettingsPresenter(val activity: AppCompatActivity) : Presenter {

    private val RC_USERNAME = 420
    private lateinit var app:TempacApplication

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
        app = (activity.application as TempacApplication)
        activity.findViewById<Button>(R.id.replay_tutorial_button).setOnClickListener {
            MusicService.buttonSoundPlay(this.activity)
            changeTutorial()
        }

        activity.findViewById<Button>(R.id.credits_button).setOnClickListener{
            MusicService.buttonSoundPlay(this.activity)
            changeActivityCredits()
        }
        val signOutButton = activity.findViewById<Button>(R.id.signOut_button)
        signOutButton.setOnClickListener{
            MusicService.buttonSoundPlay(this.activity)
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
        Toast.makeText(activity, "Ahora llamaríamos al tutorial", Toast.LENGTH_LONG).show()
    }
}