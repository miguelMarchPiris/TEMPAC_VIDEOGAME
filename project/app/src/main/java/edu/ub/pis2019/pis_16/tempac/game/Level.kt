package edu.ub.pis2019.pis_16.tempac.game

import android.graphics.Canvas
import java.util.*

//clase colisionable (los objetos con los que chocas i no pasa nada) i class no colisionable (los objetos no colisionables que no pasa nada cuando xocan.)
class Level : Drawable {
    var orbs : MutableList<Orb> = mutableListOf<Orb>()

    //TODO hay que ver que hacemos con blocks, pq tiene mas sentido que trabajemos con lines
    var lines : MutableList <MutableList<Block?>> = mutableListOf<MutableList<Block?>>()
    var r : Random=Random()

    init{
        //Block size can be changed in companion object in Block class.

        var nBlocksInLine: Int= 1080.div(Block.blockSide).toInt()
        var nLinesToDraw : Int = 25
        createLevelBlocks(nBlocksInLine,nLinesToDraw)
    }
    override fun draw(canvas: Canvas?) {
        for (orb in orbs) {
            orb.draw(canvas)
        }
        for (line in lines) {
            for (block in line) {
                if(block!=null){
                    block.draw(canvas)
                }
            }
        }
    }
    fun update(scroll : Float){
        for(orb in orbs){
            orb.update(scroll)
        }
        for (line in lines) {
            for (block in line) {
                if(block!=null){
                    block.update(scroll)
                }
            }
        }
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
            } else {
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
        var newLine : MutableList <Block?> = mutableListOf<Block?>()
        var anchoBloque : Float= Block.blockSide
        var desplazamiento : Float = anchoBloque.div(2)
        for (k in 0 until listaBooleanos.size){
            if(listaBooleanos.get(k)){
                //Todo calcular la posición que hay que pasarle al bloque
                desplazamiento=anchoBloque.times(k).plus(anchoBloque.div(2))
                var b : Block=Block(desplazamiento,anchoBloque.times(indexLine.times(-1)))
                newLine.add(k,b)
            }
            else{
                newLine.add(k, null)
            }
        }
        lines.add(newLine)
    }
}


/*class MapGenerator {
    private val filas: ArrayList<ArrayList<*>>
    private val filaActual: ArrayList<Boolean>
    private val filaAnterior: ArrayList<Boolean>

    init {
        filas = ArrayList<ArrayList<*>>()
        filaActual = ArrayList()
        filaAnterior = ArrayList()
    }

    fun generaNivelTrivial(ancho: Int, alto: Int) {
        for (k in 0 until ancho) {
            filaAnterior.add(k % 2 == 0)
            filaActual.add(false)
        }


        for (i in 0 until alto) {
            val newList: ArrayList<Boolean>
            if (i % 6 == 0) {
                newList = ArrayList(filaActual)
            } else {
                newList = ArrayList(filaAnterior)
            }
            drawLine(newList)
        }
    }

    companion object {

        internal fun generaNuevaLinea(anterior: ArrayList<*>?, ancho: Int): ArrayList<*>? {
            var nueva: ArrayList<*>? = null
            val vacios: ArrayList<*>
            var n: Int
            val prob: Int
            val total: Int
            prob = 4
            total = 10

            if (anterior == null) {
                nueva = ArrayList(ancho)
                for (i in 0 until ancho) {
                    n = (Math.random() * total).toInt()
                    if (n <= prob) {
                        nueva!!.add(i, true)
                    } else {
                        nueva!!.add(i, false)
                    }
                }
                for (i in nueva!!.indices) {
                    print(drawBlock(nueva[i] as Boolean))
                }
            } else {
                vacios = recorreFila(anterior)

                for (i in 0 until ancho) {
                    nueva!!.add(i, true)
                }

                val it = vacios.iterator()
                var tupla: IntArray
                var totalhuecos: Int
                while (it.hasNext()) {
                    tupla = it.next() as IntArray
                    totalhuecos = tupla[0] + tupla[1]

                    if (totalhuecos == 3) {
                        nueva!!.add(tupla[0], false)
                        nueva.add(tupla[0] + tupla[1], false)
                        println("metemos un hueco en las posiciones " + tupla[0] + "   " + tupla[0] + tupla[1])
                    } else {
                        n = (Math.random() * tupla[1]).toInt()
                        nueva!!.add(tupla[0] + n, false)
                    }

                }


            }

            return nueva
        }

        fun drawLine(fila: ArrayList<Boolean>) {
            val it = fila.iterator()
            while (it.hasNext()) {
                print(drawBlock(it.next()))
            }
            println("\n")
        }

        fun drawBlock(j: Boolean): String {
            return if (j) {
                "X"
            } else "_"
        }

        fun recorreFila(fila: ArrayList<*>): ArrayList<*> {
            val vacios = ArrayList()
            var anterior = false
            var actual: Boolean
            val cont = 0
            var posicion = 0
            var huecos = 0
            for (i in fila.indices) {
                actual = fila[i] as Boolean
                if (anterior) {
                    // XX
                    if (actual) {
                        //Nada
                    } else {
                        //Empezamos a contar
                        posicion = i
                        huecos = 0
                    }// X_

                } else {
                    //_X
                    if (actual) {
                        //Acabamos de contar
                        val tupla = intArrayOf(posicion, huecos)
                        vacios.add(tupla)
                    } else {
                        //Sumamos 1
                        huecos++

                    }//__

                }// _
                anterior = actual
            }
            return vacios
        }
    }
}*/
