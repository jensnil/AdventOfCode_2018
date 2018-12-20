package day18

fun printGround(area : List<List<Char>>) {
    area.forEach { line ->
        line.forEach{
            print(it)
        }
        println()
    }
}

fun transform(input: List<List<Char>>, minutes : Int) : Int {
    var current = listOf(input.map { it.toMutableList()}, input.map { it.toMutableList()})

    (1..minutes).forEach {minute ->
        (0 until input.size).forEach {line ->
            (0 until input[0].size).forEach {column ->
                var counter = mutableMapOf<Char,Int>()
                (Math.max(0, line - 1)..Math.min(input.size - 1, line + 1)).forEach {y ->
                    (Math.max(0, column - 1)..Math.min(input[0].size - 1, column + 1)).forEach {x ->
                        //print("($x, $y) ")
                        if (line != y || column != x) {
                            counter[current[0][y][x]] = counter[current[0][y][x]]?.let { it + 1 } ?: 1
                        }
                    }
                }
                when (current[0][line][column]) {
                    '.' -> if (counter['|']?:0 >= 3) {
                        current[1][line][column] = '|'
                    }
                    '|' -> if (counter['#']?:0 >= 3) {
                        current[1][line][column] = '#'
                    }
                    '#' -> if (!(counter['#']?:0 >= 1 && counter['|']?:0 >= 1)) {
                        current[1][line][column] = '.'
                    }
                }
            }
        }
        current[1].indices.forEach { line ->
            current[1][0].indices.forEach { column ->
                current[0][line][column] = current[1][line][column]
            }
        }
//        print("\nAfter $minute minutes\n")
//        printGround(current[(minute) % 2])
//        print("\n")
//        printGround(current[(minute + 1) % 2])
    }
    return current[0].flatten().count { it == '|' } * current[0].flatten().count { it == '#' }
}


fun transform2(input: List<List<Char>>, minutes : Int) : Int {
    var current = mutableListOf(input.map { it.toMutableList()})

    (1..minutes).forEach {minute ->
        var next = current.last().map { it.toMutableList()}
        (0 until input.size).forEach {line ->
            (0 until input[0].size).forEach {column ->
                var counter = mutableMapOf<Char,Int>()
                (Math.max(0, line - 1)..Math.min(input.size - 1, line + 1)).forEach {y ->
                    (Math.max(0, column - 1)..Math.min(input[0].size - 1, column + 1)).forEach {x ->
                        //print("($x, $y) ")
                        if (line != y || column != x) {
                            counter[current.last()[y][x]] = counter[current.last()[y][x]]?.let { it + 1 } ?: 1
                        }
                    }
                }
                when (current.last()[line][column]) {
                    '.' -> if (counter['|']?:0 >= 3) {
                        next[line][column] = '|'
                    }
                    '|' -> if (counter['#']?:0 >= 3) {
                        next[line][column] = '#'
                    }
                    '#' -> if (!(counter['#']?:0 >= 1 && counter['|']?:0 >= 1)) {
                        next[line][column] = '.'
                    }
                }
            }
        }
        //print("\nAfter $minute minutes\n")
        //printGround(next)

        val foundIndex = current.indexOf(next)
        if (foundIndex != -1) {
            //println("$foundIndex $minute")
            val index = (minutes - foundIndex) % (minute - foundIndex)
            return current[foundIndex + index].flatten().count { it == '|' } * current[foundIndex + index].flatten().count { it == '#' }
        }
        current.add(next)
    }
    return 0
}


fun partOne(input: List<List<Char>>, minutes : Int) : Int {
    printGround(input)

    return transform(input, minutes)
}


fun partTwo(input: List<List<Char>>, minutes : Int) : Int {
    printGround(input)

    return transform2(input, minutes)
}