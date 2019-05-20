package edu.ub.pis2019.pis_16.tempac.Model

class BehaviourBelowTheLine : GhostBehaviour() {
    override lateinit var distances: Array<Float>
    override var row: Array<Block?>? = null
    override lateinit var ghost: Ghost
    override var scroll: Float = 0.0f
    override lateinit var playerPosition: Pair<Float, Float>
    override lateinit var rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>
    override val left : Float = 5F
    override val right : Float = 5F
    override val up : Float = 5F
    override val down : Float = 5F

    override fun chase(
        ghost: Ghost,
        scroll: Float,
        playerPosition: Pair<Float, Float>,
        rows: Triple<Array<Block?>?, Array<Block?>?, Array<Block?>?>
    ) {
        setData(ghost,scroll,playerPosition,rows)

        checkAll()

        moveDefault(searchMinDistance())
    }
}