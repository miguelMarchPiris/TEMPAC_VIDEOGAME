package edu.ub.pis2019.pis_16.tempac.Model

class BehaviourG  : GhostBehaviour() {
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

    }
}