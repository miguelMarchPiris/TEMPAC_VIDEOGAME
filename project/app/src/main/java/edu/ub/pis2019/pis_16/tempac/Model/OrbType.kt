package edu.ub.pis2019.pis_16.tempac.Model

import java.util.*

enum class OrbType {
    ADD,
    SUB,
    MUL,
    DIV;

    companion object {
        val tempLimits: HashMap<OrbType, Triple<Float,Float,Float>> = hashMapOf(
            ADD to Triple(0f,20f, 50f),
            SUB to Triple(20f,35f, 40f),
            MUL to Triple(35f,40f,50f),
            DIV to Triple(50f,50f, 100f))

        fun setRandomChances(sum : Int, sub : Int, mul : Int, div :Int){
            
        }

        fun getRandomType() : OrbType {
            return values()[Random().nextInt(values().size)]
        }


    }
}