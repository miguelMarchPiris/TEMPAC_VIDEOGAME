package edu.ub.pis2019.pis_16.tempac.game

class GameEngine : Drawable {
    var temperature : Int = 50
    var scrollSpeed : Int = 0


    var gfactory : GhostFactory=GhostFactory()
    var ghosts : List<Ghost> = mutableListOf<Ghost>()
    var player : Player= Player(0f,0f)
    var level : Level= Level()


    override fun draw(){
        TODO()
    }

}