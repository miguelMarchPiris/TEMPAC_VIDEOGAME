package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.RectF

abstract class Ghost : Actor(){
    init {
        rectangle = RectF(x-20,y-20,x+20,y+20)
    }

    fun follow(){
        TODO("Chasing algorithm")
    }

    override fun update(scroll: Float){
        super.update(scroll)
        rectangle.set(x-20,y-20,x+20,y+20)
    }
}