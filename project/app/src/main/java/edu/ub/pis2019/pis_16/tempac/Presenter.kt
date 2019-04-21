package edu.ub.pis2019.pis_16.tempac

interface Presenter {
    fun onCreate()
    fun onResume()
    fun onPause()
    fun onRestart()
    fun onStop()
    fun onDestroy()
}