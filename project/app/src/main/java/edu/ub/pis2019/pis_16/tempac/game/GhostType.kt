package edu.ub.pis2019.pis_16.tempac.game

import java.util.*

enum class GhostType {
    BLUE,
    GREEN,
    YELLOW,
    RED;

    companion object {
        val tempLimits: HashMap<GhostType, Triple<Int,Int,Int>> = hashMapOf(BLUE to Triple(0,20, 35),
            GREEN to Triple(20,35, 40),
            YELLOW to Triple(35,40,50),
            RED to Triple(40,50, 100))

        fun getRandomType() :GhostType {
            return values()[Random().nextInt(values().size)]
        }
     }
}