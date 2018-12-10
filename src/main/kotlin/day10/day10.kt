package day10

import java.lang.StringBuilder

private fun findMessage(input: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>): Pair<String,Int> {
    var best: List<Pair<Pair<Int, Int>, Pair<Int, Int>>>? = null
    var diff = Int.MAX_VALUE
    var stars = input
    var index = 0
    var bestIndex = 0
    while (index < 20000) {
        stars = stars.map { Pair(Pair(it.first.first + it.second.first, it.first.second + it.second.second), it.second) }
        val newDiff = stars.map { it.first.second }.max()!! - stars.map { it.first.second }.min()!!
        if (newDiff < diff) {
            diff = newDiff
            best = stars.map { it }
            bestIndex = index
        }
        index++
    }
    val toReturn = StringBuilder()
    for (i in best!!.map { it.first.second }.min()!! .. best!!.map{ it.first.second}.max()!!) {
        for (j in best!!.map { it.first.first }.min()!! .. best!!.map{ it.first.first}.max()!!) {
            if (best!!.filter { it.first == Pair(j, i) }.isEmpty()) {
                toReturn.append(".")
            } else {
                toReturn.append("#")
            }
        }
        toReturn.append("\n")
    }
    return Pair(toReturn.toString(), bestIndex)
}

fun partOne(input: List<Pair<Pair<Int,Int>,Pair<Int,Int>>>) : String {
    return findMessage(input).first
}



fun partTwo(input: List<Pair<Pair<Int,Int>,Pair<Int,Int>>>) : Int {
    return findMessage(input).second
}
