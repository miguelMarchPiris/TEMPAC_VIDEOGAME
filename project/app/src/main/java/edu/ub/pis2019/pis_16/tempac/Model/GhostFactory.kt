package edu.ub.pis2019.pis_16.tempac.Model

import android.graphics.Bitmap

class GhostFactory(var ghostImages : List<Bitmap>) : Factory {
    override fun create(temperature: Float): Ghost {

        var randomType: GhostType = GhostType.BLUE
        while (true) {
            val tempLimits = GhostType.tempLimits[randomType]
            //Do not change the warning
            if ((temperature >= tempLimits!!.first) && (temperature <= tempLimits!!.third))
                break
            randomType = GhostType.getRandomType()
        }

        return when(randomType){
            GhostType.RED -> GhostR(ghostImages[0])
            GhostType.BLUE -> GhostB(ghostImages[1])
            GhostType.GREEN -> GhostG(ghostImages[2])
            GhostType.YELLOW -> GhostY(ghostImages[3])
        }
    }

}