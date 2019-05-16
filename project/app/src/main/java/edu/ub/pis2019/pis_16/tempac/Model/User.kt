package edu.ub.pis2019.pis_16.tempac.Model

import android.content.Context
import android.provider.Settings

class User(var username: String) {
    private var highscore = 0
    private var email = ""
    constructor(username:String,email:String):this(username){
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