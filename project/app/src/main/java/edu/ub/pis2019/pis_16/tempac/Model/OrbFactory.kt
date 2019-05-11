package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import java.util.*

class OrbFactory() : Factory {

    override fun create(temperature: Float): Orb {

        var r : Random = Random()
        when(temperature){
            //NOTE: all the numbers in the setRandomChandes added must be equal to 100. These tell u the probablities for each orb to be choosed.
            in 0f..25f -> OrbType.setChances(40,10,40,10)
            in 25f..50f -> OrbType.setChances(30, 20, 30, 20)
            in 50f..75f -> OrbType.setChances(20, 30, 20, 30)
            in 75f..100f -> OrbType.setChances(10, 40, 10, 40)
            else -> OrbType.setChances(25, 25, 25, 25)
        }



        //here we generate the random in OrbType.
        val randomType = OrbType.getRandomType()
        var addsubValues = r.nextInt(50)
        var muldivValues = r.nextInt(2)+2


        return when(randomType){ //i need to make the objects lul
            OrbType.ADD -> OrbAdd(addsubValues)
            OrbType.SUB -> OrbSub(addsubValues)
            OrbType.MUL -> OrbMul(muldivValues)
            OrbType.DIV -> OrbDiv(muldivValues)
        }
    }
}