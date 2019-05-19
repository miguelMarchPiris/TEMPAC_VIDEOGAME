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
import androidx.navigation.Navigation
import edu.ub.pis2019.pis_16.tempac.Presenter.GameOverPresenter
import edu.ub.pis2019.pis_16.tempac.R
import kotlinx.android.synthetic.main.fragment_game_over.*
import org.w3c.dom.Text


class GameOverFragment : Fragment() {

    lateinit var inflatedView: View
    private val presenter = GameOverPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        inflatedView =  inflater.inflate(R.layout.fragment_game_over, container, false)
        presenter.onCreate()
        presenter.processArgs(arguments)
        return inflatedView

    }


}
