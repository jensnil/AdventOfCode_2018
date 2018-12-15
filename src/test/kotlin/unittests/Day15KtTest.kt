package unittests

import lib.getInput
import org.junit.jupiter.api.Test


internal class Day15KtTest {

    @Test
    fun partOne() {
        val answer = day15.partOne(parse(getInput("day15ttt.txt")))
        println(answer)
        //assert(answer == 0)
    }

//    @Test
//    fun partTwo() {
//        val answer = day14.partTwo(parse(getInput("day14.txt")))
//        println(answer)
//        assert(answer == 20286858)
//    }

    private fun parse(input : List<String>) : List<String> {
        return input
    }
}