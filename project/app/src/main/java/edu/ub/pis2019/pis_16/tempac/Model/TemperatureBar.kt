package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.*
import android.support.v4.graphics.ColorUtils
import edu.ub.pis2019.pis_16.tempac.Model.Game.GameEngine

class TemperatureBar(): Object() {
    private var oldTemp = 0f
    private var drawTemp = 0f
    var temperature = 0f
        set(value) {
            if (value in 0.0..100.1){
                field = value
            }
            else if(value<0f){
                field=0f
            }else{
                field=100f
            }
        }

    var height = 50
    var width = 600

    private var paintEdge = Paint()
    private var paintShape = Paint()
    private var paintCenter = Paint()

    private var startingColor = Color.BLUE
    private var endColor = Color.BLUE
    private var edgeColorRatio = 0f

    init {
        paintShape.color = Color.WHITE
        paintShape.style = Paint.Style.FILL_AND_STROKE
        paintShape.strokeWidth = 15f

        paintEdge.color = GameEngine.BACKGROUND_COLOR
        paintEdge.style = Paint.Style.FILL_AND_STROKE
        paintEdge.strokeWidth = 45f
        paintEdge.maskFilter = BlurMaskFilter(40f,BlurMaskFilter.Blur.NORMAL)
        paintCenter.color = Color.BLUE
        paintCenter.style = Paint.Style.FILL
        paintCenter.textSize = 50f
        paintCenter.textAlign =Paint.Align.RIGHT
        paintCenter.setTypeface(Typeface.create("Droid Sans Mono", Typeface.BOLD))
    }
    override fun draw(canvas: Canvas?){
        val rect = RectF(x+height/2f,y,x+width-height*0.7f,y+height)
        //Paint glow
        canvas?.drawRoundRect(rect,13f,13f, paintEdge)
        canvas?.drawCircle(x,y+height/2f,height*1.1f,paintEdge)

        //Paint shape
        canvas?.drawRoundRect(rect,13f,13f, paintShape)
        canvas?.drawCircle(x,y+height/2f,height*1.1f,paintShape)


        if(drawTemp <= 15) {
            canvas?.drawCircle(x,y+height/2f,height*1.1f*drawTemp/15f,paintCenter)
        }
        else{
            canvas?.drawCircle(x,y+height/2f,height*1.1f,paintCenter)
            rect.right = 65f + (drawTemp/100f)*(x+width-height*0.7f-65f)
            canvas?.drawRoundRect(rect,13f,13f,paintCenter)
        }

        canvas?.drawText(drawTemp.toInt().toString()+" ºC",x+width+170f,y+height/2f+20f, paintCenter)
    }
    fun animate(){
        edgeColorRatio = edgeColorRatio.rem(1f)
        //Animate borders if temperature is extreme
        if(drawTemp < GameEngine.COLD_TEMPERATURE) {
            edgeColorRatio += 1/80f
            paintEdge.color = ColorUtils.blendARGB(GameEngine.BACKGROUND_COLOR, Color.BLUE, edgeColorRatio)
        }
        else if (drawTemp > GameEngine.HOT_TEMPERATURE) {
            edgeColorRatio += 1/60f
            paintEdge.color = ColorUtils.blendARGB(GameEngine.BACKGROUND_COLOR, Color.RED, edgeColorRatio)
        }
        else {
            edgeColorRatio = 0f
            paintEdge.color = GameEngine.BACKGROUND_COLOR
        }
        //We animate the temperature
        if(!(drawTemp in temperature -0.01 .. temperature+0.01))
            drawTemp += (temperature - oldTemp) / 25f
        else
            drawTemp = temperature
        //Color transition
        when(drawTemp){
            in 0f..20f ->{
                startingColor = Color.BLUE
                endColor = Color.BLUE
            }
            in 20f..40f ->{
                startingColor = Color.BLUE
                endColor = Color.GREEN
            }
            in 40f..65f ->{
                startingColor = Color.GREEN
                endColor = Color.YELLOW
            }
            in 80f..100f ->{
                startingColor = Color.RED
                endColor = Color.RED
            }
            in 65f..80f ->{
                startingColor = Color.YELLOW
                endColor = Color.RED
            }

        }

        paintCenter.color = ColorUtils.blendARGB(startingColor,endColor,((drawTemp-15)%25)*4f/100f)
    }
    fun changeTemperature(orb : Orb){
        //We store the old temperature for animation
        drawTemp = temperature
        oldTemp = temperature
        when(orb.operand) {

            Orb.Operand.ADD -> temperature += orb.number
            Orb.Operand.SUB -> temperature -= orb.number
            Orb.Operand.MUL -> temperature *= orb.number
            Orb.Operand.DIV -> temperature /= orb.number
        }
    }
}