package edu.ub.pis2019.pis_16.tempac.Model

class Score {


    private var bonus = 0
    private var base = 0
    private var score = 0
    var multiplayer = 0

    fun updateBonus(bonusScore : Int){
        bonus = bonusScore
    }
    fun update(augment: Int){
        score += augment
    }
    fun getScore():Int{
        return score+bonus
    }
}