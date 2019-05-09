package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import edu.ub.pis2019.pis_16.tempac.Model.Block
import edu.ub.pis2019.pis_16.tempac.Model.Drawable
import edu.ub.pis2019.pis_16.tempac.Model.Orb
import java.util.*

//clase colisionable (los objetos con los que chocas i no pasa nada) i class no colisionable (los objetos no colisionables que no pasa nada cuando xocan.)
class Level(blockImg : List<Bitmap>) : Drawable {
    var orbs : MutableList<Orb> = mutableListOf<Orb>()

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
        //Instanciamos bloques para hacer pruebas
        //Los bloques tienen un ancho de 80 (se puede modificar en classe block)

        blocks.add(Block(300f, 300f, false, blockImages))
        blocks.add(Block(380f, 300f, true, blockImages))
        orbs.add(
            Orb(
                500f,
                800f,
                Orb.Operand.ADD,
                200
            )
        )
        orbs.add(
            Orb(
                500f,
                700f,
                Orb.Operand.MUL,
                4
            )
        )
        orbs.add(
            Orb(
                500f,
                500f,
                Orb.Operand.DIV,
                2
            )
        )
        orbs.add(
            Orb(
                500f,
                600f,
                Orb.Operand.SUB,
                4
            )
        )

        //Block size can be changed in companion object in Block class.

    }

    override fun draw(canvas: Canvas?) {
        for (orb in orbs) {
            orb.draw(canvas)
        }
        for (block in blocks){
            block.draw(canvas)
        }
    }
    fun update(scroll : Float){
        for(orb in orbs){
            orb.update(scroll)
        }
        for (block in blocks){
            block.update(scroll)
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

