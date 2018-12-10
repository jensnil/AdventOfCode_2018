package unittests

import lib.getInput
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class Day10KtTest {

    @Test
    fun partOne() {
        val answer = day10.partOne(parse(getInput("day10.txt")))
        println(answer)
        assert(answer ==
"""#####...#.......######..######..#....#..#####.....##....#....#
#....#..#.......#............#..##...#..#....#...#..#...##...#
#....#..#.......#............#..##...#..#....#..#....#..##...#
#....#..#.......#...........#...#.#..#..#....#..#....#..#.#..#
#####...#.......#####......#....#.#..#..#####...#....#..#.#..#
#..#....#.......#.........#.....#..#.#..#..#....######..#..#.#
#...#...#.......#........#......#..#.#..#...#...#....#..#..#.#
#...#...#.......#.......#.......#...##..#...#...#....#..#...##
#....#..#.......#.......#.......#...##..#....#..#....#..#...##
#....#..######..######..######..#....#..#....#..#....#..#....#
""")
    }


    @Test
    fun partTwo() {
        val answer = day10.partTwo(parse(getInput("day10.txt")))
        println(answer)
        assert(answer == 10239)
    }


    private fun parse(input : List<String>) : List<Pair<Pair<Int,Int>,Pair<Int,Int>>> {
        val regex = """^position=\<\w*(.+),\w*(.+)\> velocity=\<\w*(.+),\w*(.+)\>$""".toRegex()
        return input.map {
            val matchResult = regex.find(it)
            Pair(
            Pair(
                    matchResult!!.groupValues[1].trim().toInt(),
                    matchResult!!.groupValues[2].trim().toInt()),
            Pair(
            matchResult!!.groupValues[3].trim().toInt(),
            matchResult!!.groupValues[4].trim().toInt()))
        }
    }
}