package edu.ub.pis2019.pis_16.tempac.Model

object BehaviourDefault : GhostBehaviour() {
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


    override fun chase(
        ghost: Ghost,
        scroll: Float,
        playerPosition: Pair<Float, Float>,
        rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>
    ) {
        //Firts we set all the data we need for the algorithm
        setData(ghost,scroll,playerPosition,rows)
        //Then we check all the posible movements and save how much distance do we have
        //in each movement
        checkAll()

        //if its too high it will move horizontally
        if (getTooHigh()){
            return
        }

        //Then we check which one is the closest to the player, and then move into that direction
        moveDefault(searchMinDistance())
    }
}