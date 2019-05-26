package edu.ub.pis2019.pis_16.tempac.Model

import java.util.*
import kotlin.math.absoluteValue

object BehaviourB : GhostBehaviour() {
    override lateinit var distances: Array<Float>
    override var row: Array<Block?>? = null
    override lateinit var ghost: Ghost
    override var scroll: Float = 0.0f
    override lateinit var playerPosition: Pair<Float, Float>
    override lateinit var rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>

    override val left : Float = 1F
    override val right : Float = 1F
    override val up : Float = 8F
    override val down : Float = 1F

    init {
    }

    override fun chase(
        ghost: Ghost,
        scroll: Float,
        playerPosition: Pair<Float, Float>,
        rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>
    ) {
        setData(ghost,scroll,playerPosition,rows)

        checkAll()

        //if its too high it will move horizontally
        if (getTooHigh()){
            return
        }

        moveRandom()
    }

    fun moveRandom(){

        //If you can move up, go up
        if(distances[0] != Float.MAX_VALUE){
            ghost.moveUp(scroll,up)
        }
        //If you cant, then:
        else{
            if(rows.third==null){
                ghost.moveDown(scroll, down)
                return
            }
            //Get direction returns true if the closest top hole is in the left
            var toTheLeft : Boolean = getDirection()
            if (toTheLeft){
                ghost.behaviour=BehaviourMoveLeft
                ghost.moveLeft(scroll, left)
            }
            else{
                ghost.behaviour=BehaviourMoveRight
                ghost.moveRight(scroll, right)
            }
        }
    }

    fun getDirection() : Boolean{
        //The above row
        row=rows.third

        var minDistance= -1 to Float.MAX_VALUE
        var distanceToHole : Float

        val listOfHoles = getTheHole()
        row=rows.third

        for ( i in listOfHoles){
            //If its a hole of the above row
            if(row!!.get(i)==null){
                //We get the distance in absolute value
                distanceToHole=((Block.blockSide.times(i + 0.5F))-ghost.x).absoluteValue
                //If the distance is smaller than the previous
                if(distanceToHole < minDistance.second){
                    //we set it as the minimum distance with the corresponding index
                    minDistance=i to distanceToHole
                }
                //If the distance is bigger than the previous one, we have finished (we already have the minimum)
                else{
                    break
                }
            }
        }

        //GET THE DISTANCE WITH NO ABSOLUTE VALUE, SO WE KNOW THE DIRECTION
        distanceToHole=((Block.blockSide.times(minDistance.first + 0.5F))-ghost.x)

        //if distanceToHole < 0 we need to move left
        return (distanceToHole<0)

    }

    fun getTheHole(): MutableList<Int> {
        //The middle the row
        row=rows.second
        val listOfHoles= mutableListOf<Int>()
        val listOfIndex : MutableList<Int> = mutableListOf<Int>()
        var index : Int = -1
        for ( j in 0 until row!!.size){
            //We insert the index of every null in the listOfIndex
            if(row!![j]==null && (ghost.x-(j+0.5).times(Block.blockSide)).absoluteValue<=0.5.times(Block.blockSide)){
                index=j
            }
        }

        //Something went wrong
        if(index==-1){
            return listOfHoles
        }
        //Here we take the consecutive indexs of the hole in which the ghost is
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
                else{
                    listOfHoles.clear()
                }
            }
        }
        return listOfHoles
    }







}