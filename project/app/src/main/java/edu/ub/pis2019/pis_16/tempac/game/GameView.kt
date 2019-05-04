package edu.ub.pis2019.pis_16.tempac.game

import android.content.Context
import android.os.Build
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context:Context): SurfaceView(context), SurfaceHolder.Callback{
    private var thread :GameThread
    private var engine :GameEngine
    init {
        holder.addCallback(this)
        engine = GameEngine()
        thread = GameThread(holder,this, engine)
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

}