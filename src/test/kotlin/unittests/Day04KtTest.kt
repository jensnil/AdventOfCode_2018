package unittests

import day_04.Event
import lib.getInput
import org.junit.jupiter.api.Test

internal class Day04KtTest {

    @Test
    fun partOneAndTwo() {
        val answer = day_04.partOneAndTwo(parse(getInput("day04.txt")))
        println(answer)
        assert(answer == Pair(39584, 55053))
    }

    private fun parse(input : List<String>) : List<Event> {
        // #1 @ 1,3: 4x4
        val regex = """^\[(\d+)\-(\d+)\-(\d+) (\d+):(\d+)\] (.*)$""".toRegex()
        val regex2 = """^\[(.*)#(\d+)(.*)$""".toRegex()
        return input.map {
            val matchResult = regex.find(it)
            if (matchResult!!.groupValues[6].startsWith("Guard")) {
                val matchResult2 = regex2.find(it)
                Event(matchResult!!.groupValues[1].toInt(),
                        matchResult.groupValues[2].toInt(),
                        matchResult.groupValues[3].toInt(),
                        matchResult.groupValues[4].toInt(),
                        matchResult.groupValues[5].toInt(),
                        matchResult.groupValues[6],
                        matchResult2!!.groupValues[2].toInt())

            } else {
                Event(matchResult!!.groupValues[1].toInt(),
                        matchResult.groupValues[2].toInt(),
                        matchResult.groupValues[3].toInt(),
                        matchResult.groupValues[4].toInt(),
                        matchResult.groupValues[5].toInt(),
                        matchResult.groupValues[6],
                        0)
            }
        }
    }
}