package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.*
import android.support.v4.graphics.ColorUtils

class TemperatureBar(): Object() {
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

    var paintEdge = Paint()
    var paintCenter = Paint()
    val borderPath = Path()

    var startingColor = Color.BLUE
    var endColor = Color.BLUE
    var colorProgress = 0f
    init {
        paintEdge.color = Color.WHITE
        paintEdge.style = Paint.Style.FILL_AND_STROKE
        paintEdge.strokeWidth = 15f
        paintCenter.color = Color.BLUE
        paintCenter.style = Paint.Style.FILL
        paintCenter.textSize = 50f
        paintCenter.textAlign =Paint.Align.RIGHT
    }
    override fun draw(canvas: Canvas?){

        val rect = RectF(x+height/2f,y,x+width-height*0.7f,y+height)

        canvas?.drawRoundRect(rect,13f,13f, paintEdge)
        canvas?.drawCircle(x,y+height/2f,height*1.1f,paintEdge)

        //Color transition
        when(temperature){
            in 15f..40f ->{
                startingColor = Color.BLUE
                endColor = Color.GREEN
                paintEdge.color = Color.WHITE
            }
            in 40f..65f ->{
                startingColor = Color.GREEN
                endColor = Color.YELLOW
                paintEdge.color = Color.WHITE
            }
            in 65f..90f ->{
                startingColor = Color.YELLOW
                endColor = Color.RED
                paintEdge.color = Color.WHITE
            }
            in 90f..100f ->{
                startingColor = Color.RED
                endColor = Color.RED
                paintEdge.color = Color.WHITE
            }
            in 100f..105f ->{
                paintEdge.color = Color.RED
            }
        }

        paintCenter.color = ColorUtils.blendARGB(startingColor,endColor,((temperature-15)%25)*4f/100f)
        if(temperature <= 15) {
            canvas?.drawCircle(x,y+height/2f,height*1.1f*temperature/15f,paintCenter)
        }
        else{
            canvas?.drawCircle(x,y+height/2f,height*1.1f,paintCenter)
            rect.right = 65f + (temperature/100f)*(x+width-height*0.7f-65f)
            canvas?.drawRoundRect(rect,13f,13f,paintCenter)
        }

        canvas?.drawText(temperature.toInt().toString()+" ºC",x+width+150f,y+height/2f+20f, paintCenter)
    }

    fun changeTemperature(orb : Orb){
        when(orb.operand) {

            Orb.Operand.ADD -> temperature += orb.number
            Orb.Operand.SUB -> temperature -= orb.number
            Orb.Operand.MUL -> temperature *= orb.number
            Orb.Operand.DIV -> temperature /= orb.number
            else -> temperature = temperature //i didnt know what to put in here
        }
    }
    /*fun colorGradient(color1:Int, color2:Int, percentage: Float):Int{
        val A = 255
        val R = (Color.red(color2)-Color.red(color1))*percentage +Color.red(color1)
        val G = (Color.green(color2)-Color.green(color1)) * percentage + Color.blue(color1)
        val B  = (Color.blue(color2)-Color.blue(color1))*percentage+Color.blue(color1)
        val color:Int = A and 0xff shl 24 or (R and 0xff shl 16) or (G and 0xff shl 8) or (B and 0xff)
    }*/
}