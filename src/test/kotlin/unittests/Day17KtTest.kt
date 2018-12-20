package unittests

import lib.getInput
import org.junit.jupiter.api.Test
import java.util.*


internal class Day17KtTest {

    @Test
    fun partOne() {
        val answer = day17.partOne(parse(getInput("day17.txt")))
        println(answer)
        assert(answer == 39367)
    }

    @Test
    fun partTwo() {
        val answer = day17.partTwo(parse(getInput("day17.txt")))
        println(answer)
        assert(answer == 33061)
    }

    private fun parse(input : List<String>) : List<Pair<Char, Triple<Int,Int,Int>>> {
        //x=466, y=1429..1435
        val regex = """^([xy])=(\d+), ([xy])=(\d+)\.\.(\d+)$""".toRegex()
        return input.map {
            val match = regex.find(it)
            Pair(match!!.groupValues[1][0], Triple(match.groupValues[2].toInt(), match.groupValues[4].toInt(), match.groupValues[5].toInt()))
        }
    }
}
