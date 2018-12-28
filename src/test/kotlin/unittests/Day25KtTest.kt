package unittests

import lib.getInput
import org.junit.jupiter.api.Test
import java.util.*


internal class Day25KtTest {

    @Test
    fun partOne() {
        val answer = day25.partOne(parse(getInput("day25.txt")))
        println(answer)
        assert(answer == 310)
    }

    @Test
    fun partTwo() {
        val answer = day25.partTwo(parse(getInput("day25.txt")))
        println(answer)
        //assert(answer == 1008)
    }

    private fun parse(input : List<String>) : List<List<Int>> {
        val regex = """^(-?\d+),(-?\d+),(-?\d+),(-?\d+)$""".toRegex()

        return input.map {
            val match = regex.find(it)
            listOf(match!!.groupValues[1].toInt(), match!!.groupValues[2].toInt(), match!!.groupValues[3].toInt(), match!!.groupValues[4].toInt())
        }
    }
}
