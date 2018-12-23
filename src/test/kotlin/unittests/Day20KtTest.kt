package unittests

import lib.getInput
import org.junit.jupiter.api.Test
import java.util.*


internal class Day20KtTest {

    @Test
    fun partOne() {
        val answer = day20.partOne(parse(getInput("day20.txt")))
        println(answer)
        assert(answer == 3739)
    }

    @Test
    fun partTwo() {
        val answer = day20.partTwo(parse(getInput("day20.txt")))
        println(answer)
        assert(answer == 196310)
    }

    private fun parse(input : List<String>) : String {
        return input[0]
    }
}
