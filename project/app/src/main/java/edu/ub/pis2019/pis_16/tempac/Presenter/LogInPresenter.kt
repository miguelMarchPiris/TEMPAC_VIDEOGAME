package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Intent
import android.provider.Settings
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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import edu.ub.pis2019.pis_16.tempac.Model.User
import edu.ub.pis2019.pis_16.tempac.Presenter.database.FirestoreHandler
import edu.ub.pis2019.pis_16.tempac.View.ChooseUsernameActivity


class LogInPresenter(val activity: AppCompatActivity) : Presenter {

    private lateinit var gso : GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val RC_SIGN = 69
    private val RC_USERNAME = 420
    private var customUsername = ""
    private lateinit var app :TempacApplication
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
        //Save app instance
        app = (activity.application as TempacApplication)
        //INCIALIZE FIREBASE
        FirebaseApp.initializeApp(activity)
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

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
        val account = auth.currentUser
        if(account != null){
            login(account)
        }
    }
    fun askForlogin(){
        mGoogleSignInClient.signOut()
        val signInIntent = mGoogleSignInClient.signInIntent
        activity.startActivityForResult(signInIntent,RC_SIGN)
    }
    //This is called after the user selects the account to log in
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if(requestCode == RC_SIGN){
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("GOOGLE EROR", "Google sign in failed", e)
                // ...
            }

        }
        //Result returned form launching intent ChooseUsername
        if(requestCode == RC_USERNAME){
            if(data?.getStringExtra("username")!= null)
                customUsername = data?.getStringExtra("username")
                //Afegim usuari amb ID la del dispositiu
                if(customUsername!="") {
                    val id = Settings.Secure.getString(activity.contentResolver, Settings.Secure.ANDROID_ID)
                    app.user = User(id,customUsername)
                    app.saveLocalUser()
                    FirestoreHandler.updateUser(app.user)
                    Toast.makeText(
                        activity,
                        "Logged in with device ID, username: " + app.user.username,
                        Toast.LENGTH_LONG
                    ).show()
                    changeActivity(MainMenuActivity())
                }
        }
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("FB LOGIN", "firebaseAuthWithGoogle:" + acct.id!!)

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("FB LOGIN", "signInWithCredential:success")
                    val user = auth.currentUser
                    login(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("FB LOGIN", "signInWithCredential:failure", task.exception)
                    login(null)
                }
            }
    }
    private fun loginFailed(){
        Toast.makeText(activity,"Login failed, try again", Toast.LENGTH_SHORT).show()
    }
    private fun login(account: FirebaseUser?) {
        if(account!=null){
            app.user = User(account.uid, account.displayName!!, account.email!!)
            FirestoreHandler.updateUser(app.user)
            Toast.makeText(activity,"Login successful! Welcome "+account.displayName, Toast.LENGTH_LONG).show()
            //Create user object and save it

            changeActivity(MainMenuActivity())
        }
    }
    private fun skipLogin(){
        //We try to load the user from disk
        if(app.loadLocalUser()) {
            Toast.makeText(
                activity,
                "Logged in with device ID, username: " + app.user.username,
                Toast.LENGTH_LONG
            ).show()
            changeActivity(MainMenuActivity())
        }
        else{
            //If we couldn't open the user we create a new one
            //We ask for a username
            val intent = Intent(activity, ChooseUsernameActivity::class.java)
            activity.startActivityForResult(intent, RC_USERNAME)
        }

    }
    private fun changeActivity(activity: AppCompatActivity){
        val intent = Intent(this.activity, activity::class.java)
        this.activity.startActivity(intent)
    }
}