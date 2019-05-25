package edu.ub.pis2019.pis_16.tempac.View


import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameView
import android.support.constraint.ConstraintSet
import edu.ub.pis2019.pis_16.tempac.Model.ViewIdGenerator


/**
 * A simple [Fragment] subclass.
 *
 */
class InGameFragment : Fragment() {

    lateinit var view: GameView

    private lateinit var continueButton: Button
    private lateinit var pauseButton: Button
    private lateinit var backButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var inflatedLayout = inflater.inflate(edu.ub.pis2019.pis_16.tempac.R.layout.fragment_in_game, container, false)

        //We add the game view from the layout (Cant be done in xml because of the GameView's constructor
        val layout = inflatedLayout.findViewById<ConstraintLayout>(edu.ub.pis2019.pis_16.tempac.R.id.constraintLayout)
        val set = ConstraintSet()
        view = GameView(context!!)
        view.id = ViewIdGenerator.generateViewId()
        layout.addView(view, 0)
        set.clone(layout)
        set.connect(view.id, ConstraintSet.TOP, layout.id, ConstraintSet.TOP, 0)
        set.connect(view.id, ConstraintSet.LEFT, layout.id, ConstraintSet.LEFT, 0)
        set.connect(view.id, ConstraintSet.BOTTOM, layout.id, ConstraintSet.BOTTOM, 0)
        set.connect(view.id, ConstraintSet.RIGHT, layout.id, ConstraintSet.RIGHT, 0)
        set.applyTo(layout)

        continueButton = inflatedLayout.findViewById<Button>(edu.ub.pis2019.pis_16.tempac.R.id.buttonContinue)
        continueButton.visibility = Button.GONE
        pauseButton = inflatedLayout.findViewById<Button>(edu.ub.pis2019.pis_16.tempac.R.id.buttonPause)
        backButton = inflatedLayout.findViewById<Button>(edu.ub.pis2019.pis_16.tempac.R.id.buttonBack)

        backButton.visibility = Button.GONE

        //add back button action
        backButton.setOnClickListener{
            activity?.finish()
        }
        //add pause button action
        pauseButton.setOnClickListener{
            togglePauseGame()
        }
        //add continue button action
        continueButton.setOnClickListener{
            togglePauseGame()
        }

        return inflatedLayout
    }

    private fun togglePauseGame() {
        when (continueButton.visibility) {
            Button.GONE -> continueButton.visibility = Button.VISIBLE
            Button.VISIBLE -> continueButton.visibility = Button.GONE
        }
        backButton.visibility = continueButton.visibility
        view.togglePauseThread()
    }

    override fun onPause() {
        super.onPause()
        togglePauseGame()
    }

}


