package edu.ub.pis2019.pis_16.tempac.Model

import java.util.*
import kotlin.math.absoluteValue

class BehaviourB : GhostBehaviour() {
    override lateinit var distances: Array<Float>
    override var row: Array<Block?>? = null
    override lateinit var ghost: Ghost
    override var scroll: Float = 0.0f
    override lateinit var playerPosition: Pair<Float, Float>
    override lateinit var rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>

    override val left : Float = 1F
    override val right : Float = 1F
    override val up : Float = 1F
    override val down : Float = 1F

    var invertedXAxis : Boolean = false
    val probInvertedXAxis : Float = 0.5F
    init {
        invertedXAxis=(Random().nextFloat()<probInvertedXAxis)
    }

    override fun chase(
        ghost: Ghost,
        scroll: Float,
        playerPosition: Pair<Float, Float>,
        rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>
    ) {
        setData(ghost,scroll,playerPosition,rows)

        checkAll()
        moveRandom()
    }

    fun moveRandom(){
        //If you can move up, go up
        if(! (distances[0]== Float.MAX_VALUE)){
            ghost.moveUp(scroll,up)
        }
        //If you cant, then:
        else{
            getDirection()
        }
    }
    fun getDirection(){
        //The above row
        row=rows.third
        if(row==null){
            return
        }
        var minDistance= -1 to Float.MAX_VALUE
        var distanceToHole : Float

        val listOfHoles = getTheHole()


        for ( i in listOfHoles){
            //If its a hole
            if(row!!.get(i)==null){
                //We get the distance in absolute value
                distanceToHole=((Block.blockSide.times(i + 0.5F))-ghost.x).absoluteValue
                //If the distance is smaller than the previous
                if(distanceToHole < minDistance.second){
                    //we set it as the minimum distance with the corresponding index
                    minDistance=i to distanceToHole
                }
            }
        }
        distanceToHole=((Block.blockSide.times(minDistance.first + 0.5F))-ghost.x)
        //if distanceToHole  0 we need to move left
        if (distanceToHole<0){
            ghost.moveLeft(scroll,left)
        }
        else{
            ghost.moveRight(scroll,right)
        }
    }

    fun getTheHole(): MutableList<Int> {
        //The middle the row
        row=rows.second
        val listOfHoles= mutableListOf<Int>()
        //We insert the index of every null in the list
        var index : Int = -1
        for ( j in 0 until row!!.size){
            if(ghost.x-(j+0.5).times(Block.blockSide)<=0.5.times(Block.blockSide)){
                index=j
                break
            }
        }

        var inTheCorrectHole : Boolean=false
        for (i in 0 until row!!.size){
            if(i==index){
                inTheCorrectHole=true
            }

            if (row!![i]==null){
                listOfHoles.add(i)
            }
            else{
                if(inTheCorrectHole){
                    return listOfHoles
                }
                listOfHoles.clear()
            }
        }
        return listOfHoles
    }







}