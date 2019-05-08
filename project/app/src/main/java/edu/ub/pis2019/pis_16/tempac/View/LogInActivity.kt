package edu.ub.pis2019.pis_16.tempac.View

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.ub.pis2019.pis_16.tempac.R
import kotlinx.android.synthetic.main.activity_login.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener{
            login(it) //we have to comment it cause it made the applicaton crash for some reason?

        }

        button_google.setOnClickListener{
            login_google(it)
        }

        button_signup.setOnClickListener {
            signup(it)
        }
        button_skipLogin.setOnClickListener {
            skipLogin(it)
        }
    }

    //connects with another activity passed by parameter
    fun changeActivity(activity: AppCompatActivity){
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
    }

    private fun signup(it: View?) {
        changeActivity(SignUpActivity())
    }

    private fun login_google(it: View?) {
        //TODO("not implemented")
    }

    private fun login(it: View?) {
        changeActivity(MainMenuActivity())
        //TODO("not implemented")
    }
    private fun skipLogin(it: View?){
        changeActivity(MainMenuActivity())
    }
}
