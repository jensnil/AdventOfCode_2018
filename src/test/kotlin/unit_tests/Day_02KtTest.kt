package unit_tests

import input.day_01.input_02
import org.junit.jupiter.api.Test

internal class Day_02KtTest {

    @Test
    fun partOne() {
        val answer = day_02.partOne(input_02.split("\n"))
        println(answer)
        assert(answer == 7776)
    }

    @Test
    fun partTwo() {
        val answer = day_02.partTwo(input_02.split("\n"))
        println(answer)
        assert(answer== "wlkigsqyfecjqqmnxaktdrhbz")
    }
}