package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class GhostY(yellow : Bitmap) : Ghost(yellow) {
    override var onCorrectTemperature: Boolean = false
    override var topCorrectTemperature: Float = 65.0f
    override var lowerCorrectTemperature: Float = 60.0f



    //private var paint = Paint()

    init {

    }

    override fun getOnCorrectTemperature(temperature : Float): Boolean {
        return (temperature in lowerCorrectTemperature..(topCorrectTemperature+0.1f))
    }
    override fun setSpecialBehaviour() {
        this.behaviour=BehaviourDefault
    }
}