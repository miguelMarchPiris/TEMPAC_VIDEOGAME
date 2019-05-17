package edu.ub.pis2019.pis_16.tempac.Model


class User(val uid: String, val username: String) {
    private var highscore = 0
    private var email = ""
    constructor(uid:String, username:String,email:String):this(uid,username){
        this.email = email
    }
    fun setHighscore(value:Int):Boolean{
        if(value > highscore) {
            highscore = value
            return true
        }
        return false
    }
    fun getHighscore():Int{
        return highscore
    }
}