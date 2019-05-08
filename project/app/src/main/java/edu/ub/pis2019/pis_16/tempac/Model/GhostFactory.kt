package edu.ub.pis2019.pis_16.tempac.Model

class GhostFactory : Factory {
    override fun create(temperature: Int): Ghost {

        var randomType: GhostType = GhostType.BLUE
        while (true) {
            val tempLimits = GhostType.tempLimits[randomType]
            if (temperature > tempLimits!!.first && temperature <= tempLimits!!.third)
                break
            randomType = GhostType.getRandomType()
        }

        return when(randomType){
            GhostType.RED -> GhostR()
            GhostType.BLUE -> GhostB()
            GhostType.GREEN -> GhostG()
            GhostType.YELLOW -> GhostY()
        }
    }
}