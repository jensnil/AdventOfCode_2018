package unittests

import day_03.Claim
import lib.getInput
import org.junit.jupiter.api.Test

internal class Day03KtTest {

    @Test
    fun partOne() {
        val answer = day_03.partOne(parse(getInput("day03.txt")))
        println(answer)
        assert(answer == 97218)
    }

    @Test
    fun partTwo() {
        val answer = day_03.partTwo(parse(getInput("day03.txt")))
        println(answer)
        assert(answer == 717)
    }

    private fun parse(input : List<String>) : List<Claim> {
        // #1 @ 1,3: 4x4
        val regex = """^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$""".toRegex()
        return input.map {
            val matchResult = regex.find(it)
            Claim(matchResult!!.groupValues[1].toInt(),
                    matchResult.groupValues[2].toInt(),
                    matchResult.groupValues[3].toInt(),
                    matchResult.groupValues[4].toInt(),
                    matchResult.groupValues[5].toInt())
        }
    }
}