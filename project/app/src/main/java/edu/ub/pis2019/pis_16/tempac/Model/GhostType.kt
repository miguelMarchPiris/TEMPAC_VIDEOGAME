package edu.ub.pis2019.pis_16.tempac.Model

import java.util.*

enum class GhostType {
    BLUE,
    GREEN,
    YELLOW,
    RED;

    companion object {
        val tempLimits: HashMap<GhostType, Triple<Float,Float,Float>> = hashMapOf(
            BLUE to Triple(0f,20f, 35f),
            GREEN to Triple(20f,35f, 40f),
            YELLOW to Triple(35f,40f,50f),
            RED to Triple(40f,50f, 100f))

        fun getRandomType() : GhostType {
            return values()[Random().nextInt(values().size)]
        }
     }
}