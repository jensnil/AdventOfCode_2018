package day22

import day15.printGame
import java.util.*

val cave = mutableMapOf<Pair<Int,Int>, Int>()

fun printCave(target : Pair<Int, Int>, depth : Int) {
    (0..cave.keys.map { it.second }.max()!!).forEach { y ->
        (0..cave.keys.map { it.first }.max()!!).forEach {x ->
            val region = Pair(x, y)
            print(getType(region, target, depth))
        }
        println()
    }
    println()
}

fun getType (region : Pair<Int,Int>, target : Pair<Int,Int>, depth : Int) : Char {
    return if (region == Pair(0, 0)) {
        'M'
    } else if (region == target) {
        'T'
    } else return when (erosionLevel(region, depth) % 3) {
        0 -> '.'
        1 -> '='
        2 -> '|'
        else -> ' '
    }
}

fun getRiskLevel (region : Pair<Int,Int>, depth : Int) : Int {
    return erosionLevel(region, depth) % 3
}

fun erosionLevel(region : Pair<Int,Int>, depth : Int) : Int {
    return if (cave.contains(region)) {
        ((cave[region]!! + depth) % 20183)
    } else {
        0
    }
}

fun geologicIndex(region : Pair<Int,Int>, target : Pair<Int, Int>, depth : Int) : Int{
    return if (region == Pair(0,0)) {
        0
    } else if (region == target) {
        0
    } else if (region.second == 0) {
        region.first * 16807
    } else if (region.first == 0) {
        region.second * 48271
    } else {
        erosionLevel(Pair(region.first - 1, region.second), depth) * erosionLevel(Pair(region.first, region.second - 1), depth)
    }
}


fun partOne(input: Pair<Int, Pair<Int,Int>>) : Int {
    val depth = input.first
    val target = input.second

    (0..target.second).forEach { y ->
        (0..target.first).forEach {x ->
            val region = Pair(x, y)
            cave[region] = geologicIndex(region, target, depth)
            //printCave(target, depth)
        }
    }

    var riskLevel = 0
    (0..target.second).forEach { y ->
        (0..target.first).forEach {x ->
            val region = Pair(x, y)
            riskLevel += getRiskLevel(region, depth)
        }
    }
    return riskLevel
}


////////////Part 2

data class State ( val cost : Int, val sortValue : Int, val gear : Char, val region : Pair<Int, Int>) : Comparable<State> {
    override fun compareTo(other: State) = sortValue.compareTo(other.sortValue)
    override fun equals(other: Any?): Boolean {
        val otherState = other as State
        return region == otherState.region && gear == otherState.gear
    }

    override fun hashCode(): Int {
        return (region.toString() + "/" + gear).hashCode()
    }
}

var states = PriorityQueue<State>()
val lowestValues = mutableMapOf<Pair<Int,Int>,MutableMap<Char,Int>>()

fun expandHorizontally(target : Pair<Int, Int>, depth : Int) {
    val maxX = cave.keys.map { it.first }.max()!! + 1
    (0..cave.keys.map { it.second }.max()!!).forEach { y ->
        val region = Pair(maxX, y)
        cave[region] = geologicIndex(region, target, depth)
    }
}
fun expandVertically(target : Pair<Int, Int>, depth : Int) {
    val maxY = cave.keys.map { it.second }.max()!! + 1
    (0..cave.keys.map { it.first }.max()!!).forEach { x ->
        val region = Pair(x, maxY)
        cave[region] = geologicIndex(region, target, depth)
    }
}

fun gearCost(currentGear : Char, nextGear : Char) : Int {
    return if (currentGear != nextGear) 7 else 0
}

fun getNewStates(state : State, target : Pair<Int, Int>, depth : Int) : List<State> {
    return listOf(Pair(-1, 0),Pair(1, 0),Pair(0, -1),Pair(0, 1)).map {
            val next = Pair(state.region.first + it.first, state.region.second + it.second)
            when (getType(next, Pair(0,0), depth)) {
                '.' -> {
                    listOf(
                            State( state.cost + 1 + gearCost(state.gear, 'C'), state.cost + 1 + gearCost(state.gear, 'C') + Math.abs(target.first - next.first) + Math.abs(target.second - next.second), 'C', next),
                            State( state.cost + 1 + gearCost(state.gear, 'T'), state.cost + 1 + gearCost(state.gear, 'T') + Math.abs(target.first - next.first) + Math.abs(target.second - next.second), 'T', next)
                    )
                }
                '=' -> {
                    listOf(
                            State( state.cost + 1 + gearCost(state.gear, 'C'), state.cost + 1 + gearCost(state.gear, 'C') + Math.abs(target.first - next.first) + Math.abs(target.second - next.second), 'C', next),
                            State( state.cost + 1 + gearCost(state.gear, 'N'), state.cost + 1 + gearCost(state.gear, 'N') + Math.abs(target.first - next.first) + Math.abs(target.second - next.second), 'N', next)
                    )
                }
                '|' -> {
                    listOf(
                            State( state.cost + 1 + gearCost(state.gear, 'T'), state.cost + 1 + gearCost(state.gear, 'T') + Math.abs(target.first - next.first) + Math.abs(target.second - next.second), 'T', next),
                            State( state.cost + 1 + gearCost(state.gear, 'N'), state.cost + 1 + gearCost(state.gear, 'N') + Math.abs(target.first - next.first) + Math.abs(target.second - next.second), 'N', next)
                    )
                }
                else -> emptyList()
            }
        }.flatten().filter { it.region.first >= 0 && it.region.second >= 0 }
}

fun partTwo(input: Pair<Int, Pair<Int,Int>>) : Int {
    val depth = input.first
    val target = input.second
    val start = Pair(0, 0)
    val startState = State(0, target.first + target.second, 'T', start)

    cave[start] = geologicIndex(start, target, depth)
    states.add(startState)
    if (!lowestValues.containsKey(start)) {
        lowestValues[start] = mutableMapOf()
    }
    lowestValues[start]!![startState.gear] = startState.cost

    while (!states.isEmpty()) {
        val state = states.remove()!!
        if (state.region == target && state.gear == 'T') {
            return state.cost
        }

        if (state.region.first == cave.keys.map { it.first }.max()!!) {
            expandHorizontally(target, depth)
            //printCave(target, depth)
        }
        if (state.region.second == cave.keys.map { it.second }.max()!!) {
            expandVertically(target, depth)
            //printCave(target, depth)
        }
        val newStates = getNewStates(state, target, depth)
        //states.addAll(newStates)

        newStates.forEach {newState ->
            val existingState = lowestValues[newState.region]?.let { it[newState.gear] }
            if (newState.cost <  existingState ?: Int.MAX_VALUE) {
                if (!lowestValues.containsKey(newState.region)) {
                    lowestValues[newState.region] = mutableMapOf()
                }
                lowestValues[newState.region]?.let { it[newState.gear] = newState.cost }
                states.add(newState)
            }
        }
    }

    return -1
}

