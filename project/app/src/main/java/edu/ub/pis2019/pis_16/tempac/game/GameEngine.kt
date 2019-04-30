package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint



class GameEngine:Drawable{
    private var temperature : Int = 50
    private var scrollSpeed : Int = 0


    private var gfactory : GhostFactory=GhostFactory()
    private var ghosts : List<Ghost> = mutableListOf<Ghost>()
    private var player : Player = Player()
    private var level : Level= Level()

    fun update(){
        //Process state of the game

        //Process inputs
        player.update()
        //Process AI

        //Process physics

        //Process animations

        //Process sound

        //Process video

    }
    override fun draw(canvas: Canvas?){
        if (canvas != null) {
            canvas.drawColor(Color.BLACK)
            player.draw(canvas)
        }
    }

}