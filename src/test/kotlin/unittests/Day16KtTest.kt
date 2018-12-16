package unittests

import lib.getInput
import org.junit.jupiter.api.Test


internal class Day16KtTest {

    @Test
    fun partOne() {
        val answer = day16.partOne(parse(getInput("day16a.txt")))
        println(answer)
        assert(answer == 509)
    }

    @Test
    fun partTwo() {
        val answer = day16.partTwo(parse(getInput("day16a.txt")), parse2(getInput("day16b.txt")))
        println(answer)
        assert(answer == 496)
    }

    private fun parse(input : List<String>) : List<Triple<List<Int>,List<Int>,List<Int>>> {
        val regex = listOf("""^Before: \[(\d+), (\d+), (\d+), (\d+)\]$""".toRegex(), """^(\d+) (\d+) (\d+) (\d+)$""".toRegex(), """^After:  \[(\d+), (\d+), (\d+), (\d+)\]$""".toRegex())
        return (0..(input.size-1)/4).map {
            val match0 = regex[0].find(input[4 * it])
            val match1 = regex[1].find(input[4 * it + 1])
            val match2 = regex[2].find(input[4 * it + 2])
            Triple(listOf(match0!!.groupValues[1].toInt(), match0!!.groupValues[2].toInt(), match0!!.groupValues[3].toInt(), match0!!.groupValues[4].toInt()),
                    listOf(match1!!.groupValues[1].toInt(), match1!!.groupValues[2].toInt(), match1!!.groupValues[3].toInt(), match1!!.groupValues[4].toInt()),
                    listOf(match2!!.groupValues[1].toInt(), match2!!.groupValues[2].toInt(), match2!!.groupValues[3].toInt(), match2!!.groupValues[4].toInt()))
        }
    }

    private fun parse2(input : List<String>) : List<List<Int>> {
        val regex = """^(\d+) (\d+) (\d+) (\d+)$""".toRegex()
        return input.map {
            val match0 = regex.find(it)
            listOf(match0!!.groupValues[1].toInt(), match0!!.groupValues[2].toInt(), match0!!.groupValues[3].toInt(), match0!!.groupValues[4].toInt())
        }
    }
}