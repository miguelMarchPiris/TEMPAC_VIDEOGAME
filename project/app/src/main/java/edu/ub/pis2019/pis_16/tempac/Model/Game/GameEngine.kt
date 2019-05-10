package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import edu.ub.pis2019.pis_16.tempac.Model.*
import edu.ub.pis2019.pis_16.tempac.R

/*GameEngine singelton????
* Usar la classe collisionable per a algo, lol.
* */
class GameEngine(var context: Context) : Drawable {

    //Const values
    companion object {
        const val MAX_GHOSTS = 6
        const val MIN_DISTANCE = 105f
    }

    //Game variables
    private var scrollSpeed = 1.5f
    private var dead = false

    //Secondary Game Variables
    private var breakableTempeature=90f

    //Objects
    private var temperatureBar = TemperatureBar()
    private var score = Score()

    //Actors
    private var gfactory : GhostFactory = GhostFactory(initGhostImages())
    private var player : Player = Player(500f,1000f, initPacmanImages())
    private var ghosts : MutableList<Ghost> = mutableListOf<Ghost>()
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
    var metrics = context.resources.displayMetrics
    var w = metrics.widthPixels
    var h = metrics.heightPixels

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
        //overlayPaint.alpha = 100 //This makes it so we can se what its outside the playzone

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
        score.update(bonus = 0)
        if(dead){
            //Player died we should make the game end
        }
        //Process inputs
        player.update(scrollSpeed)

        //Process AI
        generateGhost()
        level.update(scrollSpeed)
        for(ghost in ghosts) ghost.update(scrollSpeed)

        //Process physics
        processPhysics()
        //Process animations

        //Process sound

        //Process video
    }

    fun generateGhost(){
        if(ghosts.size <= MAX_GHOSTS){
            ghosts.add(gfactory.create(temperatureBar.temperature))
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
        val lastArray:Array<Block?> =level.getLastArray()
        //This returns the positionY of every block on the line(even if there are no blocks
        var positionYLastArray : Float?=level.positionYArray.get(lastArray)
        if (positionYLastArray!=null){
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

    private fun breakableState(){
        //TODO() //probably we should add a list in level that marks the possible changeable blocks (Ask Miguel)
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
}