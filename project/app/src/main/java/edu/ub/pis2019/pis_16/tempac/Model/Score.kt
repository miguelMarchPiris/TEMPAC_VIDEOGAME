package edu.ub.pis2019.pis_16.tempac.Model

class Score {

    var score : Int = 0

    fun update(bonus : Int){
        score += 1 + bonus
    }
}