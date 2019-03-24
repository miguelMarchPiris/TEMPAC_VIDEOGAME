package edu.ub.pis2019.pis_16.tempac

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView

class HighScore : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)

        val listaHighScore:ListView= findViewById<ListView>(R.id.highscore_listView)


        listaHighScore.adapter=ListaAdapter(this)


    }

    private class ListaAdapter(contexto:Context) : BaseAdapter() {

        private val mContext:Context
        private lateinit var nicknameArray:ArrayList<String>
        private lateinit var highscoreArray:ArrayList<String>

        init {
            mContext= contexto
            nicknameArray= arrayListOf(
                "Juanjo",
                "Juanito",
                "Julián",
                "José",
                "Juan Ramón"
            )
            highscoreArray= arrayListOf(
                "9999999",
                "8888888",
                "7777777",
                "6666666",
                "5555555"
            )
        }

        //cuantas lineas
        override fun getCount(): Int {
            return nicknameArray.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflator=LayoutInflater.from(mContext)
            val rowMain=layoutInflator.inflate(R.layout.highscore_row,parent,false)

            val rowRanking=rowMain.findViewById<TextView>(R.id.ranking_textView)
            val rowNickname=rowMain.findViewById<TextView>(R.id.nickname_textView)
            val rowScore=rowMain.findViewById<TextView>(R.id.score_textView)

            rowRanking.text="${position.plus(1)}"
            rowNickname.text=nicknameArray.get(position)
            rowScore.text=highscoreArray.get(position)

            return rowMain
        }


        //ignorarlos por ahora
        override fun getItem(position: Int): Any {
            return "TestString"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

    }
}
