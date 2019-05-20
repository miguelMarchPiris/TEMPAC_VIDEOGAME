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
    val FRAMES_INVERTED : Int = 120
    var contFrames : Int = 0
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

        if(invertedXAxis){
            //Here we invert the X Axis
            var l=distances[2]
            distances[2]=distances[1]
            distances[1]=l
        }
        moveRandom(searchMinDistance())
        /**
        contFrames++
        if(contFrames==FRAMES_INVERTED){
        contFrames=0
        invertXAxis()
        }
         */

    }

    fun invertXAxis(){
        invertedXAxis=!invertedXAxis
    }

    fun moveRandom(minIndex : Int){
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
        row=rows.third
        if(row==null){
            return
        }
        var minDistance= -1 to Float.MAX_VALUE
        var distanceToHole : Float
        for ( i in 0 until row!!.size){
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
}