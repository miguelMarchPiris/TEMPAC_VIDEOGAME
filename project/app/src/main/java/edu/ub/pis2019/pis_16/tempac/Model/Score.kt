package edu.ub.pis2019.pis_16.tempac.Model

class Score {


    private var bonus = 0
    private var base = 0

    fun updateBonus(bonusScore : Int){
        bonus += bonusScore
    }
    fun update(baseScore: Int){
        base = baseScore
    }
    fun getScore():Int{
        return base+bonus
    }
}