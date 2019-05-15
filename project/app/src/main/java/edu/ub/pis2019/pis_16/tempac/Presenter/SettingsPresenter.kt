package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import edu.ub.pis2019.pis_16.tempac.View.CreditsActivity
import edu.ub.pis2019.pis_16.tempac.View.LogInActivity
import edu.ub.pis2019.pis_16.tempac.R






class SettingsPresenter(val activity: AppCompatActivity) : Presenter {

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

        activity.findViewById<Button>(R.id.replay_tutorial_button).setOnClickListener {
            changeTutorial()
        }

        activity.findViewById<Button>(R.id.credits_button).setOnClickListener{
            changeActivityCredits()
        }
        activity.findViewById<Button>(R.id.signOut_button).setOnClickListener{
            signOut()
        }


    }
    private fun signOut(){
        val  gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        mGoogleSignInClient.signOut().addOnCompleteListener(activity, OnCompleteListener<Void> {
                //Clean user data
                //Restart app
                val intent = Intent(activity,LogInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                activity.startActivity(intent)
        })
    }
    private fun changeActivityCredits(){
        val intent = Intent(activity, CreditsActivity::class.java)
        activity.startActivity(intent)
    }
    private fun changeTutorial(){
        Toast.makeText(activity, "Ahora llamaríamos al tutorial", Toast.LENGTH_LONG).show()
    }
}