package day24

data class FightingGroup(val id : Int, val isImmuneSystem: Boolean, var units : Int, val hitpoints : Int, val immune : Set<String>, val weak : Set<String>, var attack : Int, val attackType : String, val initiative : Int) {
    var targetedBy : FightingGroup? = null
    var targeting : FightingGroup? = null

    fun effectivePower() : Int {
        return units * attack
    }

    fun hitBy(hitter : FightingGroup) : Int {
        return hitter.effectivePower() * if (immune.contains(hitter.attackType)) {
            0
        } else if (weak.contains(hitter.attackType)) {
            2
        } else {
            1
        }
    }

    fun attack() : Boolean {
        if (targeting != null) {
            var killedUnits = targeting!!.hitBy(this) / targeting!!.hitpoints
            if (killedUnits > targeting!!.units) {
                killedUnits = targeting!!.units
            }
            targeting!!.units -= killedUnits
            //printAttack(this, killedUnits)
            return targeting!!.units == 0
        } else {
            return false
        }
    }
}

private fun populate(input: List<List<Any>>): MutableList<FightingGroup> {
    var immuneId = 0
    var infectionId = 0
    return input.map {
        val immune = mutableSetOf<String>()
        val weak = mutableSetOf<String>()
        val regex = """^(\w+) to (\w+)(, (\w+))?(, (\w+))?(, (\w+))?$""".toRegex()
        val virtueOneMatch = regex.find(it[3] as String)
        if (virtueOneMatch?.let { it.groupValues[1]} == "immune") {
            immune.add(virtueOneMatch!!.groupValues[2])
            if (virtueOneMatch!!.groupValues[4] != "") {
                immune.add(virtueOneMatch!!.groupValues[4])
            }
            if (virtueOneMatch!!.groupValues[6] != "") {
                immune.add(virtueOneMatch!!.groupValues[6])
            }
            if (virtueOneMatch!!.groupValues[8] != "") {
                immune.add(virtueOneMatch!!.groupValues[8])
            }
        } else if (virtueOneMatch?.let { it.groupValues[1]} == "weak") {
            weak.add(virtueOneMatch!!.groupValues[2])
            if (virtueOneMatch!!.groupValues[4] != "") {
                weak.add(virtueOneMatch!!.groupValues[4])
            }
            if (virtueOneMatch!!.groupValues[6] != "") {
                weak.add(virtueOneMatch!!.groupValues[6])
            }
            if (virtueOneMatch!!.groupValues[8] != "") {
                weak.add(virtueOneMatch!!.groupValues[8])
            }
        }
        val virtueTwoMatch = regex.find(it[4] as String)
        if (virtueTwoMatch?.let { it.groupValues[1]} == "immune") {
            immune.add(virtueTwoMatch!!.groupValues[2])
            if (virtueTwoMatch!!.groupValues[4] != "") {
                immune.add(virtueTwoMatch!!.groupValues[4])
            }
            if (virtueTwoMatch!!.groupValues[6] != "") {
                immune.add(virtueTwoMatch!!.groupValues[6])
            }
            if (virtueTwoMatch!!.groupValues[8] != "") {
                immune.add(virtueTwoMatch!!.groupValues[8])
            }
        } else if (virtueTwoMatch?.let { it.groupValues[1]} == "weak") {
            weak.add(virtueTwoMatch!!.groupValues[2])
            if (virtueTwoMatch!!.groupValues[4] != "") {
                weak.add(virtueTwoMatch!!.groupValues[4])
            }
            if (virtueTwoMatch!!.groupValues[6] != "") {
                weak.add(virtueTwoMatch!!.groupValues[6])
            }
            if (virtueTwoMatch!!.groupValues[8] != "") {
                weak.add(virtueTwoMatch!!.groupValues[8])
            }
        }
        if (it[0] as Boolean) {
            immuneId++
        } else {
            infectionId++
        }

        FightingGroup(if (it[0] as Boolean) immuneId else infectionId, it[0] as Boolean, it[1] as Int, it[2] as Int, immune, weak, it[5] as Int, it[6] as String, it[7] as Int)
    }.toMutableList()
}

fun printArmies(fightingGroups : List<FightingGroup>) {
    println("Immune System:")
    fightingGroups.filter { it.isImmuneSystem}.forEach {
        println("Group ${it.id} contains ${it.units}")
    }
    println("Infection:")
    fightingGroups.filter { !it.isImmuneSystem}.forEach {
        println("Group ${it.id} contains ${it.units}")
    }
    println()
}

fun printDealDamage(fighter : FightingGroup, targets : List<FightingGroup>) {
    targets.forEach { target ->
        println("${if (fighter.isImmuneSystem) "Immune System" else "Infection"} group ${fighter.id} would deal defending group ${target.id} ${target.hitBy(fighter)} damage")
    }
}

fun printAttack(fighter : FightingGroup, killed : Int) {
    println("${if (fighter.isImmuneSystem) "Immune System" else "Infection"} group ${fighter.id} attack defending group ${fighter.targeting!!.id}, killing ${killed} units")
}

private fun fight(input: List<List<Any>>, boost : Int): Pair<Boolean, Int> {
    var fightingGroups = populate(input)
    fightingGroups.filter { it.isImmuneSystem }.forEach { it.attack += boost }
    var oldHitPointSum = -1
    var currentHitPointSum = fightingGroups.map { it.units * it.hitpoints }.sum()

    while (fightingGroups.any { it.isImmuneSystem } && fightingGroups.any { !it.isImmuneSystem } && oldHitPointSum != currentHitPointSum) {
        //printArmies(fightingGroups)

        val selectionOrder = fightingGroups.sortedWith(compareBy({ it.isImmuneSystem }, { -it.effectivePower() }, { -it.initiative }))
        selectionOrder.forEach { fighter ->
            val damage = fightingGroups.filter { enemy -> enemy.isImmuneSystem != fighter.isImmuneSystem && enemy.targetedBy == null }
            //printDealDamage(fighter, damage)
            val sortedDamage = damage.filter { it.hitBy(fighter) > 0 }.sortedWith(compareBy({ -it.hitBy(fighter) }, { -it.effectivePower() }, { -it.initiative }))
            if (!sortedDamage.isEmpty()) {
                val max = sortedDamage.first()
                max.targetedBy = fighter
                fighter.targeting = max
            }
        }
        //println()
        val sortedAttackingGroups = fightingGroups.sortedWith(compareBy({ -it.initiative }))
        sortedAttackingGroups.forEach { fighter ->
            if (fighter.hitpoints > 0) {
                if (fighter.attack()) {
                    fightingGroups.remove(fighter.targeting)
                }
            }
        }
        fightingGroups.forEach {
            it.targeting = null
            it.targetedBy = null
        }

        oldHitPointSum = currentHitPointSum
        currentHitPointSum = fightingGroups.map { it.units * it.hitpoints }.sum()
    }
    //printArmies(fightingGroups)

    return if (oldHitPointSum == currentHitPointSum) {
        Pair(false, -1)
    } else {
        Pair(fightingGroups.any { it.isImmuneSystem }, fightingGroups.sumBy { it.units })
    }
}

fun partOne(input: List<List<Any>>) : Int {
    return fight(input, 0).second
}

fun partTwo(input: List<List<Any>>) : Int {
    var boost = 0
    var fightResult = Pair(false, -1)
    while (!fightResult.first) {
        fightResult = fight(input, boost)
        boost++
    }
    return fightResult.second
}
