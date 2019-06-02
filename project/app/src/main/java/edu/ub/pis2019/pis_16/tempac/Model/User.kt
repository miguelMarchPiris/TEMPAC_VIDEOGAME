package edu.ub.pis2019.pis_16.tempac.Model

import java.io.*


class User():Serializable {
    private var highscore = 0
    private var email = ""
    var uid = ""
    var username = ""

    constructor(uid: String, username: String):this(){
        this.uid = uid
        this.username = username
    }
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
    fun isGoogleUser():Boolean{
        val googleUser = (email != "")
        return googleUser
    }

}