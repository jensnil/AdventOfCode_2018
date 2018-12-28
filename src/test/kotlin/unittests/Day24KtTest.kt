package unittests

import lib.getInput
import org.junit.jupiter.api.Test
import java.util.*


internal class Day24KtTest {

    @Test
    fun partOne() {
        val answer = day24.partOne(parse(getInput("day24.txt")))
        println(answer)
        assert(answer == 25088)
    }

    @Test
    fun partTwo() {
        val answer = day24.partTwo(parse(getInput("day24.txt")))
        println(answer)
        assert(answer == 2002)
    }

    private fun parse(input : List<String>) : List<List<Any>> {
        //4081 units each with 8009 hit points (immune to slashing, radiation; weak to bludgeoning, cold) with an attack that does 17 fire damage at initiative 7
        val regex = """^(\d+) units each with (\d+) hit points (\(([^;]*)(; (.*))?\))?\s?with an attack that does (\d+) (\w+) damage at initiative (\d+)$""".toRegex()
        var isImmuneSystem = true

        return input.mapNotNull {
            if (it == "Infection:") {
                isImmuneSystem = false
                null
            } else {
                val match = regex.find(it)
                if (match == null) {
                    null
                } else {
                    listOf(isImmuneSystem, match!!.groupValues[1].toInt(), match!!.groupValues[2].toInt(), match!!.groupValues[4], match!!.groupValues[6], match!!.groupValues[7].toInt(), match!!.groupValues[8], match!!.groupValues[9].toInt())
                }
            }
        }
    }
}
