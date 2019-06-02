package edu.ub.pis2019.pis_16.tempac.Model.Game

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.MotionEvent
import edu.ub.pis2019.pis_16.tempac.Model.*
import edu.ub.pis2019.pis_16.tempac.R
import java.util.*

abstract class Engine(var context : Context): Drawable{
    //Const values
    companion object {
        const val MAX_GHOSTS = 15
        const val MIN_DISTANCE = 105f
        const val PLAYFIELD_HEIGTH = 1400
        const val PLAYFIELD_WIDTH = 1080
        const val BOTTOM_PLAYING_FIELD : Float = 1625F
        const val LEFT_PLAYING_FIELD : Float = 0F
        const val RIGHT_PLAYING_FIELD : Float = 1080F
        const val TOP_PLAYING_FIELD : Float = 225F
        const val BLOCK_BREAKABLE_TEMPERATURE = 80f
        const val HOT_TEMPERATURE = 80f
        const val COLD_TEMPERATURE = 20f
        const val BACKGROUND_COLOR = Color.BLACK
        const val TOO_HIGH_LINE_GHOSTS = Engine.TOP_PLAYING_FIELD +Block.blockSide.times(1)
        const val HIGH_LINE_GHOSTS = Engine.TOP_PLAYING_FIELD +Block.blockSide.times(2)

        lateinit var overlayRect0 : RectF
        lateinit var overlayRect1 : RectF
        lateinit var overlayRect2 : RectF
        lateinit var overlayRect3 : RectF
    }

    //Game variables. internal
    protected open var scrollSpeed = 0f
    protected open var bonusScrollSpeed = 0f
    //protected var ghostSpeed = 0f
    protected open var baseScrollSpeed = 3f
    protected var extremeWeather = false //used to control the score. When its cold or hot it activates
    protected var screenCatchUp = false
    var dead = false
    var paused = false



    //Objects
    protected var temperatureBar = TemperatureBar()
    protected var score = Score()

    //Actors
    private var gfactory : GhostFactory = GhostFactory(initGhostImages())
    protected var player : Player = Player(PLAYFIELD_WIDTH/2f+35f,1000f, initPacmanImages())
    protected var ghosts : MutableList<Ghost> = mutableListOf()
    protected var dyingGhosts : MutableList<Ghost> = mutableListOf()
    protected abstract  var level : Level

    //Play zone rects
    protected val playingField = RectF(LEFT_PLAYING_FIELD, TOP_PLAYING_FIELD, RIGHT_PLAYING_FIELD, BOTTOM_PLAYING_FIELD)
    private val playingFieldLine = RectF(playingField.left-2.5f, playingField.top-2.5f,playingField.right+2.5f,playingField.bottom+2.5f)
    private val fieldLinePaint = Paint()
    protected val fieldPaint = Paint()
    private val overlay : List<RectF>
    private val overlayPaint = Paint()

    //Score text paint
    protected val textPaint = Paint()

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
        temperatureBar.temperature = 15f
        fieldLinePaint.style = Paint.Style.STROKE
        fieldLinePaint.strokeWidth = 5f
        fieldLinePaint.color = Color.WHITE

        //playfield
        overlayRect0 = RectF(0f,0f,playingField.left,playingField.bottom) //Left
        overlayRect1 = RectF(0f,0f,playingField.right,playingField.top) //Top
        overlayRect2 = RectF(playingField.right,0f,playingField.right,playingField.bottom) //Right
        overlayRect3 = RectF(0f,playingField.bottom,playingField.right,h.toFloat()+500f)    //Bottom
        overlay = listOf(overlayRect0,overlayRect1,overlayRect2,overlayRect3)
        overlayPaint.color = BACKGROUND_COLOR
        //overlayPaint.alpha = 100 //This makes it so we can se what its outside the playzone

