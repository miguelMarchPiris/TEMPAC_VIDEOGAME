package edu.ub.pis2019.pis_16.tempac.Model

object BehaviourMoveLeft : GhostBehaviour(){
    override lateinit var distances: Array<Float>
    override var row: Array<Block?>? = null
    override lateinit var ghost: Ghost
    override var scroll: Float = 0.0f
    override lateinit var playerPosition: Pair<Float, Float>
    override lateinit var rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>
    override var left : Float = 1F
    override var right : Float = 1F
    override var up : Float = 1F
    override var down : Float = 1F
    init {
        this.left=BehaviourB.left
        this.right=BehaviourB.right
        this.up=BehaviourB.up
        this.down=BehaviourB.down
    }
    override fun chase(
        ghost: Ghost,
        scroll: Float,
        playerPosition: Pair<Float, Float>,
        rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>
    ) {
        setData(ghost,scroll,playerPosition,rows)

        checkAll()

        if(distances[0] != Float.MAX_VALUE){
            ghost.behaviour=BehaviourB
            ghost.moveUp(scroll, up)
        }

        else{
            //If it can move left, move left
            if(distances[1]!= Float.MAX_VALUE){
                ghost.moveLeft(scroll, left)
            }
            else{
                //If it can't move left, move right and change to BehaviourMoveRight
                ghost.behaviour=BehaviourMoveRight
                ghost.moveRight(scroll, right)
            }
        }
    }
}