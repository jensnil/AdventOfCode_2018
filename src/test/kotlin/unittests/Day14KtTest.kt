package unittests

import lib.getInput
import org.junit.jupiter.api.Test


internal class Day14KtTest {

    @Test
    fun partOne() {
        val answer = day14.partOne(parse(getInput("day14.txt")))
        println(answer)
        assert(answer == "5371393113")
    }

    @Test
    fun partTwo() {
        val answer = day14.partTwo(parse(getInput("day14.txt")))
        println(answer)
        assert(answer == 20286858)
    }

    private fun parse(input : List<String>) : String {
        return input.first()
    }
}