package unittests

import lib.getInput
import org.junit.jupiter.api.Test
import java.util.*


internal class Day19KtTest {

    @Test
    fun partOne() {
        val answer = day19.partOne(parse(getInput("day19.txt")))
        println(answer)
        //assert(answer == 598416)
    }

    @Test
    fun partTwo() {
        val answer = day19.partTwo(parse(getInput("day19.txt")))
        println(answer)
        assert(answer == 196310)
    }

    private fun parse(input : List<String>) : Pair<Int, List<List<Any>>> {
        val regex = """^\#ip (\d+)$""".toRegex()
        val regex2 = """^(\w+) (\d+) (\d+) (\d+)$""".toRegex()
        val match = regex.find(input[0])
        val ip = match!!.groupValues[1]
        return Pair(ip.toInt(), input.drop(1).map {
            val match2 = regex2.find(it)
            listOf(match2!!.groupValues[1], match2!!.groupValues[2].toInt(), match2!!.groupValues[3].toInt(), match2!!.groupValues[4].toInt())
        })
    }
}
