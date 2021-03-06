package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.RectF
import edu.ub.pis2019.pis_16.tempac.Model.Game.Engine
import java.util.*

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

    internal fun getTooHigh() : Boolean{

        //If its over the high line and also the tooHigh is true
        //we call the algorithm
        if(ghost.y < Engine.HIGH_LINE_GHOSTS && ghost.tooHigh){
            //It its already using a horizontla behaviour ( BehaviourMoveLeft/right)
            if(ghost.behaviour is BehaviourHorizontal){
                //keep moving horizontally
                (ghost.behaviour as BehaviourHorizontal).keepMovingHorizontally()
            }
            //Else set a horizontal behaviour
            else{
                if(Random().nextBoolean()){
                    ghost.behaviour=BehaviourMoveLeft
                    ghost.moveLeft(scroll, left)
                }
                else{
                    ghost.behaviour=BehaviourMoveRight
                    ghost.moveRight(scroll, right)
                }
            }
            return true
        }
        //If it gets to the top
        else if(ghost.y < Engine.TOO_HIGH_LINE_GHOSTS){
            ghost.tooHigh=true
            return true
        }
        else{
            //Return false
            ghost.tooHigh=false
            return false
        }
    }
    internal fun checkAll(){
        checkUp()
        checkLeft()
        checkRight()
        checkDown()
    }
    internal fun checkUp() : Boolean{
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
        return (distances[0] != Float.MAX_VALUE)
    }
    internal fun checkLeft() : Boolean{
        /***************************************
         *           CHECK LEFT
         **************************************/
        //presuponemos que nos podemos mover
        this.row =rows.second
        ghost.moveLeft(scroll, left)
        ghost.updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row==null || (!ghost.collidesWithBlock(row!!) && (! ghost.collidesWithBlock(rows.third!!)) && !(RectF.intersects(ghost.rectangle, Engine.overlayRect0) ))){
            distances[1] = ghost.calculateDistanceToPlayer(playerPosition)
        }
        //Deshacemos el movimiento
        ghost.moveRight(scroll, left)
        return (distances[1] != Float.MAX_VALUE)
    }
    internal fun checkRight() : Boolean{
        /***************************************
         *           CHECK RIGHT
         **************************************/
        //presuponemos que nos podemos mover
        this.row =rows.second
        ghost.moveRight(scroll, right)
        ghost.updateRect()
        //If the movement is valid, we calculate distance to the player
        if(row==null || (!ghost.collidesWithBlock(row!!) && (! ghost.collidesWithBlock(rows.third!!)) && !(RectF.intersects(ghost.rectangle, Engine.overlayRect2) ))){
            distances[2] = ghost.calculateDistanceToPlayer(playerPosition)
        }
        //Deshacemos el movimiento
        ghost.moveLeft(scroll, right)
        return (distances[2] != Float.MAX_VALUE)
    }
    internal fun checkDown() : Boolean{
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
        return (distances[3] != Float.MAX_VALUE)
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