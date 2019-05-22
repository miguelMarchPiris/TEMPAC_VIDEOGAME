package edu.ub.pis2019.pis_16.tempac.Presenter

import android.app.Application
import android.os.Environment

import edu.ub.pis2019.pis_16.tempac.Model.User
import java.io.*

class TempacApplication : Application(){
    lateinit var user:User
    init {

    }
    //Methods for data persistency
    fun saveLocalUser() {
        if(!user.isGoogleUser()) {
            val out: ObjectOutput
            try {
                val file = File(applicationContext.filesDir, "user.data")
                out = ObjectOutputStream(FileOutputStream(file))
                out.writeObject(user)
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadLocalUser():Boolean {
        val input: ObjectInput
        var user: User? = null
        var loaded = false
        try {
            val file = File(applicationContext.filesDir, "user.data")
            input = ObjectInputStream(FileInputStream(file))
            user = input.readObject() as User
            input.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if(user != null) {
            this.user = user
            loaded = true
        }
        return loaded
    }
    fun signOutLocalUser():Boolean{
        try {
            val file = File(applicationContext.filesDir, "user.data")
            return file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}