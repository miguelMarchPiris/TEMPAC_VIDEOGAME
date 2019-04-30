package edu.ub.pis2019.pis_16.tempac.game

abstract class Object (): Drawable {

    var x = 0f
    var y = 0f

    fun setPosition(posx:Float,posy:Float){
        x=posx
        y=posy
    }

}