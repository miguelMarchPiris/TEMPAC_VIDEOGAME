package edu.ub.pis2019.pis_16.tempac.View


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.ub.pis2019.pis_16.tempac.Presenter.GameOverPresenter
import edu.ub.pis2019.pis_16.tempac.R

class GameOverTutorialFragment : GameOverFragment() {
    override var presenter = GameOverPresenter(this)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?):View?{
        var view = super.onCreateView(inflater, container, savedInstanceState)
        presenter.setUpTutorial()
        return view
    }
}
