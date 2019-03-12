package edu.ub.pis2019.pis_16.tempac

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_login.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button_login.setOnClickListener{
            login(it)
        }
        button_google.setOnClickListener{
            login_google(it)
        }
        button_signup.setOnClickListener {
            signup(it)
        }
    }

    private fun signup(it: View?) {
        TODO("not implemented")
    }

    private fun login_google(it: View?) {
        TODO("not implemented")
    }

    private fun login(it: View?) {
        TODO("not implemented")
    }
}