        textPaint.color = Color.WHITE
        textPaint.setTypeface(Typeface.create("Droid Sans Mono", Typeface.ITALIC))
        textPaint.textSize = 45f
        textPaint.textAlign = Paint.Align.CENTER
    }

    private fun initPacmanImages() : List<Bitmap>{
        val pacman_open_up : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_open_up)
        val pacman_open_rigth : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_open_right)
        val pacman_open_down : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_open_down)
        val pacman_open_left : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_open_left)
        val pacman_closed : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pacman_closed)
        val pacmanImages : List<Bitmap> = listOf(pacman_open_up, pacman_open_rigth, pacman_open_down,pacman_open_left,pacman_closed)
        return pacmanImages
    }
    private fun initGhostImages() : List<Bitmap>{
        val ghost_red : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost_red)
        val ghost_blue : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost_blue)
        val ghost_green : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost_green)
        val ghost_yellow : Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ghost_yellow)
        val ghostsImages : List<Bitmap> = listOf(ghost_red,ghost_blue,ghost_green,ghost_yellow)
        return ghostsImages
    }

    abstract fun update()

    protected open fun gameState(temperature : Float){

        //On extreme temps we also change the scroll
        when {
            temperature >= HOT_TEMPERATURE -> {
                baseScrollSpeed +=0.00025f //Increment scroll as time goes on
                bonusScrollSpeed = 1.5f
                extremeWeather = true
            }
            temperature <= COLD_TEMPERATURE -> {
                baseScrollSpeed +=0.00025f //Increment scroll as time goes on
                bonusScrollSpeed = -0.75f
                extremeWeather = true
            }
            else -> {
                baseScrollSpeed +=0.0005f //Increment scroll as time goes on
                bonusScrollSpeed = 0f
                extremeWeather = false
            }
        }

        scrollSpeed = baseScrollSpeed + bonusScrollSpeed
    }

    protected fun scoreManagement(hotOrCold : Boolean ){
        if(hotOrCold)
            score.updateBonus(9)
        else
            score.updateBonus(0)

        score.update()

    }

    protected fun playerCatchingTop(){
        screenCatchUp = player.y < playingField.top + (playingField.bottom-playingField.top)/2f*0.8f
        //If the player is near the top, we make the scroll go faster
        if(screenCatchUp)
            scrollSpeed += player.ySpeed
        //Log.v("SCROLL", "SCROLL: " + scrollSpeed)
    }

    private fun deleteGhosts(temperature : Float){
        var sizeBeggining = ghosts.size

        val blues : MutableList<Ghost> = (ghosts.filterIsInstance<GhostB>()).toMutableList()
        val greens : MutableList<Ghost> = (ghosts.filterIsInstance<GhostG>()).toMutableList()
        val yellows : MutableList<Ghost> = (ghosts.filterIsInstance<GhostY>()).toMutableList()
        val reds : MutableList<Ghost> = (ghosts.filterIsInstance<GhostR>()).toMutableList()

        when(temperature){
            in 0f..20f -> {
                ghosts = blues
                //Greens, yellows and reds died
                dyingGhosts.addAll(greens.union(yellows.union(reds)))
            }
            in 20f..40f -> {
                ghosts = blues.union(greens).toMutableList()
                //Yellows and reds died
                dyingGhosts.addAll(yellows.union(reds))
            }
            in 40f..60f ->{
                ghosts = greens.union(yellows).toMutableList()
                //Blues and reds died
                dyingGhosts.addAll(reds.union(blues))
            }
            in 60f..80f ->{
                ghosts = yellows.union(reds).toMutableList()
                //Blues and greens died
                dyingGhosts.addAll(blues.union(greens))
            }
            in 80f..100f ->{
                ghosts = reds
                //Greens, yellows and reds died
                dyingGhosts.addAll(greens.union(yellows.union(blues)))
            }
        }
        for(ghost in dyingGhosts)
            ghost.dead = true
        var ghostsKilled = sizeBeggining - ghosts.size

        score.ghostBonus(ghostsKilled)
    }
    protected fun spawnGhost(){
        if(ghosts.size < MAX_GHOSTS){

            val lastArray= level.getLastArray() ?: return //same as (lastArray == null) {return}
            val pair=level.getPositionHoles(lastArray)
            val listOfHoles=pair.second
            //We select a random position in the line
            var positionInTheLine:Int= listOfHoles.get(Random().nextInt(listOfHoles.size))

            var g=gfactory.create(temperatureBar.temperature)
            g.setPosition((0.5f+positionInTheLine)*(Block.blockSide),(pair.first as Float))
            //g.setPosition(1.times(Block.blockSide.times(1.5f)),(pair.first as Float))

            ghosts.add(g)
        }
    }

    override fun draw(canvas: Canvas?){
        if (canvas != null) {

            canvas.scale(w/1080f,w/1080f)

            //Draw background
            canvas.drawColor(BACKGROUND_COLOR)

            if(!paused) {
                //Draw Actors
                player.draw(canvas)
                level.draw(canvas)
                for (ghost in ghosts) ghost.draw(canvas)
                for(ghost in dyingGhosts) ghost.draw(canvas)
                //Draw Overlay & Playzone
                for (rect in overlay)
                    canvas.drawRect(rect, overlayPaint)
                canvas.drawRect(playingFieldLine, fieldLinePaint)

                //Draw Objects
                temperatureBar.draw(canvas)

            }
            else{
                canvas.drawText("PAUSED", 1080 / 2f, PLAYFIELD_HEIGTH/2f, textPaint)
            }
            canvas.drawText("Score: " + score.getScore().toString(), 1080 / 2f, PLAYFIELD_HEIGTH + 300f, textPaint)


        }

    }
    protected fun processAnimations(){
        //Process animations
        player.animate()
        temperatureBar.animate()
        for(ghost in ghosts)
            ghost.animate()
        //Delete ghosts that have already ended their animation
        dyingGhosts = dyingGhosts.filter { ghost -> !ghost.deathAnimEnded }.toMutableList()
        for(ghost in dyingGhosts)
            ghost.animate()
    }
    protected open fun processPhysics(){
        checkOutOfPlayzone()
        checkCollisions()
    }
    protected fun checkOutOfPlayzone(){
        //check if player is out of the game
        if(isOutOfPlayzone(player))
            dead = true

        //check out of playzone
        //this is like a list comprehension in python, super fast and is the only clean way to delete elements
        //we also check breakable blocks that need to be deleted

        //Last array of the matrix
        val lastArray:Array<Block?>? = level.getLastArray()

        //This returns the positionY of every block on the line(even if there are no blocks
        var positionYLastArray : Float?= level.positionYArray[lastArray]
        if (positionYLastArray!=null){
            if (positionYLastArray>(playingField.bottom+Block.blockSide.times(1.5f))){
                level.deleteLine(lastArray as Array<Block?>)
            }
        }

        //delete the orbs if...
        level.orbs = (level.orbs.filter {element ->
            !isOutOfPlayzone(element) && //they are out the playground
                    !checkCollisionsOrb(element) // or the player collides with them
        }).toMutableList()

        //Deletes the ghost if they are below the screen
        ghosts = (ghosts.filter {element -> element.y <= BOTTOM_PLAYING_FIELD.plus(Block.blockSide.times(1.5f))}).toMutableList()
    }
    protected fun checkCollisions(){
        //goes over the level matrix and checks the blocks collisions
        for(array in level.matrixBlocks){
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
    open fun processInput(event: MotionEvent){

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
                /*
                else
                    player.direction = Player.Direction.STATIC
                */
            }
        }
    }

    private fun checkCollisionsBlock(block: Block){


        if(RectF.intersects(block.rectangle, player.rectangle)) {

            if(block.breakable && (temperatureBar.temperature>=BLOCK_BREAKABLE_TEMPERATURE)){
                //Destroy block and return
                level.deleteBreakableBlock(block)
            }
            else{
                //If we change the player image we may change the numbers for the collisions
                when (player.direction) {
                    Player.Direction.UP -> player.setPosition(player.x, player.y + player.ySpeed + scrollSpeed)
                    Player.Direction.DOWN -> player.setPosition(player.x, player.y - player.ySpeed - scrollSpeed)
                    Player.Direction.LEFT -> player.setPosition(player.x + player.xSpeed + scrollSpeed, player.y)
                    Player.Direction.RIGHT -> player.setPosition(player.x - player.xSpeed - scrollSpeed, player.y)
                    Player.Direction.STATIC -> player.setPosition(player.x, player.y)
                }
                player.direction = Player.Direction.STATIC
            }
        }
    }

    private fun checkCollisionsOrb(orb: Orb):Boolean{
        val collides = RectF.intersects(orb.rectangle,player.rectangle)
        if(collides)
            temperatureBar.changeTemperature(orb)
        deleteGhosts(temperatureBar.temperature)
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
            player.setPosition(player.x + player.xSpeed, player.y)
            player.direction = Player.Direction.STATIC
        }
        //Si toca a la dreta col·lisio
        else if(RectF.intersects(overlay[2], player.rectangle)){
            player.setPosition(player.x - player.xSpeed, player.y)
            player.direction = Player.Direction.STATIC
        }
        //Si toca a la adalt col·lisio
        else if(RectF.intersects(overlay[1], player.rectangle)){
            player.setPosition(player.x, player.y + player.ySpeed + scrollSpeed)
            player.direction = Player.Direction.STATIC
        }
        //Si toca surt per baix esta mort
        else if(RectF.intersects(overlay[3], player.rectangle)){
            return true
        }
        return false
    }

    fun getScore():Int{
        return score.getScore()
    }
}