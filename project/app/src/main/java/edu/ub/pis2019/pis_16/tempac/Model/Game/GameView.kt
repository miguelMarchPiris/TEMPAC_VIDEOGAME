package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.navigation.Navigation
import edu.ub.pis2019.pis_16.tempac.R

class GameView(var cntxt: Context, motor : GameEngine): SurfaceView(cntxt), SurfaceHolder.Callback{
    private var thread : GameThread
    private var engine : GameEngine
    init {
        holder.addCallback(this)
        engine = motor
        thread = GameThread(holder, this, engine)
        isFocusable = true
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        if(!engine.dead) {
            thread.pauseGame = true
            return
        }

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
    fun togglePauseThread(){
        thread.pauseGame = !thread.pauseGame
    }


}