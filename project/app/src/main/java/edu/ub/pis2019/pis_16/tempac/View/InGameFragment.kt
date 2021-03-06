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
import edu.ub.pis2019.pis_16.tempac.Model.Game.Engine
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameEngine
import edu.ub.pis2019.pis_16.tempac.Model.Game.TutorialEngine
import edu.ub.pis2019.pis_16.tempac.Model.MusicService
import edu.ub.pis2019.pis_16.tempac.Model.ViewIdGenerator
import edu.ub.pis2019.pis_16.tempac.R
import kotlin.reflect.KClass

private const val ARG_PARAM2 = "isTutorial"
/**
 * A simple [Fragment] subclass.
 *
 */
class  InGameFragment : Fragment() {

    lateinit var view: GameView
    private lateinit var continueButton: Button
    private lateinit var pauseButton: Button
    private lateinit var backButton: Button
    private lateinit var engine: Engine
    private var isTutorial = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        arguments?.let {
            isTutorial = it.getBoolean(ARG_PARAM2)
        }
        if(isTutorial){
            engine = TutorialEngine(context!!)
        }
        else
            engine = GameEngine(context!!)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var inflatedLayout = inflater.inflate(edu.ub.pis2019.pis_16.tempac.R.layout.fragment_in_game, container, false)

        //We add the game view from the layout (Cant be done in xml because of the GameView's constructor
        val layout = inflatedLayout.findViewById<ConstraintLayout>(edu.ub.pis2019.pis_16.tempac.R.id.constraintLayout)
        val set = ConstraintSet()
        if(!isTutorial)
            view = GameView(context!!, engine, R.id.gameOverFragment)
        else
            view = GameView(context!!, engine, R.id.gameOverTutorialFragment)
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
            MusicService.buttonSoundPlay(activity)
            MusicService.destroyReproducer()
            MusicService.startMusicMenu(this.activity)
            activity?.finish()
        }
        //add pause button action
        pauseButton.setOnClickListener{
            MusicService.buttonSoundPlay(activity)
            togglePauseGame()
        }
        //add continue button action
        continueButton.setOnClickListener{
            MusicService.buttonSoundPlay(activity)
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

    override fun onResume() {
        super.onResume()
        MusicService.resumeMusic()
    }

    override fun onPause() {
        super.onPause()
        togglePauseGame()
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param endGameFragmentId Parameter 1.
         * @return A new instance of fragment TutorialFragment.
         */
        @JvmStatic
        fun newInstance(isTutorial: Boolean) =
            InGameFragment().apply {
                arguments = Bundle().apply {
                    putBoolean(ARG_PARAM2, isTutorial )
                }
            }
    }

}


