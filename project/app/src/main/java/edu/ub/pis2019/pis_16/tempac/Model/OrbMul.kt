package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF

class OrbMul(number: Int) : Orb(number){

    init{
        //setPosition(500f,250f)
        operand = Orb.Operand.MUL
    }
}