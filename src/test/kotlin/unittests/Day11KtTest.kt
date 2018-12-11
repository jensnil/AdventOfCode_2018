package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day11KtTest {

    @Test
    fun partOne() {
        val answer = day11.partOne(parse(getInput("day11.txt")))
        println(answer)
        assert(answer == Pair(21,61))
    }


    @Test
    fun partTwo() {
        val answer = day11.partTwo(parse(getInput("day11.txt")))
        println(answer)
        assert(answer == Triple(232,251,12))
    }


    private fun parse(input : List<String>) : Pair<Int,Int> {
        val regex = """^(\d+),(\d+)$""".toRegex()
        return input.map {
            val matchResult = regex.find(it)
            Pair(matchResult!!.groupValues[1].trim().toInt(), matchResult!!.groupValues[2].trim().toInt())
        }.first()
    }
}