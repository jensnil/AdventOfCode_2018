package unittests

import day04.Event
import lib.getInput
import org.junit.jupiter.api.Test

internal class Day05KtTest {

    @Test
    fun partOne() {
        val answer = day05.partOne(parse(getInput("day05.txt")))
        println(answer)
        assert(answer == 9822)
    }

    @Test
    fun partTwo() {
        val answer = day05.partTwo(parse(getInput("day05.txt")))
        println(answer)
        assert(answer == 5726)
    }

    private fun parse(input : List<String>) : String {
        return input.first()
    }
}