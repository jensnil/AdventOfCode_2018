package day13

var tracks : List<String>? = null

data class Cart(var x : Int, var y : Int, var direction : Char) {
    var nextTurn = 'L'

    fun move() {
        when (direction) {
            '>' -> x++
            '<' -> x--
            'v' -> y++
            '^' -> y--
        }
        when (tracks!![y][x]) {
            '/' ->
                when (direction) {
                    '>' -> direction = '^'
                    '<' -> direction = 'v'
                    'v' -> direction = '<'
                    '^' -> direction = '>'
                }
            '\\' ->
                when (direction) {
                    '>' -> direction = 'v'
                    '<' -> direction = '^'
                    'v' -> direction = '>'
                    '^' -> direction = '<'
                }
            '+' -> {
                when (direction) {
                    '>' ->
                        when (nextTurn) {
                            'L' -> direction = '^'
                            'R' -> direction = 'v'
                        }
                    '<' ->
                        when (nextTurn) {
                            'L' -> direction = 'v'
                            'R' -> direction = '^'
                        }
                    'v' ->
                        when (nextTurn) {
                            'L' -> direction = '>'
                            'R' -> direction = '<'
                        }
                    '^' ->
                        when (nextTurn) {
                            'L' -> direction = '<'
                            'R' -> direction = '>'
                        }
                }
                when (nextTurn) {
                    'L' -> nextTurn = 'S'
                    'S' -> nextTurn = 'R'
                    'R' -> nextTurn = 'L'
                }
            }
            '-'-> {}
            '|'-> {}
            else -> {
                print("")
            }
        }
    }
}

fun populate(input: List<String>) : MutableList<Cart> {
    var carts = mutableListOf<Cart>()

    input.forEachIndexed { y, row ->
        row.forEachIndexed { x, cell ->
            if (listOf('<', '>', 'v', '^').contains(cell)) {
                carts.add(Cart(x, y, cell))
            }
        }
    }
    tracks = input.map { row -> row
            .replace('<', '-')
            .replace('>', '-')
            .replace('v', '|')
            .replace('^', '|')
    }
    return carts
}

fun printTrack(carts : MutableList<Cart>) {
    tracks?.forEachIndexed { lineIndex, row ->
        row.forEachIndexed { rowIndex, cell ->
            carts.filter { it.x == rowIndex && it.y == lineIndex }.firstOrNull()?.let { print(it.direction) } ?: run {print(cell)}
        }
        println()
    }
}

fun printTrackFpcused(carts : MutableList<Cart>) {
    tracks?.forEachIndexed { lineIndex, row ->
        if (carts.first().y - 5 < lineIndex && carts.first().y + 5 > lineIndex) {
            row.forEachIndexed { rowIndex, cell ->
                if (carts.first().x - 5 < rowIndex && carts.first().x + 5 > rowIndex) {
                    carts.filter { it.x == rowIndex && it.y == lineIndex }.firstOrNull()?.let {
                        print(it.direction)
                    } ?: run {
                        print(cell)
                    }
                }
            }
            println()
        }
    }
    println(carts.first().nextTurn)
}

fun partOne(input: List<String>) : Pair<Int,Int> {
    var carts = populate(input)

    printTrack(carts)

    while (true) {
        carts = carts.sortedWith(compareBy ({it.x}, {it.y})).toMutableList()
        for (cart in carts) {
            cart.move()
            val temp = carts.groupBy {it3 -> it3.x.toString() + "," + it3.y.toString() }.values.firstOrNull { it2 -> it2.size > 1}
            if (temp != null) {
                return Pair(temp.first().x, temp.first().y)
            }

        }
    }
    return Pair(0, 0)
}

fun partTwo(input: List<String>) : Pair<Int,Int> {
    var carts = populate(input)
    while (true) {
        carts = carts.sortedWith(compareBy ({it.x}, {it.y})).toMutableList()
        for (cart in carts) {
            cart.move()
            carts= carts.groupBy {it3 -> it3.x.toString() + "," + it3.y.toString() }.values.filter{it4 -> it4.size == 1}.flatten().toMutableList()
        }
        if (carts.size == 1) {
            return Pair(carts.first().x, carts.first().y)
        }
    }
    return Pair(0, 0)
}
