package unit_tests

import input.day_01.input_01
import lib.circular
import org.junit.jupiter.api.Test

internal class Day_01KtTest {

    @Test
    fun partOne() {
        val answer = day_01.partOne(input_01.split("\n").map { it.toInt() })
        println(answer)
        assert(answer == 442)
    }

    @Test
    fun partTwo() {
        val answer = day_01.partTwo(input_01.split("\n").map { it.toInt() }.circular())
        println(answer)
        assert(answer== 59908)
    }
}