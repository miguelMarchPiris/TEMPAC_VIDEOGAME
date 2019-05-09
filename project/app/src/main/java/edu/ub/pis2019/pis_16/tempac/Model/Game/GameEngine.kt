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

    //Game variables
    private var scrollSpeed = 1.5f
    private var dead = false
    //Secundary Game Variables
    private var breakableTempeature=90f

    //Objects
    private var temperatureBar = TemperatureBar()
    private var score = Score()

    //Actors
    private var gfactory : GhostFactory = GhostFactory()
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
    private val MIN_DISTANCE = 105f

    //Screen variables
    var metrics = context.resources.displayMetrics
    var w = metrics.widthPixels
    var h = metrics.heightPixels

    init {

        temperatureBar.x = 100f
        temperatureBar.y = 100f
        temperatureBar.temperature = 0f
        fieldLinePaint.style = Paint.Style.STROKE
        fieldLinePaint.strokeWidth = 5f
        fieldLinePaint.color = Color.WHITE


        val overlayRect0 = RectF(0f,0f,playingField.left,playingField.bottom) //Left
        val overlayRect1 = RectF(0f,0f,playingField.right,playingField.top) //Top
        val overlayRect2 = RectF(playingField.right,0f,playingField.right,playingField.bottom) //Right
        val overlayRect3 = RectF(0f,playingField.bottom,playingField.right,h.toFloat()+500f)    //Bottom
        overlay = listOf(overlayRect0,overlayRect1,overlayRect2,overlayRect3)
        overlayPaint.color = Color.BLACK
        //overlayPaint.alpha = 100 //This makes it so we can se what its outside the playzone

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

    fun update(){
        //Process state of the game
        score.update(bonus = 0)
        if(dead){
            //Player died we should make the game end
        }
        //Process inputs
        player.update(scrollSpeed)
        level.update(scrollSpeed)
        //Process AI


        //Process physics
        processPhysics()
        //Process animations

        //Process sound

        //Process video
    }
    override fun draw(canvas: Canvas?){
        if (canvas != null) {
            canvas.scale(w/1080f,w/1080f)
            //Draw background
            canvas.drawColor(Color.BLACK)
            //Draw Actors
            player.draw(canvas)
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

        for(block in level.blocks){
            //check collisions
            checkCollisionsBlock(block)
            //check out of playzone
            if(isOutOfPlayzone(block))
                level.blocks.remove(block)
        }
        for(orb in level.orbs){
            //check collisions
            checkCollisionsOrb(orb)
            //check out of playzone
            if(isOutOfPlayzone(orb))
                level.orbs.remove(orb)
        }
        for(ghost in ghosts){
            //check collisions

            //check out of playzone
            if(isOutOfPlayzone(ghost))
                ghosts.remove(ghost)
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
        //if they collide and is not breakable

        //Todo: I tried this but pacman gets automatically to the bottom ????
        //if (RectF.intersects(block.rectangle, player.rectangle) && !block.breakable || (temperatureBar.temperature>=breakableTempeature)) {
        if (RectF.intersects(block.rectangle, player.rectangle) && (!block.breakable || (temperatureBar.temperature<breakableTempeature))) {
            //If we change the player image we may change the numbers for the collisions
            when (player.direction) {
                Player.Direction.UP -> player.setPosition(player.x, player.y + player.speed + scrollSpeed)
                Player.Direction.DOWN -> player.setPosition(player.x, player.y - player.speed - scrollSpeed)
                Player.Direction.LEFT -> player.setPosition(player.x + player.speed, player.y)
                Player.Direction.RIGHT -> player.setPosition(player.x - player.speed, player.y)
                Player.Direction.STATIC -> player.setPosition(player.x, player.y + scrollSpeed)
            }
            player.direction = Player.Direction.STATIC

        }else if(RectF.intersects(block.rectangle, player.rectangle) && block.breakable && (temperatureBar.temperature>=breakableTempeature)){
            //Todo, write somewhere the variable that contains the limit temperature to break blocks
            level.blocks.remove(block)
        }


        /* Maybe this is faster? idk
        if(block.breakable){
            if (RectF.intersects(block.rectangle, player.rectangle)) {
                //If we change the player image we may change the numbers for the collisions
                when (player.direction) {
                    Player.Direction.UP -> player.setPosition(player.x, player.y + 5 + scrollSpeed)
                    Player.Direction.DOWN -> player.setPosition(player.x, player.y - 5 - scrollSpeed)
                    Player.Direction.LEFT -> player.setPosition(player.x + 5, player.y)
                    Player.Direction.RIGHT -> player.setPosition(player.x - 5, player.y)
                }
                player.direction = Player.Direction.STATIC

        }else{
            if(RectF.intersects(block.rectangle, player.rectangle)) level.blocks.remove(block)
        }

        */
    }

    private fun checkCollisionsOrb(orb: Orb){
        //Maybe the deleting is the lag (i dont think so) but i really suspect the problem is the thermometer
        //var orbsChecked : MutableList<Int> = mutableListOf<Int>()
        if(RectF.intersects(orb.rectangle,player.rectangle)){
            temperatureBar.changeTemperature(orb)
            level.orbs.remove(orb)
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