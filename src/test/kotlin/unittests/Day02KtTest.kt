package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day02KtTest {

    @Test
    fun partOne() {
        val answer = day_02.partOne(parse(getInput("day02.txt")))
        println(answer)
        assert(answer == 7776)
    }

    @Test
    fun partTwo() {
        val answer = day_02.partTwo(parse(getInput("day02.txt")))
        println(answer)
        assert(answer== "wlkigsqyfecjqqmnxaktdrhbz")
    }

    private fun parse(lines : List<String>) : List<String> = lines
}