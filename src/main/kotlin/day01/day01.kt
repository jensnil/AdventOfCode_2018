package day01

import lib.CircularList

fun partOne(input : List<Int>) : Int {
    return input.sum()
}

fun partTwo(inputCircular : CircularList<Int>) : Int {
    var index = 0
    val usedFrequencies = mutableSetOf<Int>()
    var latestFrequency = 0

    while (!usedFrequencies.contains(latestFrequency)) {
        usedFrequencies.add(latestFrequency)
        latestFrequency += inputCircular[index]
        index++
    }
    return latestFrequency
}
