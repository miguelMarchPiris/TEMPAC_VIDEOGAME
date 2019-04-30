package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception

class GameThread(private var surfaceHolder: SurfaceHolder, private var gameView: GameView) : Thread() {

    private var fps = 30
    private var avgFps = 0.0
    private var gameEngine = GameEngine()
    private var isrunning = false
    var canvas:Canvas? = null

    //GameLoop
    override fun run() {
        var startTime : Long = 0
        var timeMilis : Long = 0
        var waitMilis : Long = 0
        var totalTime : Long = 0
        var frameCount : Long = 0
        var targetTime = 1/fps

        while(isrunning){

            startTime = System.nanoTime()
            canvas = null

            //Lock the canvas and update the game
            try{
                canvas = this.surfaceHolder.lockCanvas()
                synchronized(surfaceHolder){
                    gameEngine.update()
                    //drawCanvas?
                    //gameEngine.draw(canvas)
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
        }
    }
    fun setRunning(running: Boolean){
        isrunning = running
    }


}