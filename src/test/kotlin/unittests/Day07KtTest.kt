package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day07KtTest {

    @Test
    fun partOne() {
        val answer = day07.partOne(parse(getInput("day07.txt")))
        println(answer)
        assert(answer == "BKCJMSDVGHQRXFYZOAULPIEWTN")
    }

    @Test
    fun partTwo() {
        val answer = day07.partTwo(parse(getInput("day07.txt")), 5, 60)
        println(answer)
        assert(answer == 1040)
    }

    private fun parse(input : List<String>) : List<Pair<Char,Char>> {
        // #1 @ 1,3: 4x4
        val regex = """^Step (\w) must be finished before step (\w) can begin.$""".toRegex()
        return input.map {
            val matchResult = regex.find(it)
            Pair(
                    matchResult!!.groupValues[1][0],
                    matchResult!!.groupValues[2][0])
        }
    }
}