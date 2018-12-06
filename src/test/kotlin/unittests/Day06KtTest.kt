package unittests

import day04.Event
import lib.getInput
import org.junit.jupiter.api.Test

internal class Day06KtTest {

    @Test
    fun partOne() {
        val answer = day06.partOne(parse(getInput("day06.txt")))
        println(answer)
        assert(answer == 3660)
    }

    @Test
    fun partTwo() {
        val answer = day06.partTwo(parse(getInput("day06.txt")))
        println(answer)
        assert(answer == 35928)
    }

    private fun parse(input : List<String>) : List<Pair<Int,Int>> {
        // #1 @ 1,3: 4x4
        val regex = """^(\d+), (\d+)$""".toRegex()
        return input.map {
            val matchResult = regex.find(it)
            Pair(
                    matchResult!!.groupValues[1].toInt(),
                    matchResult!!.groupValues[2].toInt())
        }
    }
}