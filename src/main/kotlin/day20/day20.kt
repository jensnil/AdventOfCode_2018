package day20

import java.util.*

var regex =  ""
val board = mutableMapOf<Pair<Int,Int>, Char>()
val visited = mutableMapOf<Pair<Int,Int>, Int>()

fun printGame(remainingInput : String, current : Pair<Int,Int>) {
    val minX = board.map { it.key.first }.min()!!
    val maxX = board.map { it.key.first }.max()!!
    val minY = board.map { it.key.second }.min()!!
    val maxY = board.map { it.key.second }.max()!!

    (minY .. maxY).forEach {y ->
        (minX .. maxX).forEach {x ->
            if (x == 0 && y == 0) {
                print("X")
            } else if (x == current.first && y == current.second) {
                print("O")
            } else {
                print(board[Pair(x,y)]?.let { it } ?: run { ' ' })
            }
        }
        println()
    }
    println(remainingInput)
    println()
}

fun getCellType(cell : Pair<Int,Int>) : Char {
    return if (cell.first % 2 == 0 && cell.second % 2 == 0) {
        '.'
    } else if (cell.first % 2 != 0 && cell.second % 2 != 0) {
        '#'
    } else {
        '?'
    }
}

fun updateCell(current : Pair<Int,Int>, door : Pair<Int,Int>) {
    (-1 .. 1).forEach {y ->
        (-1 .. 1).forEach {x ->
            val next = Pair(current.first + x, current.second + y)
            if (board[next] == null) {
                board[next] = getCellType(next)
            }
        }
    }
    if (door.first % 2 != 0) {
        board[door] = '|'
    } else {
        board[door] = '-'
    }
}

fun indexOfMatchingParanthesis(startIndex : Int) : Int {
    var parenthesisCount = 0
    (startIndex..regex.length).forEach {
        if (regex[it] == '(') {
            parenthesisCount++
        } else if (regex[it] == ')') {
            parenthesisCount--
        }
        if (parenthesisCount == 0) {
            return it
        }
    }
    return -1
}

fun createPaths(startIndex : Int, endIndex : Int, start : Pair<Int,Int>) : Set<Pair<Int,Int>> {
    var states = mutableSetOf(start)
    val statesToReturn = mutableSetOf<Pair<Int,Int>>()

    var currentIndex = startIndex
    while (currentIndex < endIndex)  {
        states = states.map {currentPosition ->
            //printGame(currentIndex.toString(), currentPosition)
            when (regex[currentIndex]) {
                'N' -> {
                    val door = Pair(currentPosition.first, currentPosition.second - 1)
                    val next = Pair(currentPosition.first, currentPosition.second - 2)
                    updateCell(next, door)
                    setOf(next)
                }
                'E' -> {
                    val door = Pair(currentPosition.first + 1, currentPosition.second)
                    val next = Pair(currentPosition.first + 2, currentPosition.second)
                    updateCell(next, door)
                    setOf(next)
                }
                'W' -> {
                    val door = Pair(currentPosition.first - 1, currentPosition.second)
                    val next = Pair(currentPosition.first - 2, currentPosition.second)
                    updateCell(next, door)
                    setOf(next)
                }
                'S' -> {
                    val door = Pair(currentPosition.first, currentPosition.second + 1)
                    val next = Pair(currentPosition.first, currentPosition.second + 2)
                    updateCell(next, door)
                    setOf(next)
                }
                '(', '^' -> {
                    val matchingParenthesis = indexOfMatchingParanthesis(currentIndex)
                    val toReturn = createPaths(currentIndex + 1, matchingParenthesis, currentPosition)
                    currentIndex = matchingParenthesis
                    toReturn
                }
                '|' -> {
                    statesToReturn.add(currentPosition)
                    setOf(start)
                }
                else -> {
                    emptySet()
                }
            }
        }.flatten().toMutableSet()
        currentIndex++
    }
    return statesToReturn
}

fun traverse(current : Pair<Int,Int>) {
    val n = Pair(current.first, current.second - 1)
    val n2 = Pair(current.first, current.second - 2)
    val e = Pair(current.first - 1, current.second)
    val e2 = Pair(current.first - 2, current.second)
    val w = Pair(current.first + 1, current.second)
    val w2 = Pair(current.first + 2, current.second)
    val s = Pair(current.first, current.second + 1)
    val s2 = Pair(current.first, current.second + 2)
    if (board[n] == '-' && visited[n2] ?: Int.MAX_VALUE > visited[current]!! + 1) {
        visited[n2] = visited[current]!! + 1
        traverse(n2)
    }
    if (board[e] == '|' && visited[e2] ?: Int.MAX_VALUE > visited[current]!! + 1) {
        visited[e2] = visited[current]!! + 1
        traverse(e2)
    }
    if (board[w] == '|' && visited[w2] ?: Int.MAX_VALUE > visited[current]!! + 1) {
        visited[w2] = visited[current]!! + 1
        traverse(w2)
    }
    if (board[s] == '-' && visited[s2] ?: Int.MAX_VALUE > visited[current]!! + 1) {
        visited[s2] = visited[current]!! + 1
        traverse(s2)
    }
}


