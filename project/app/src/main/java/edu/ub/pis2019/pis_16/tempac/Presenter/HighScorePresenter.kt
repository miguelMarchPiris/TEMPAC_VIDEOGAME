package edu.ub.pis2019.pis_16.tempac.Presenter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import edu.ub.pis2019.pis_16.tempac.Presenter.database.DatabaseCallback
import edu.ub.pis2019.pis_16.tempac.Presenter.database.FirestoreHandler
import edu.ub.pis2019.pis_16.tempac.R
import edu.ub.pis2019.pis_16.tempac.View.HighScoreActivity

class HighScorePresenter(private val activity: HighScoreActivity): Presenter,
    DatabaseCallback {

    override fun onResume() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPause() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate() {

        FirestoreHandler.getHighscoreTable(this)


    }
    override fun handleHighscoreTable(highscoreTable: ArrayList<Pair<String, Int>>) {
        activity.findViewById<View>(R.id.loadingPanel).visibility=View.GONE
        var listaHighScore = activity.findViewById<ListView>(R.id.highscore_listView)
        listaHighScore.invalidateViews()
        listaHighScore.adapter= ListaAdapter(activity, highscoreTable)
    }





    private class ListaAdapter(private val context: Context,private var highscoreArray:ArrayList<Pair<String,Int>>) : BaseAdapter() {


        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val layoutInflator= LayoutInflater.from(context)
            val rowMain=layoutInflator.inflate(R.layout.row_highscore,parent,false)

            val rowRanking=rowMain.findViewById<TextView>(R.id.ranking_textView)
            val rowNickname=rowMain.findViewById<TextView>(R.id.nickname_textView)
            val rowScore=rowMain.findViewById<TextView>(R.id.score_textView)

            rowRanking.text="${position.plus(1)}"
            rowNickname.text=highscoreArray[position].first
            rowScore.text=highscoreArray[position].second.toString()
            return rowMain
        }

        override fun getItem(position: Int): Any {
            return highscoreArray[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
        //cuantas lineas
        override fun getCount(): Int {
            return highscoreArray.size
        }

    }
}
