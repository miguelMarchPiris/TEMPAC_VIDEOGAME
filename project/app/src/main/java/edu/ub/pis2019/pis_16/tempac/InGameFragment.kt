package edu.ub.pis2019.pis_16.tempac


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import edu.ub.pis2019.pis_16.tempac.game.GameView
import kotlinx.android.synthetic.main.fragment_in_game.*


/**
 * A simple [Fragment] subclass.
 *
 */
class InGameFragment : Fragment() {

    var isStarted = true
    var progressStatus = 0
    var handler: Handler? = null
    lateinit var temperatureProgress: ProgressBar
    lateinit var dieButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        var inflatedLayout = inflater.inflate(R.layout.fragment_in_game, container, false)
        temperatureProgress = inflatedLayout.findViewById(R.id.temperatureBar)
        dieButton = inflatedLayout.findViewById(R.id.dieButton)

        //Temperature doesn't increase with time!
        /*
        handler = Handler(Handler.Callback {
            if (isStarted) {
                progressStatus++
            }
            temperatureProgress.temperature = progressStatus
            handler?.sendEmptyMessageDelayed(0, 100)

            true
        })

        handler?.sendEmptyMessage(0)
        */
        temperatureProgress.progress = 45;

        dieButton.setOnClickListener(){
            gameOver()
        }
        if(activity!=null) {
            return GameView(activity!!.applicationContext)
        }
        return inflatedLayout
    }

    fun gameOver(){
        var gameOverFragment = GameOverFragment()
        fragmentManager!!.beginTransaction()
            .replace(R.id.inGameFragment, gameOverFragment)
            .commit()
    }
}


