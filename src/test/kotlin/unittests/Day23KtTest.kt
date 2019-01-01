package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day23KtTest {

    @Test
    fun partOne() {
        val answer = day23.partOne(parse(getInput("day23.txt")))
        println(answer)
        assert(answer == 430L)
    }

    @Test
    fun partTwo() {
        val answer = day23.partTwo(parse(getInput("day23.txt")))
        println(answer)
        assert(answer == 80250793L)
    }

    private fun parse(input : List<String>) : List<Pair<Triple<Long,Long,Long>, Long>> {
        //pos=<0,0,0>, r=4
        val regex = """^pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(\d+)$""".toRegex()
        return input.map {
            val match = regex.find(it)
            Pair(Triple(match!!.groupValues[1].toLong(),match.groupValues[2].toLong(),match.groupValues[3].toLong()), match.groupValues[4].toLong())
        }
    }
}
