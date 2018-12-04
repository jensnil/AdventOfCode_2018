package unittests

import lib.circular
import lib.getInput
import org.junit.jupiter.api.Test

internal class Day01KtTest {

    @Test
    fun partOne() {
        val answer = day01.partOne(parse(getInput("day01.txt")))
        println(answer)
        assert(answer == 442)
    }

    @Test
    fun partTwo() {
        val answer = day01.partTwo(parse(getInput("day01.txt")).circular())
        println(answer)
        assert(answer== 59908)
    }

    private fun parse(lines : List<String>) : List<Int> = lines.map { it.toInt() }
}