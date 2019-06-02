package edu.ub.pis2019.pis_16.tempac.View


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.ub.pis2019.pis_16.tempac.Presenter.GameOverPresenter
import edu.ub.pis2019.pis_16.tempac.R

open class GameOverFragment : Fragment() {

    lateinit var inflatedView: View
    protected open var presenter = GameOverPresenter(this)

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

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

}
