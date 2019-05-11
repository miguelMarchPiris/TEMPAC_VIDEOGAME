package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import edu.ub.pis2019.pis_16.tempac.Model.*
import edu.ub.pis2019.pis_16.tempac.R


class GameEngine(var context: Context) : Drawable {

    //Const values
    companion object {
        const val MAX_GHOSTS = 1    //1 ghost para probar ai
        const val MIN_DISTANCE = 105f
        const val MAX_ORBS = 12
        const val PLAYFIELD_HEIGTH = 1400
        const val PLAYFIELD_WIDTH = 1080
    }

    //Game variables
    private var scrollSpeed = 3f
    var dead = false

    //Secondary Game Variables
    private var breakableTempeature=90f

    //Objects
    private var temperatureBar = TemperatureBar()
    private var score = Score()

    //Actors
    private var gfactory : GhostFactory = GhostFactory(initGhostImages())
    private var player : Player = Player(500f,1000f, initPacmanImages())
    private var ghosts : MutableList<Ghost> = mutableListOf()
    private var level : Level = Level(initBlockImages())

    //Play zone rects
    private val playingField = RectF(0f, 225f,1080f,1625f)
    private val playingFieldLine = RectF(playingField.left-2.5f, playingField.top-2.5f,playingField.right+2.5f,playingField.bottom+2.5f)
    private val fieldLinePaint = Paint()
    private val fieldPaint = Paint()
    private val overlay : List<RectF>
    private val overlayPaint = Paint()

    //Input variables
    private var touchStartX = 0f
    private var touchStartY = 0f
    private var touchEndX = 0f
    private var touchEndY = 0f

    //Screen variables
    private var metrics = context.resources.displayMetrics
    private var w = metrics.widthPixels
    private var h = metrics.heightPixels

    init {

        //thermometer
        temperatureBar.x = 100f
        temperatureBar.y = 100f
        temperatureBar.temperature = 35f
        fieldLinePaint.style = Paint.Style.STROKE
        fieldLinePaint.strokeWidth = 5f
        fieldLinePaint.color = Color.WHITE

        //playfield
        val overlayRect0 = RectF(0f,0f,playingField.left,playingField.bottom) //Left
        val overlayRect1 = RectF(0f,0f,playingField.right,playingField.top) //Top
        val overlayRect2 = RectF(playingField.right,0f,playingField.right,playingField.bottom) //Right
        val overlayRect3 = RectF(0f,playingField.bottom,playingField.right,h.toFloat()+500f)    //Bottom
        overlay = listOf(overlayRect0,overlayRect1,overlayRect2,overlayRect3)
        overlayPaint.color = Color.BLACK
        overlayPaint.alpha = 100 //This makes it so we can se what its outside the playzone

        //Factory (maybe this is not necesesary?)
        ghosts.add(gfactory.create(temperatureBar.temperature))

    }

