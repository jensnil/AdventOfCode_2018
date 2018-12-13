package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day13KtTest {

    @Test
    fun partOne() {
        val answer = day13.partOne(parse(getInput("day13.txt")))
        println(answer)
        assert(answer == Pair(116, 91))
    }


    @Test
    fun partTwo() {
        val answer = day13.partTwo(parse(getInput("day13.txt")))
        println(answer)
        assert(answer == Pair(8, 23))
    }


    private fun parse(input : List<String>) : List<String> {
        return input
    }
}