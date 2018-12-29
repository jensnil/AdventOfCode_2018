package unittests

import lib.getInput
import org.junit.jupiter.api.Test


internal class Day15KtTest {

    @Test
    fun partOne() {
        val answer = day15.partOne(parse(getInput("day15.txt")))
        println(answer)
        assert(answer == 190012)
    }

    @Test
    fun partTwo() {
        val answer = day15.partTwo(parse(getInput("day15.txt")))
        println(answer)
        assert(answer == 34364)
    }

    private fun parse(input : List<String>) : List<String> {
        return input
    }
}