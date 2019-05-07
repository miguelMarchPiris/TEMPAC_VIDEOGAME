package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas

//clase colisionable (los objetos con los que chocas i no pasa nada) i class no colisionable (los objetos no colisionables que no pasa nada cuando xocan.)
class Level : Drawable {
    var orbs : MutableList<Orb> = mutableListOf<Orb>()
    var blocks : MutableList <Block> = mutableListOf<Block>()
    
    init{
        //Instanciamos bloques para hacer pruebas
        //Los bloques tienen un ancho de 80 (se puede modificar en classe block)
        blocks.add(Block(300f,300f))
        blocks.add(Block(380f,300f))

    }
    override fun draw(canvas: Canvas?) {
        for(orb in orbs){
            orb.draw(canvas)
        }
        for(block in blocks){
            block.draw(canvas)
        }
    }
    fun update(scroll : Float){
        for(orb in orbs){
            orb.update(scroll)
        }
        for(block in blocks){
            block.update(scroll)
        }
    }


}