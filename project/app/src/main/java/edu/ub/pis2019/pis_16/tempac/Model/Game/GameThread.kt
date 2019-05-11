package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.graphics.Canvas
import android.opengl.Visibility
import android.util.Log
import android.view.SurfaceHolder
import java.lang.Exception

class GameThread(private var surfaceHolder: SurfaceHolder, private var gameView: GameView, private var gameEngine: GameEngine) : Thread() {

    private var fps = 30
    private var avgFps = 0.0
    private var isrunning = false
    var canvas:Canvas? = null

    //GameLoop
    override fun run() {
        var startTime : Long
        var timeMilis : Long
        var waitMilis : Long
        var totalTime : Long = 0
        var frameCount = 0
        var targetTime = 1/fps

        while(isrunning){

            startTime = System.nanoTime()
            canvas = null

            //Lock the canvas and update the game
            try{
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(surfaceHolder){
                    if(gameEngine.dead) {
                        gameView.endGame(gameEngine.getScore())
                    }
                    else {
                        gameEngine.update()
                        gameEngine.draw(canvas)
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
            }finally {
                //Unlock the canvas
                if (canvas!=null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                }
            }
            //Sleep to keep desired fps
            timeMilis = (System.nanoTime()-startTime)/1000000
            waitMilis = targetTime - timeMilis
            if(waitMilis>0)
                try {
                    sleep(waitMilis)
                }catch (e:Exception)
                {
                    e.printStackTrace()
                }

            totalTime += System.nanoTime() - startTime
            frameCount++
            if (frameCount == fps)        {
                avgFps = 1000.0 / ((totalTime / frameCount) / 1000000.0)
                frameCount = 0
                totalTime = 0
                Log.d("FPS",this.avgFps.toString())

            }
        }
    }
    fun setRunning(running: Boolean){
        isrunning = running
    }

}