package edu.ub.pis2019.pis_16.tempac

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), OnItemSelectedListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initSpinner()

        //button to go back to the last/main activity
        btn_back.setOnClickListener {

        }

        //spinner to show the languages
        spn_languages.setOnClickListener {

        }

    }

    //inicialitzes the Spinner
    fun initSpinner(){
        var adapterList : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item)
        adapterList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spn_languages.adapter=adapterList
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        //we'll put here what we want the desplegable to do
    }

    override fun onNothingSelected(arg0: AdapterView<*>){

    }
}


