package unit_tests

import day_03.Claim
import input.day_01.input_03
import org.junit.jupiter.api.Test

internal class Day_03KtTest {

    @Test
    fun partOne() {
        val answer = day_03.partOne(parse(input_03)!!)
        println(answer)
    }

    @Test
    fun partTwo() {
        val answer = day_03.partTwo(parse(input_03)!!)
        println(answer)
    }

    private fun parse(input : String) : List<Claim>? {
        // #1 @ 1,3: 4x4
        val regex = """^#(\d+) @ (\d+),(\d+): (\d+)x(\d+)$""".toRegex()
        val lines = input.split("\n")
        return lines.map {
            val matchResult = regex.find(it)
            Claim(matchResult!!.groupValues[1].toInt(), matchResult.groupValues[2].toInt(), matchResult.groupValues[3].toInt(), matchResult.groupValues[4].toInt(), matchResult.groupValues[5].toInt())
        }
    }
}