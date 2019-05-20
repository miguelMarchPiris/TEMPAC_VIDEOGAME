package edu.ub.pis2019.pis_16.tempac.Model

import kotlin.math.absoluteValue

abstract class GhostBehaviour {
    abstract val left : Float
    abstract val right : Float
    abstract val up : Float
    abstract val down : Float

    abstract var ghost : Ghost
    abstract var scroll : Float
    abstract var playerPosition : Pair<Float,Float>
    abstract var rows: Triple<Array<Block?>?,Array<Block?>?,Array<Block?>?>
    //distancia al jugador en funcion del movimiento 0-up, 1-left, 2-right, 3-down
    abstract var distances : Array<Float>
    abstract var row: Array<Block?>?
    abstract fun chase(ghost : Ghost,scroll: Float, playerPosition: Pair<Float,Float>,rows: Triple<Array<Block?>?,Array<Block?>?,Array<Block?>?>)


    internal fun setData(
        ghost: Ghost,
        scroll: Float,
        playerPosition: Pair<Float, Float>,
        rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>
    ){
        this.ghost=ghost
        this.scroll=scroll
        this.playerPosition=playerPosition
        this.rows=rows
        this.distances=arrayOf(Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE,Float.MAX_VALUE)
    }
    internal fun checkAll(){
        checkUp()
        checkLeft()
        checkRight()
        checkDown()
    }
    internal fun checkUp(){
        /***************************************
         *           CHECK UP
         **************************************/
        //presuponemos que nos podemos mover
        this.row =rows.third
        ghost.moveUp(scroll, up)
        ghost.updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row==null || !ghost.collidesWithBlock(row!!)){
            distances[0] = ghost.calculateDistanceToPlayer(playerPosition)
        }
        //Deshacemos el movimiento
        ghost.moveDown(scroll, up)
    }
    internal fun checkLeft(){
        /***************************************
         *           CHECK LEFT
         **************************************/
        //presuponemos que nos podemos mover
        this.row =rows.second
        ghost.moveLeft(scroll, left)
        ghost.updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row==null || !ghost.collidesWithBlock(row!!)){
            distances[1] = ghost.calculateDistanceToPlayer(playerPosition)
        }
        //Deshacemos el movimiento
        ghost.moveRight(scroll, left)
    }
    internal fun checkRight(){
        /***************************************
         *           CHECK RIGHT
         **************************************/
        //presuponemos que nos podemos mover
        this.row =rows.second
        ghost.moveRight(scroll, right)
        ghost.updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row==null || !ghost.collidesWithBlock(row!!)){
            distances[2] = ghost.calculateDistanceToPlayer(playerPosition)
        }
        //Deshacemos el movimiento
        ghost.moveLeft(scroll, right)
    }
    internal fun checkDown(){
        /***************************************
         *           CHECK DOWN
         **************************************/
        //presuponemos que nos podemos mover
        this.row =rows.first
        ghost.moveDown(scroll, down)
        ghost.updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row==null || !ghost.collidesWithBlock(row!!)){
            distances[3] = ghost.calculateDistanceToPlayer(playerPosition)
        }
        //Deshacemos el movimiento
        ghost.moveUp(scroll, down)
    }
    internal fun searchMinDistance(): Int {

        var min = Float.MAX_VALUE
        var minIndex = 0
        for((index,distance) in distances.withIndex()){
            if(distance < min) {
                min = distance
                minIndex = index
            }
        }
        return minIndex
    }
    internal fun searchMinDistanceNOTWORKING(): MutableList<Int> {
        var sortedValues = mutableListOf(
            0 to distances[0],
            1 to distances[1],
            2 to distances[2],
            3 to distances[3]
        )
        sortedValues.sortBy {it.second}
        val sortedIndex = mutableListOf<Int>()
        for (i in 0 until sortedValues.size){
            sortedIndex.add(i, sortedValues.get(i).first)
        }
        return sortedIndex

    }

    internal fun moveDefault(minIndex : Int){

        when(minIndex){
            0 -> ghost.moveUp(scroll, up)
            1 -> ghost.moveLeft(scroll, left)
            2 -> ghost.moveRight(scroll, right)
            3 -> ghost.moveDown(scroll, down)
        }
        //if (distances[minIndex])



        ghost.updateRect()
    }

}