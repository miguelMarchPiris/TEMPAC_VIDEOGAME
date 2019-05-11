package edu.ub.pis2019.pis_16.tempac.Model

import java.util.*

enum class GhostType {
    BLUE,
    GREEN,
    YELLOW,
    RED;

    companion object {
        val tempLimits: HashMap<GhostType, Triple<Float,Float,Float>> = hashMapOf(
            BLUE to Triple(0f,20f, 40f),
            GREEN to Triple(20f,40f, 60f),
            YELLOW to Triple(40f,60f,80f),
            RED to Triple(60f,80f, 100f))

        fun getRandomType() : GhostType {
            return values()[Random().nextInt(values().size)]
        }
     }
}