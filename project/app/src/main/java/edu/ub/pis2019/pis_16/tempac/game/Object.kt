package edu.ub.pis2019.pis_16.tempac.game

abstract class Object (x:Float,y: Float): Drawable {

    var posx : Float=x
    var posy : Float=y

    fun setPosition(x:Float,y:Float){
        posx=x
        posy=y
    }

}