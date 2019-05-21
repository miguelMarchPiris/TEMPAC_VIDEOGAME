package edu.ub.pis2019.pis_16.tempac.Presenter

import android.app.Application
import android.content.Intent
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import edu.ub.pis2019.pis_16.tempac.Model.User
import edu.ub.pis2019.pis_16.tempac.Presenter.database.FirestoreHandler
import edu.ub.pis2019.pis_16.tempac.View.CreditsActivity
import edu.ub.pis2019.pis_16.tempac.View.LogInActivity
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.ChooseUsernameActivity
import edu.ub.pis2019.pis_16.tempac.View.MainMenuActivity
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_settings.view.*
import kotlin.math.sign


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
            changeTutorial()
        }

        activity.findViewById<Button>(R.id.credits_button).setOnClickListener{
            changeActivityCredits()
        }
        val signOutButton = activity.findViewById<Button>(R.id.signOut_button)
        signOutButton.setOnClickListener{
            signOut()
        }
        val changeUsernameButton = activity.findViewById<Button>(R.id.changeUsername_button)
        changeUsernameButton.setOnClickListener{
            changeUsername()
        }
        when(app.user.isGoogleUser()){
            true -> {
                changeUsernameButton.visibility = View.GONE
                changeUsernameButton.invalidate()
            }
            false ->{
                signOutButton.visibility = View.GONE
                signOutButton.invalidate()
            }
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
                app.saveUser()
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
        FirebaseAuth.getInstance().signOut()
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