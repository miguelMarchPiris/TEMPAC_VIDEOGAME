package edu.ub.pis2019.pis_16.tempac.Model

class Score {

    private var score = 0
    private var baseScore = 1
    private var bonus = 0
    private val killingGhostBonus = 20

    fun updateBonus(bonusScore : Int){
        bonus = bonusScore
    }

    fun ghostBonus(times : Int){
        score += killingGhostBonus*times
    }

    fun update(){
        score += baseScore
    }

    fun getScore():Int{
        return score+bonus
    }
}