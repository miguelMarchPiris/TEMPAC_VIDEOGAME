package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.navigation.Navigation
import edu.ub.pis2019.pis_16.tempac.R

class GameView(var cntxt:Context): SurfaceView(cntxt), SurfaceHolder.Callback{
    private var thread : GameThread
    private var engine : GameEngine
    init {
        holder.addCallback(this) //Llamar a level i en level crea un game engine. Game engine deberia ser un singeltone
        engine = GameEngine(context)
        thread = GameThread(holder, this, engine)
        isFocusable = true
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread.setRunning(true)
        thread.start()

    }
    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    //INPUTS
    override fun onTouchEvent(event: MotionEvent): Boolean {
        engine.processInput(event)
        return true
    }
    fun endGame(score:Int){
        //Stop the thread
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.interrupt()
                thread.join()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            retry = false
        }
        var nav = Navigation.findNavController(this)
        var bundle = Bundle()
        bundle.putInt("score",score)
        nav.navigate(R.id.gameOverFragment, bundle)

    }
    fun tooglePauseThread(){
        thread.pauseGame = !thread.pauseGame
    }

}