package edu.ub.pis2019.pis_16.tempac.View


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameView
import edu.ub.pis2019.pis_16.tempac.R



/**
 * A simple [Fragment] subclass.
 *
 */
class InGameFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        var inflatedLayout = inflater.inflate(R.layout.fragment_in_game, container, false)

        //We add the game view from the layout (Cant be done in xml because of the GameView's constructor
        val linearLayout = inflatedLayout.findViewById<LinearLayout>(R.id.linearLayout)
        linearLayout.addView(GameView(context!!))

        return inflatedLayout
    }

}


