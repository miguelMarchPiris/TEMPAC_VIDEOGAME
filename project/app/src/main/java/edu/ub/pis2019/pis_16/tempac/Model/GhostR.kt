package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import edu.ub.pis2019.pis_16.tempac.Model.Game.Engine

class GhostR(red : Bitmap) : Ghost(red) {
    override var onCorrectTemperature: Boolean = false
    override var topCorrectTemperature: Float = 100.0f
    override var lowerCorrectTemperature: Float = Engine.HOT_TEMPERATURE



    //private var paint = Paint()

    init {

    }

    override fun getIfCorrectTemperature(): Boolean {
        return (temperature in lowerCorrectTemperature..(topCorrectTemperature+0.1f))
    }
    override fun setSpecialBehaviour() {
        this.behaviour=BehaviourR
    }
}