package unittests

import lib.getInput
import org.junit.jupiter.api.Test
import java.util.*


internal class Day18KtTest {

    @Test
    fun partOne() {
        val answer = day18.partOne(parse(getInput("day18.txt")), 10)
        println(answer)
        assert(answer == 598416)
    }

    @Test
    fun partTwo() {
        val answer = day18.partTwo(parse(getInput("day18.txt")), 1000000000)
        println(answer)
        assert(answer == 196310)
    }

    private fun parse(input : List<String>) : List<List<Char>> {
        //x=466, y=1429..1435
        return input.map {it.map { it }}
    }
}
