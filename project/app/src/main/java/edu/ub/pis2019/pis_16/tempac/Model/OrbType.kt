package edu.ub.pis2019.pis_16.tempac.Model

import android.util.Log
import java.util.*

enum class OrbType {
    ADD,
    SUB,
    MUL,
    DIV;

    companion object {
        var r = Random()
        var addBound = 0
        var subBound = 0
        var mulBound = 0
        var divBound = 0

        fun setChances(add: Int, sub: Int, mul: Int, div: Int){
            addBound = add
            subBound = sub
            mulBound = mul
            divBound = div
        }

        fun getRandomType() : OrbType {

            var ran = r.nextInt(100)

            val secondBound = addBound+subBound
            val thirdBound = secondBound+mulBound
            val fourthBound = thirdBound+divBound

            return when(ran){
                in 0..addBound -> values()[0]
                in addBound..secondBound -> values()[1]
                in secondBound..thirdBound -> values()[2]
                in thirdBound..fourthBound -> values()[3]
                else -> values()[0] //in case with a problem with the random generates a sum.
            }
        }


    }
}