package edu.ub.pis2019.pis_16.tempac.View


import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import edu.ub.pis2019.pis_16.tempac.R


class GameOverFragment : Fragment() {

    lateinit var menuButton: Button
    lateinit var shareButton: Button
    lateinit var backgroundLayout: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var inflatedView =  inflater.inflate(R.layout.fragment_game_over, container, false)

        var animBounce = AnimationUtils.loadAnimation(context, R.anim.bounce)
        var text : TextView = inflatedView.findViewById(R.id.playAgainText)
        text.startAnimation(animBounce)

        menuButton = inflatedView.findViewById(R.id.menuButton)
        shareButton = inflatedView.findViewById(R.id.shareButton)
        backgroundLayout = inflatedView.findViewById(R.id.backgroundLayout)

        menuButton.setOnClickListener{
            //Menu Button Action
            changeActivityMenu()
        }

        shareButton.setOnClickListener{
            //Menu Button Action
            share()
        }

        backgroundLayout.setOnClickListener{
            //Menu Button Action
            playAgain()
        }
        return inflatedView;
    }
    fun changeActivityMenu(){
        activity?.finish()
    }
    fun share(){

    }
    fun playAgain(){
        var fragment = InGameFragment()
        fragmentManager!!.beginTransaction()
            .replace(R.id.inGameFragment, fragment)
            .commit()
    }

}
