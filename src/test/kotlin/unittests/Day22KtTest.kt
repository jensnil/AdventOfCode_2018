package unittests

import lib.getInput
import org.junit.jupiter.api.Test
import java.util.*


internal class Day22KtTest {

    @Test
    fun partOne() {
        val answer = day22.partOne(parse(getInput("day22.txt")))
        println(answer)
        assert(answer == 7299)
    }

    @Test
    fun partTwo() {
        val answer = day22.partTwo(parse(getInput("day22.txt")))
        println(answer)
        assert(answer == 1008)
    }

    private fun parse(input : List<String>) : Pair<Int, Pair<Int,Int>> {
        //depth: 11109
        //target: 9,731

        val regex = """^depth: (\d+)$""".toRegex()
        val regex2 = """^target: (\d+),(\d+)$""".toRegex()
        val match = regex.find(input[0])
        val match2 = regex2.find(input[1])
        return Pair(match!!.groupValues[1].toInt(), Pair(match2!!.groupValues[1].toInt(), match2!!.groupValues[2].toInt()))
    }
}
