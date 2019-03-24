package edu.ub.pis2019.pis_16.tempac

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        credits_button.setOnClickListener{
            changeActivityCredits()
        }
        //spinner to show the languages
        spn_languages.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.languages))
        spn_languages.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
                //we'll put here what we want the desplegable to do
            }

            override fun onNothingSelected(arg0: AdapterView<*>){

            }
        }

    }
    fun changeActivityCredits(){
        val intent = Intent(this, CreditsActivity::class.java)
        startActivity(intent)
    }
}



