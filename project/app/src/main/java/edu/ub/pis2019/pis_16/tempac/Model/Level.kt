package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import edu.ub.pis2019.pis_16.tempac.Model.Block
import edu.ub.pis2019.pis_16.tempac.Model.Drawable
import edu.ub.pis2019.pis_16.tempac.Model.Orb
import java.util.*

//clase colisionable (los objetos con los que chocas i no pasa nada) i class no colisionable (los objetos no colisionables que no pasa nada cuando xocan.)
class Level(blockImg : List<Bitmap>) : Drawable {
    companion object{
        const val MAX_ORBS = 4
        const val MAX_BLOCKS = 50
        const val MAX_LINES = 0
    }
    var ofactory : OrbFactory = OrbFactory()
    var orbs : MutableList<Orb> = mutableListOf<Orb>()

    var temperature = 0f

    //TODO hay que ver que hacemos con blocks, pq tiene mas sentido que trabajemos con lines
    var lines : MutableList <MutableList<Block?>> = mutableListOf<MutableList<Block?>>()
    var blocks : MutableList<Block> = mutableListOf<Block>()
    var breakableBlocks : MutableList<Block?> = mutableListOf<Block?>()
    var r : Random=Random()
    var blockImages : List<Bitmap>

    init{
        blockImages=blockImg
        var nBlocksInLine: Int= 1080.div(Block.blockSide).toInt()
        var nLinesToDraw : Int = 10
        createLevelBlocks(nBlocksInLine,nLinesToDraw)

    }

    override fun draw(canvas: Canvas?) {
        for (orb:Orb in orbs) {
            orb.draw(canvas)
        }
        for (block in blocks){
            block.draw(canvas)
        }
    }

    fun update(scroll : Float){
        spawnOrbs()

        for(orb in orbs){
            orb.update(scroll)
        }

        for (block in blocks){
            block.update(scroll)
        }
    }

    fun spawnOrbs(){
        if(orbs.size <= MAX_ORBS) {
            orbs.add(ofactory.create(temperature))

            //TODO FUNCTION TO DECIDE WHERE THE GHOST SHOULD SPAWN
            //we could make the function return a Par<Float, Float> and pass each one for parameter or we could change the set position to redive a par.
            orbs[(orbs.size - 1)].setPosition(250f, 250f)
        }
    }

    fun removeBreakableBlock(b : Block){
        breakableBlocks.remove(b)
    }

    fun createLevelBlocks(ancho : Int, alto: Int){
        createTrivialLevelBlocks(ancho,alto)
        //generateNewLevel(ancho,alto)
    }
    fun createTrivialLevelBlocks(ancho: Int, alto: Int) {
        var listaintermitente : MutableList<Boolean> = mutableListOf<Boolean>()
        var listavacia : MutableList<Boolean> = mutableListOf<Boolean>()

        for (k in 0 until ancho) {
            listaintermitente.add(k,k % 2 == 0)
            listavacia.add(k, false)
        }
        for (i in 0 until alto) {
            val newList: MutableList<Boolean>
            if (i % 6 == 0) {
                newList = listavacia.toMutableList()
            }else {
                newList = listaintermitente.toMutableList()
            }
            createNewBlockLine(newList,i)
        }
    }



    fun generateNewLevel(ancho: Int, alto: Int){
        var fila: MutableList<Boolean>? = null
        for (i in 0 until alto) {
            fila=generateNewLine(fila, ancho)
            if (fila != null) { createNewBlockLine(fila,i) }
        }
    }
    fun generateNewLine(filaAnterior : MutableList<Boolean>?, ancho : Int): MutableList<Boolean>? {
        var nueva : MutableList<Boolean> = mutableListOf()
        var holes : MutableList<Pair<Int,Int>>

        //Probability that each place in the first line has a block
        var probPrimeros : Float=0.3F
        //Probability that one block is created afterwards the obligatory blocks are created
        var probRandomHole : Float=0.3F
        //Probability that once one line is created it reapeats itselve just after
        var probRepetLine : Float=0.7F


        var prob : Float

        var n: Int
        //Si
        if (filaAnterior == null) {
            for (i in 0 until ancho){
                prob = r.nextFloat()
                if (prob <= probPrimeros) { nueva.add(i, true)}
                else {                      nueva.add(i, false)}
            }
        }
        else{
            holes = getHoles(filaAnterior.toMutableList<Boolean>())
            for (i in 0 until ancho) {nueva.add(i, true)}//inicializamos la fila llena de bloques
            var tupla: Pair<Int,Int>
            var totalhuecos : Int

            //Recorremos la lista y metemos huecos según los huecos de la fila anterior
            for(i in 0 until holes.size){
                tupla = holes.get(i)
                totalhuecos = tupla.second
                //Si el hueco
                if (totalhuecos == 3) {
                    nueva.set(tupla.first, false)
                    nueva.set(tupla.first + (tupla.second.minus(1) ), false)
                } else {
                    n = (r.nextInt(tupla.second.minus(1)))
                    nueva.set((tupla.first + n), false)
                }

            }

            //Ponemos huecos aleatoriamente
            for (i in 0 until ancho){
                if (r.nextFloat()<=probRandomHole){ nueva.set(i,false)}
            }

        }

        return nueva
    }

    fun getHoles(line: MutableList<Boolean>): MutableList<Pair<Int, Int>> {
        var holeList : MutableList<Pair<Int,Int>> = mutableListOf<Pair<Int,Int>>()
        var prev : Boolean = false
        var position : Int=0
        var withHole : Int=0
        var actual : Boolean
        for (i in 0 until line.size){
            actual=line.get(i)

            if (prev){
                // (XX)
                if(actual){}
                // (X_) Empezamos a contar
                else{
                    position=i
                    withHole=0
                }
            }
            else{
                //(_X)
                if (actual){holeList.add(Pair(position, withHole+1)) }
                //(__)
                else{
                    withHole++
                    if(i==line.size-1){holeList.add(Pair(position, withHole+1))}
                }
            }
        }
        return holeList
    }

    fun createNewBlockLine(listaBooleanos : MutableList<Boolean>,indexLine : Int){
        var anchoBloque : Float= Block.blockSide
        var desplazamiento : Float
        for (k in 0 until listaBooleanos.size){
            if(listaBooleanos.get(k)){
                //Calcular la posición que hay que pasarle al bloque
                desplazamiento=anchoBloque.times(k).plus(anchoBloque.div(2))
                //Todo, see how we choose breakable blocks
                var b =Block(desplazamiento,anchoBloque.times(indexLine.times(-1)), true, blockImages)
                blocks.add(b)
            }
        }
    }
}