fun traverse2(start : Pair<Int,Int>) {

    val toBeVisited = ArrayDeque<Pair<Int,Int>>()
    toBeVisited.push(start)

    while (!toBeVisited.isEmpty()) {
        val current = toBeVisited.pop()
        val n = Pair(current.first, current.second - 1)
        val n2 = Pair(current.first, current.second - 2)
        val w = Pair(current.first - 1, current.second)
        val w2 = Pair(current.first - 2, current.second)
        val e = Pair(current.first + 1, current.second)
        val e2 = Pair(current.first + 2, current.second)
        val s = Pair(current.first, current.second + 1)
        val s2 = Pair(current.first, current.second + 2)

        if (board[n] == '-' && visited[n2] ?: Int.MAX_VALUE > visited[current]!! + 1) {
            visited[n2] = visited[current]!! + 1
            toBeVisited.push(n2)
        }
        if (board[w] == '|' && visited[w2] ?: Int.MAX_VALUE > visited[current]!! + 1) {
            visited[w2] = visited[current]!! + 1
            toBeVisited.push(w2)
        }
        if (board[e] == '|' && visited[e2] ?: Int.MAX_VALUE > visited[current]!! + 1) {
            visited[e2] = visited[current]!! + 1
            toBeVisited.push(e2)
        }
        if (board[s] == '-' && visited[s2] ?: Int.MAX_VALUE > visited[current]!! + 1) {
            visited[s2] = visited[current]!! + 1
            toBeVisited.push(s2)
        }
    }
}


fun removeQuestionMarks() {
    board.filter { it.value == '?' }.toMap().forEach {key, value ->
        board[key] = '#'
    }
}

fun partOne(input: String) : Int {
    regex = input
    (-1 .. 1).forEach {y ->
        (-1 .. 1).forEach {x ->
            val cell = Pair(x, y)
            board[cell] = getCellType(cell)
        }
    }
    val start = Pair(0, 0)
    createPaths(1, regex.length - 1, start)
    removeQuestionMarks()
    printGame(input,start)

    visited[start] = 0
    traverse2(start)

    return visited.values.max()!!
}

fun partTwo(input: String) : Int {
    regex = input
    (-1 .. 1).forEach {y ->
        (-1 .. 1).forEach {x ->
            val cell = Pair(x, y)
            board[cell] = getCellType(cell)
        }
    }
    val start = Pair(0, 0)
    createPaths(1, regex.length - 1, start)
    removeQuestionMarks()
    printGame(input,start)

    visited[start] = 0
    traverse2(start)

    return visited.values.count{ it >= 1000 }
}




fun createPaths2(input : String, start : Pair<Int,Int>) : Int {
    var index = 1
    var current = start
    var door : Pair<Int,Int>? = null
    while (index < input.length) {
        //printGame(input.substring(index), current)
        when (input[index]) {
            'N' -> {
                door = Pair(current.first, current.second - 1)
                current = Pair(current.first, current.second - 2)
                updateCell(current, door)
            }
            'E' -> {
                door = Pair(current.first + 1, current.second)
                current = Pair(current.first + 2, current.second)
                updateCell(current, door)
            }
            'W' -> {
                door = Pair(current.first - 1, current.second)
                current = Pair(current.first - 2, current.second)
                updateCell(current, door)
            }
            'S' -> {
                door = Pair(current.first, current.second + 1)
                current = Pair(current.first, current.second + 2)
                updateCell(current, door)
            }
            '(' -> {
                val rightIndex = index + createPaths2(input.substring(index), current)
                //index = rightIndex
            }
            ')' -> {
            }
            '|' -> {
                current = start
            }
            '$' ->
                return index
        }
        index++
    }
    return index
}

//fun createPaths(input : String, start : Pair<Int,Int>) {
//    var door : Pair<Int,Int>? = null
//    val states = mutableListOf<Pair<String, Pair<Int,Int>>>()
//    states.add(Pair(input.drop(1), start))
//
//    while (!states.isEmpty()) {
//        val state = states.removeAt(0)
//        var current = state.second
//        //printGame(state.first, state.second)
//        when (state.first[0]) {
//            'N' -> {
//                door = Pair(current.first, current.second - 1)
//                current = Pair(current.first, current.second - 2)
//                updateCell(current, door)
//                states.add(Pair(state.first.drop(1), current))
//            }
//            'E' -> {
//                door = Pair(current.first + 1, current.second)
//                current = Pair(current.first + 2, current.second)
//                updateCell(current, door)
//                states.add(Pair(state.first.drop(1), current))
//            }
//            'W' -> {
//                door = Pair(current.first - 1, current.second)
//                current = Pair(current.first - 2, current.second)
//                updateCell(current, door)
//                states.add(Pair(state.first.drop(1), current))
//            }
//            'S' -> {
//                door = Pair(current.first, current.second + 1)
//                current = Pair(current.first, current.second + 2)
//                updateCell(current, door)
//                states.add(Pair(state.first.drop(1), current))
//            }
//            '(' -> {
//                var startIndex = 0
//                var pIndex = startIndex + 1
//                var match = 1
//                val pipes = mutableListOf(startIndex)
//                while (match > 0) {
//                    if (state.first[pIndex] == '(') {
//                        match++
//                    } else if (state.first[pIndex] == ')') {
//                        match--
//                    } else if (match == 1 && state.first[pIndex] == '|') {
//                        pipes.add(pIndex)
//                    }
//                    pIndex++
//                }
//                pipes.add(pIndex - 1)
//                for (i in pipes.indices.drop(1)) {
//                    states.add(Pair(state.first.substring(pipes[i-1] + 1, pipes[i]) + state.first.substring(pIndex), state.second))
//                }
//            }
//            ')' -> {
//                println("Error1")
//            }
//            '|' -> {
//                println("Error2")
//            }
//            '$' -> {
//                //println("Bye")
//            }
//        }
//    }
//}