    fun initPacmanImages() : List<Bitmap>{
        val pacman_open_up : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_open_up)
        val pacman_open_rigth : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_open_right)
        val pacman_open_down : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_open_down)
        val pacman_open_left : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_open_left)
        val pacmanImages : List<Bitmap> = listOf(pacman_open_up, pacman_open_rigth, pacman_open_down,pacman_open_left)
        return pacmanImages
    }

    fun initBlockImages() : List<Bitmap>{

        val blockImages : List<Bitmap> = listOf()
        return blockImages
    }

    fun initGhostImages() : List<Bitmap>{
        val ghost_red : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost_red)
        val ghost_blue : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost_blue)
        val ghost_green : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost_green)
        val ghost_yellow : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost_yellow)
        val ghostsImages : List<Bitmap> = listOf(ghost_red,ghost_blue,ghost_green,ghost_yellow)
        return ghostsImages
    }

    fun update(){
        //Process state of the game
        level.temperature = temperatureBar.temperature
        //Increment scroll speed
        score.update(1f)    //To test the score in game over screen works fine
        level.update(scrollSpeed)
        deleteGhosts(temperatureBar.temperature)
        spawnGhost()
        //Process inputs
        player.update(scrollSpeed)

        //Process AI
        for(ghost in ghosts) ghost.update(scrollSpeed, Pair(player.x,player.y), level.get3RowsAtY(ghost.y+scrollSpeed))

        //Process physics
        processPhysics()
        //Process animations

        //Process sound

        //Process video
    }

    fun deleteGhosts(temperature : Float){
        var blues : MutableList<Ghost> = (ghosts.filterIsInstance<GhostB>()).toMutableList()
        var greens : MutableList<Ghost> = (ghosts.filterIsInstance<GhostG>()).toMutableList()
        var yellows : MutableList<Ghost> = (ghosts.filterIsInstance<GhostY>()).toMutableList()
        var reds : MutableList<Ghost> = (ghosts.filterIsInstance<GhostR>()).toMutableList()

        when(temperature){
            in 0f..20f -> ghosts = blues
            in 20f..40f -> ghosts = blues.union(greens).toMutableList()
            in 40f..60f -> ghosts = greens.union(yellows).toMutableList()
            in 60f..80f -> ghosts = yellows.union(reds).toMutableList()
            in 80f..100f -> ghosts = reds
        }
    }
    fun spawnGhost(){
        if(ghosts.size <= MAX_GHOSTS){
            ghosts.add(gfactory.create(temperatureBar.temperature))

            //TODO FUNCTION TO DECIDE WHERE THE GHOST SHOULD SPAWN
            //we could make the function return a Par<Float, Float> and pass each one for parameter or we could change the set position to redive a par.

            ghosts[ghosts.size-1].setPosition(500f,500f)
        }
    }
    override fun draw(canvas: Canvas?){
        if (canvas != null) {

            canvas.scale(w/1080f,w/1080f)

            //Draw background
            canvas.drawColor(Color.BLACK)

            //Draw Actors
            player.draw(canvas)
            for(ghost in ghosts) ghost.draw(canvas)
            level.draw(canvas)

            //Draw Overlay & Playzone
            for(rect in overlay)
                canvas.drawRect(rect,overlayPaint)
            canvas.drawRect(playingFieldLine,fieldLinePaint)


            //Draw Objects
            temperatureBar.draw(canvas)
            //>>posar aqui el draw de pause i score<<



        }

    }

    private fun processPhysics(){
        //check if player is out of the game
        if(isOutOfPlayzone(player))
            dead = true

        //check out of playzone
        //this is like a list comprehension in python, super fast and is the only clean way to delete elements
        //we also check breakable blocks that need to be deleted

        /*
        level.blocks = (level.blocks.filter { block ->
            !isOutOfPlayzone(block) &&
            !(RectF.intersects(block.rectangle, player.rectangle) && block.breakable && (temperatureBar.temperature>=breakableTempeature))
        }).toMutableList()
        */

        //todo check best way to check is lastArray is out
        //Last array of the matrix
        val lastArray:Array<Block?>? =level.getLastArray()
        //This returns the positionY of every block on the line(even if there are no blocks
        var positionYLastArray : Float?=level.positionYArray.get(lastArray)
        if (positionYLastArray!=null && lastArray!=null){
            if (positionYLastArray>(playingField.bottom+Block.blockSide)){
                level.deleteLine(lastArray)
            }
        }
        level.orbs = (level.orbs.filter { element ->
            !isOutOfPlayzone(element) &&
            !checkCollisionsOrb(element)
        }).toMutableList()
        ghosts = (ghosts.filter { element -> !isOutOfPlayzone(element)}).toMutableList()
        for(array in level.filasA){
            for(block in array){
                //check collisions
                if(block!=null){
                    checkCollisionsBlock(block)
                }
            }
        }

        for(ghost in ghosts){
            checkCollisionsGhost(ghost)
        }

    }

    fun processInput(event: MotionEvent){

        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN ->{
                touchStartX = event.x
                touchStartY = event.y
            }
            MotionEvent.ACTION_UP ->{
                touchEndX = event.x
                touchEndY = event.y

                val deltaX = touchEndX-touchStartX
                val deltaY = touchEndY-touchStartY
                Log.println(Log.VERBOSE,"TOUCH INPUT", deltaX.toString() + " - " + deltaY.toString())
                if (Math.abs(deltaY) > MIN_DISTANCE && Math.abs(deltaY) > Math.abs(deltaX))//swipe up-down
                {
                    if(deltaY < 0)//UP
                        player.direction = Player.Direction.UP
                    else
                        player.direction = Player.Direction.DOWN //down

                }
                else if(Math.abs(deltaX) > MIN_DISTANCE){ //swipe left-right
                    if(deltaX< 0)    //LEFT
                        player.direction = Player.Direction.LEFT
                    else//right
                        player.direction = Player.Direction.RIGHT

                }
                else
                    player.direction = Player.Direction.STATIC

            }
        }
    }

    private fun checkCollisionsBlock(block: Block){


        if(RectF.intersects(block.rectangle, player.rectangle)) {

            if(block.breakable && (temperatureBar.temperature>=breakableTempeature)){
                //Destroy block and return
                level.deleteBreakableBlock(block)
            }
            else{
                //If we change the player image we may change the numbers for the collisions
                when (player.direction) {
                    Player.Direction.UP -> player.setPosition(player.x, player.y + player.speed + scrollSpeed)
                    Player.Direction.DOWN -> player.setPosition(player.x, player.y - player.speed - scrollSpeed)
                    Player.Direction.LEFT -> player.setPosition(player.x + player.speed, player.y)
                    Player.Direction.RIGHT -> player.setPosition(player.x - player.speed, player.y)
                    Player.Direction.STATIC -> player.setPosition(player.x, player.y + scrollSpeed)
                }
                player.direction = Player.Direction.STATIC
            }
        }
    }

    private fun checkCollisionsOrb(orb: Orb):Boolean{
        val collides = RectF.intersects(orb.rectangle,player.rectangle)
        if(collides)
            temperatureBar.changeTemperature(orb)
        return collides
    }

    private fun checkCollisionsGhost(ghost : Ghost){

        if(RectF.intersects(ghost.rectangle,player.rectangle)){
            player.direction = Player.Direction.STATIC
            dead = true
        }
    }

    private fun isOutOfPlayzone(actor: Actor):Boolean{
        return RectF.intersects(actor.rectangle,overlay[3]) && !RectF.intersects(actor.rectangle,playingField)
    }

    private fun isOutOfPlayzone(player: Player): Boolean{
        //Si toca  a l'esquerra col·lisio
        if(RectF.intersects(overlay[0], player.rectangle)){
            player.setPosition(player.x + player.speed, player.y)
            player.direction = Player.Direction.STATIC
        }
        //Si toca a la dreta col·lisio
        else if(RectF.intersects(overlay[2], player.rectangle)){
            player.setPosition(player.x - player.speed, player.y)
            player.direction = Player.Direction.STATIC
        }
        //Si toca a la adalt col·lisio
        else if(RectF.intersects(overlay[1], player.rectangle)){
            player.setPosition(player.x, player.y + player.speed + scrollSpeed)
            player.direction = Player.Direction.STATIC
        }
        //Si toca surt per baix esta mort
        else if(RectF.intersects(overlay[3], player.rectangle)){
            return true
        }
        return false
    }

    fun getScore():Float{
        return score.score
    }
}