package unittests

import lib.getInput
import org.junit.jupiter.api.Test

internal class Day12KtTest {

    @Test
    fun partOne() {
        val answer = day12.partOne(parse(getInput("day12.txt")))
        println(answer)
        assert(answer == 6201)
    }


    @Test
    fun partTwo() {
        val answer = day12.partTwo(parse(getInput("day12.txt")))
        println(answer)
        assert(answer == 9300000001023L)
    }


    private fun parse(input : List<String>) : Pair<String,List<Pair<String,String>>> {
        val regex1 = """^initial state: ([\#\.]+)$""".toRegex()
        val regex2 = """^([\#\.]+) => ([\#\.])$""".toRegex()
        val matchResult = regex1.find(input.first())
        return Pair(
                matchResult!!.groupValues[1],

                input.subList(2,input.size).map {
                    val matchResult2 = regex2.find(it)
                    Pair(
                            matchResult2!!.groupValues[1],
                            matchResult2!!.groupValues[2])
                })
    }
}