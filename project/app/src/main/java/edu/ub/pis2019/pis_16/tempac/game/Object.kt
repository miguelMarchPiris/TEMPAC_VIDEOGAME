package edu.ub.pis2019.pis_16.tempac.game

abstract class Object (): Drawable {

    var x:Int = 0
    var y:Int = 0

    fun setPosition(posx:Int,posy:Int){
        x=posx
        y=posy
    }

}