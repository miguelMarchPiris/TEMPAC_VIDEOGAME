package edu.ub.pis2019.pis_16.tempac.Presenter

import edu.ub.pis2019.pis_16.tempac.Model.User

interface DatabaseHandler {
    fun updateUser(user: User)
    fun getHighscore(username:String)
    //Add functions for highscore table

}