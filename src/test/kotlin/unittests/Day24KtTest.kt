package unittests

import lib.getInput
import org.junit.jupiter.api.Test
import java.util.*


internal class Day24KtTest {

    @Test
    fun partOne() {
        val answer = day23.partOne(parse(getInput("day23.txt")))
        println(answer)
        //assert(answer == 7299)
    }

    @Test
    fun partTwo() {
        val answer = day23.partTwo(parse(getInput("day23.txt")))
        println(answer)
        //assert(answer == 1008)
    }

    private fun parse(input : List<String>) : List<Pair<Triple<Int,Int,Int>, Int>> {
        //pos=<0,0,0>, r=4
        val regex = """^pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(\d+)$""".toRegex()
        return input.map {
            val match = regex.find(it)
            Pair(Triple(match!!.groupValues[1].toInt(),match.groupValues[2].toInt(),match.groupValues[3].toInt()), match.groupValues[4].toInt())
        }
    }
}
