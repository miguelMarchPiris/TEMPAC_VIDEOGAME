package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import edu.ub.pis2019.pis_16.tempac.View.MainMenuActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import edu.ub.pis2019.pis_16.tempac.R
import com.google.android.gms.common.api.ApiException
import android.util.Log


class LogInPresenter(val activity: AppCompatActivity) : Presenter {
    // Configure sign-in to request the user's ID, email address, and basic
    // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN = 69
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
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        // Set the dimensions of the sign-in button.
        val signInButton = activity.findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setSize(SignInButton.SIZE_WIDE)
        //Set the button's listener
        activity.findViewById<SignInButton>(R.id.sign_in_button).setOnClickListener{
            askForlogin()
        }
        //Set the skip login listener
        activity.findViewById<Button>(R.id.button_skipLogin).setOnClickListener {
            skipLogin()
        }
    }
    fun onStart(){
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(activity)
        if(account != null){
            login(account)
        }
    }
    fun askForlogin(){
        val signInIntent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent,RC_SIGN)
    }
    //This is called after the user selects the account to log in
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if(requestCode == RC_SIGN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            try {
                val account = task.getResult(ApiException::class.java)

                // Signed in successfully, show authenticated UI.
                login(account)
            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.w("GOOGLE", "signInResult:failed code=" + e.statusCode)
                loginFailed()
            }

        }
    }
    fun loginFailed(){
        Toast.makeText(activity,"Login failed, try again", Toast.LENGTH_SHORT).show()
    }
    fun login(account: GoogleSignInAccount?) {
        if(account!=null){
            Toast.makeText(activity,"Login successful! Welcome "+account.displayName, Toast.LENGTH_SHORT).show()
            //Create user object and save it
            changeActivity(MainMenuActivity())
        }
    }
    fun skipLogin(){
        changeActivity(MainMenuActivity())
    }
    private fun changeActivity(activity: AppCompatActivity){
        val intent = Intent(this.activity, activity::class.java)
        this.activity.startActivity(intent)
    }
}