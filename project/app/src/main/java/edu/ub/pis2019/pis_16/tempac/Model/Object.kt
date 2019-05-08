package edu.ub.pis2019.pis_16.tempac.Model

import android.content.Context

abstract class Object (): Drawable {

    var x = 0f
    var y = 0f


    fun setPosition(posx:Float, posy:Float){
        x=posx
        y=posy
    }

}