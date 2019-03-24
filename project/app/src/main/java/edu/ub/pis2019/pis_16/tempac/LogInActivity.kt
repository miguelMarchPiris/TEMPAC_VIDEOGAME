package edu.ub.pis2019.pis_16.tempac

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
    }

    //connects with another activity passed by parameter
    fun changeActivity(){
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

    private fun signup(it: View?) {
        //TODO("not implemented")
    }

    private fun login_google(it: View?) {
        //TODO("not implemented")
    }

    private fun login(it: View?) {
        changeActivity()
        //TODO("not implemented")
    }
}
