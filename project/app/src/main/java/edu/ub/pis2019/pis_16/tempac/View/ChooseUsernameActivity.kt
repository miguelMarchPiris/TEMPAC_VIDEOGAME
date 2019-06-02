package edu.ub.pis2019.pis_16.tempac.View


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import edu.ub.pis2019.pis_16.tempac.R
import kotlinx.android.synthetic.main.activity_choose_username.*
import android.app.Activity
import android.content.Intent
import android.widget.EditText
import edu.ub.pis2019.pis_16.tempac.Model.OnHomePressedListener
import edu.ub.pis2019.pis_16.tempac.Model.HomeWatcher
import edu.ub.pis2019.pis_16.tempac.Model.MusicService

class ChooseUsernameActivity : AppCompatActivity() {

    private lateinit var editText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_username)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        var width = dm.widthPixels
        var height = dm.heightPixels

        this.title = ""

        window.setLayout((width*.8).toInt(), (height*.6).toInt())
        editText = findViewById<EditText>(R.id.editText)
        button.setOnClickListener{
            if(editText.text.toString() !="") {
                val returnIntent = Intent()
                returnIntent.putExtra("username", editText.text.toString())
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        }
        editText.requestFocus()

        //Home Button Watcher
        val mHomeWatcher = HomeWatcher(this)

        mHomeWatcher.setOnHomePressedListener(object : OnHomePressedListener {
            override fun onHomePressed() {
                MusicService.pauseMusic()
            }

            override fun onHomeLongPressed() {
                MusicService.pauseMusic()
            }
        })
        mHomeWatcher.startWatch()
    }
}
