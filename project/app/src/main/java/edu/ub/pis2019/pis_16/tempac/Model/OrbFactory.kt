package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap
import java.util.*

class OrbFactory() : Factory {

    /*
    * The TempValue enum class it will be used if the value in the orbs must change depending the temperature. Now we arent using it
    * */
    private enum class TempValue {COLD,COOL,HOT,BURN}
    private var state = TempValue.COLD
    private var r : Random = Random()


    override fun create(temperature: Float): Orb {
        when(temperature){
            //NOTE: all the numbers in the setRandomChandes added must be equal to 100. These tell u the probablities for each orb to be choosed.
            in 0f..25f -> {
                OrbType.setChances(60, 15, 20, 5)
                state = TempValue.COLD
            }
            in 25f..50f -> {
                OrbType.setChances(45, 30, 15, 10)
                state = TempValue.COOL
            }
            in 50f..75f -> {
                OrbType.setChances(30, 45, 10, 15)
                state = TempValue.HOT
            }
            in 75f..100f -> {
                OrbType.setChances(15, 60, 5, 20)
                state = TempValue.BURN
            }
            else -> OrbType.setChances(25, 25, 25, 25)
        }



        //here we generate the random in OrbType.
        val randomType = OrbType.getRandomType()
        var addsubValues = orbValueAddSub(state)
        var muldivValues = r.nextInt(2)+2


        return when(randomType){ //i need to make the objects lul
            OrbType.ADD -> OrbAdd(addsubValues)
            OrbType.SUB -> OrbSub(addsubValues)
            OrbType.MUL -> OrbMul(muldivValues)
            OrbType.DIV -> OrbDiv(muldivValues)
        }

    }

    private fun orbValueAddSub(state : TempValue) : Int{

        var chance = r.nextInt(100)
        return when(chance){
            in 0..5 -> r.nextInt(5)+1
            in 6..30 -> r.nextInt(10)+6
            in 31..70 -> r.nextInt(20)+16
            in 71..95 -> r.nextInt(10)+36
            in 96..100 -> r.nextInt(5)+46
            else -> 0
        }

    }
}