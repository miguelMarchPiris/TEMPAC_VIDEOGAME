package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas

//clase colisionable (los objetos con los que chocas i no pasa nada) i class no colisionable (los objetos no colisionables que no pasa nada cuando xocan.)
class Level : Drawable {
    var orbs : List<Orb> = mutableListOf<Orb>()
    var structures : List <Structure> = mutableListOf<Structure>()


    override fun draw(canvas: Canvas?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}