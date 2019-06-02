package edu.ub.pis2019.pis_16.tempac.Presenter.database

interface DatabaseCallback{
    fun handleHighscoreTable(highscoreTable: ArrayList<Pair<String, Int>>) {}
    fun handleHighscore(highscore: Pair<String, Int>) {}
}