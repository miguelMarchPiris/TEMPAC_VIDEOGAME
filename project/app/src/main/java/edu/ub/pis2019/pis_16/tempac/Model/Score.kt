package edu.ub.pis2019.pis_16.tempac.Model

class Score {

    var score : Float = 0f

    fun update(bonus : Float){
        score += 1 + bonus
    }
}