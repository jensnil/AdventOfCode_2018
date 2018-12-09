package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day09KtTest {

    @Test
    fun partOne() {
        val answer = day09.partOne(parse(getInput("day09.txt")))
        println(answer)
        assert(answer == 361466L)
    }


    @Test
    fun partTwo() {
        val answer = day09.partTwo(parse(getInput("day09.txt")))
        println(answer)
        assert(answer == 2945918550)
    }


    private fun parse(input : List<String>) : Pair<Int,Int> {
        val regex = """^(\d+) players; last marble is worth (\d+) points$""".toRegex()
        return input.map {
            val matchResult = regex.find(it)
            Pair(
                    matchResult!!.groupValues[1].toInt(),
                    matchResult!!.groupValues[2].toInt())
        }.first()
    }
}