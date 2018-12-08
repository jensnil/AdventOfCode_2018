package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day08KtTest {

    @Test
    fun partOne() {
        val answer = day08.partOne(parse(getInput("day08.txt")))
        println(answer)
        assert(answer == 42768)
    }

    @Test
    fun partTwo() {
        val answer = day08.partTwo(parse(getInput("day08.txt")))
        println(answer)
        assert(answer == 34348)
    }

    private fun parse(input : List<String>) : List<Int> {
        return input.first().split(" ").map { it.toInt() }
    }
}