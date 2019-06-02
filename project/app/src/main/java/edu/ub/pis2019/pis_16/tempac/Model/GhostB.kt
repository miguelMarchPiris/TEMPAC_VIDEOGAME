package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import edu.ub.pis2019.pis_16.tempac.Model.Game.Engine

class GhostB(image : Bitmap) : Ghost(image) {
    override var onCorrectTemperature: Boolean = false
    override var topCorrectTemperature: Float = Engine.COLD_TEMPERATURE
    override var lowerCorrectTemperature: Float = 0.0f



    //private var paint = Paint()

    init {

    }

    override fun getIfCorrectTemperature(): Boolean {
        return (temperature in lowerCorrectTemperature..(topCorrectTemperature+0.1f))
    }

    override fun setSpecialBehaviour() {
        if (this.behaviour == BehaviourDefault || this.behaviour == BehaviourBelowTheLine){
            //todo solo pa saber si funciona bien
            Log.d("Behaviour blue", topCorrectTemperature.toString())
            this.behaviour = BehaviourB
            var jamon=0
        }
    }

}