package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap

class OrbFactory() : Factory {
    override fun create(temp: Float): Ghost {

        when{
            (temp>0f || temp == 0f) && (temp <25f) -> OrbType.setRandomChances(40,10,40,10) //ADD, SUM, MUL, DIV
            (temp>25f || temp == 25f) && (temp <50f) -> OrbType.setRandomChances(30, 20, 30, 20)
            (temp>50f || temp == 50f) && (temp <75f) -> OrbType.setRandomChances(20, 30, 20, 30)
            (temp>75f || temp == 75f) && (temp <100f || temp == 100f) -> OrbType.setRandomChances(10, 40, 10, 40)
        }

        val randomType = OrbType.getRandomType()


        return when(randomType){ //i need to make the objects lul
            OrbType.ADD -> OrbAdd()
            OrbType.SUB -> OrbSub()
            OrbType.MUL -> OrbMul()
            OrbType.DIV -> OrbDiv()
        }
    }
}