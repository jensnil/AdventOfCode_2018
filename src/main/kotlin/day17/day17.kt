package day17

val ground = mutableMapOf<Pair<Int,Int>, Char>()
var minX = 0
var maxX = 0
var minY = 0
var maxY = 0

fun populate(input : List<Pair<Char, Triple<Int,Int,Int>>>) {
    input.forEach {
        for (i in it.second.second..it.second.third) {
            if (it.first == 'x') {
                ground[Pair(it.second.first, i)] = '#'
            } else {
                ground[Pair(i, it.second.first)] = '#'
            }
        }
    }
    minX = ground.keys.map { it.first }.min()!!
    maxX = ground.keys.map { it.first }.max()!!
    minY = ground.keys.map { it.second }.min()!!
    maxY = ground.keys.map { it.second }.max()!!
    //printGround(maxY)
}

fun printGround(max : Int) {
    for (y in 0..max) {
        for (x in minX-10..maxX+10) {
            if (y == 0 && x == 500) {
                print('+')
            } else {
                print(ground[Pair(x, y)] ?: '.')
            }
        }
        println()
    }
    println()
}

fun identifyAndFillWall(current : Pair<Int,Int>) {
    var nextLeft = current
    while (ground[nextLeft] == '|') {
        nextLeft = Pair(nextLeft.first - 1, nextLeft.second)
    }
    var nextRight = current
    while (ground[nextRight] == '|') {
        nextRight = Pair(nextRight.first + 1, nextRight.second)
    }
    if (ground[nextLeft] == '#' &&  ground[nextRight] == '#') {
        for (i in nextLeft.first+1..nextRight.first-1) {
            if (ground[Pair(i, nextLeft.second)] != null && ground[Pair(i, nextLeft.second)] != '|') {
                print("!!!")
            }
            ground[Pair(i, nextLeft.second)] = '~'
        }
    }
}

fun pour(current : Pair<Int,Int>) : Boolean {
    if (current.second <= maxY) {
        val below = Pair(current.first, current.second + 1)
        val left = Pair(current.first - 1, current.second)
        val right = Pair(current.first + 1, current.second)

        if (ground[current] == '#') {
            return true
        } else if ((ground[current] == '~')) {
            return true
        } else if (ground[current] == null) {
            ground[current] = '|'
            if (pour(below)) {
                var leftRes = if (ground[left] == null) {
                    pour(left)
                } else true
                var rightRes = if (ground[right] == null) {
                    pour(right)
                } else true
                if (leftRes && rightRes) {
                    identifyAndFillWall(current)
                }
                return leftRes && rightRes
            }
        }
        return false
    }
    return false
}


fun partOne(input: List<Pair<Char, Triple<Int,Int,Int>>>) : Int {
    populate(input)
    pour(Pair(500, 0))
    return ground.count { (it.value == '|' || it.value == '~') && it.key.second >= minY}
}


fun partTwo(input: List<Pair<Char, Triple<Int,Int,Int>>>) : Int {
    populate(input)
    pour(Pair(500, 0))
    return ground.count { it.value == '~' && it.key.second >= minY}
}