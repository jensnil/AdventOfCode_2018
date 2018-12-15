package day15

class Player(val isElf : Boolean) {
    var hitPoints = 200
}

var board = mutableListOf(mutableListOf<Char>())
var elves = mutableMapOf<Pair<Int,Int>, Player>()
var goblins = mutableMapOf<Pair<Int,Int>, Player>()
var all = mutableMapOf<Pair<Int,Int>, Player>()

fun populate(input : List<String>) {
    board = MutableList(input.size) { MutableList(input[0].length) {' '}}
    input.forEachIndexed { y, line ->
        for (x in line.indices) {
            if (line[x] == 'E') {
                elves[Pair(x, y)] =  Player(true)
                board[y][x] = '.'
            } else if (line[x] == 'G') {
                goblins[Pair(x, y)] =  Player(false)
            } else {
                board[y][x] = line[x]
            }
        }
    }
    all.putAll(elves)
    all.putAll(goblins)
}

fun printGame() {
    board.forEachIndexed { y, line ->
        line.forEachIndexed { x, cell ->
            elves.filter { it.key.first == x && it.key.second == y}.values.firstOrNull()?.let {
                print("E")
            } ?: goblins.filter { it.key.first == x && it.key.second == y}.values.firstOrNull()?.let {
                print("G")
            } ?: print(cell)
        }
        println()
    }
    println()
}

fun getType(cell : Pair<Int,Int>) : Char {
    return board[cell.second][cell.first]
}

fun getSurroundingEmpty(cell : Pair<Int,Int>) : List<Pair<Int,Int>> {
    var toReturn = mutableListOf<Pair<Int,Int>>()
    var newCell = Pair(cell.first, cell.second - 1)
    if (getType(newCell) == '.' && !all.contains(newCell)) {
        toReturn.add(newCell)
    }
    newCell = Pair(cell.first - 1, cell.second)
    if (getType(newCell) == '.' && !all.contains(newCell)) {
        toReturn.add(newCell)
    }
    newCell = Pair(cell.first + 1, cell.second)
    if (getType(newCell) == '.' && !all.contains(newCell)) {
        toReturn.add(newCell)
    }
    newCell = Pair(cell.first, cell.second + 1)
    if (getType(newCell) == '.' && !all.contains(newCell)) {
        toReturn.add(newCell)
    }
    return toReturn
}


fun inRange(isEnemyElf : Boolean) : List<Pair<Int,Int>> {
    val enemies = all.filter { it.value.isElf == isEnemyElf }.map { enemy -> getSurroundingEmpty(enemy.key).distinct()}.flatten()
    return enemies
}

fun getShortestPath(player : Pair<Int,Int>, inRangeOfEnemies : List<Pair<Int,Int>>) : MutableList<Pair<Int,Int>>? {
    val paths = mutableMapOf<Pair<Int,Int>, MutableList<Pair<Int,Int>>>()
    val toBeVisited = mutableSetOf<Pair<Int,Int>>()
    toBeVisited.add(player)
    while (!toBeVisited.isEmpty()) {
        val key = toBeVisited.first()
        val value = paths[key] ?: mutableListOf()
        toBeVisited.remove(key)
        val surroundingEmpty = getSurroundingEmpty(key)
        surroundingEmpty.forEach {
            val newList = value.toMutableList()
            newList.add(it)
            if (paths.containsKey(it)) {
                if (newList.size < paths[it]!!.size) {
                    paths[it] = newList
                    toBeVisited.add(it)
                }
            } else {
                paths[it] = newList
                toBeVisited.add(it)
            }
        }
    }
    return paths.filter { inRangeOfEnemies.contains(it.key) }.values.minBy { it.size }
}

fun hit(cell : Pair<Int,Int>, player : Player) {
    var newCell = Pair(cell.first, cell.second - 1)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        all[newCell]!!.hitPoints -=  3
    }
    newCell = Pair(cell.first - 1, cell.second)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        all[newCell]!!.hitPoints -=  3
    }
    newCell = Pair(cell.first + 1, cell.second)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        all[newCell]!!.hitPoints -=  3
    }
    newCell = Pair(cell.first, cell.second + 1)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        all[newCell]!!.hitPoints -=  3
    }
}

fun partOne(input: List<String>) : Int {
    populate(input)
    printGame()
    while (true) {
        all = all.toSortedMap( compareBy ({it.second}, {it.first} ))
        all.toMap().forEach {
            val reachableEnemies = inRange(!it.value.isElf)
            val shortestPath = getShortestPath(it.key, reachableEnemies)

            val nextPosition = shortestPath?.first()

            nextPosition?.let {next ->
                all.remove(it.key)
                all[next] = it.value
                hit(next, it.value)
            }
        }
    }

    return 0
}


//fun partTwo(input: String) : Int {
//}

//
//fun getSurroundingEnemies(cell : Pair<Int,Int>, isEnemyElf: Boolean) : Set<Pair<Int,Int>> {
//    var toReturn = mutableSetOf<Pair<Int,Int>>()
//    var newCell = Pair(cell.first, cell.second - 1)
//    if ((elves.contains(newCell) && isEnemyElf) || (goblins.contains(newCell) && !isEnemyElf)) {
//        toReturn.add(newCell)
//    }
//    newCell = Pair(cell.first - 1, cell.second)
//    if ((elves.contains(newCell) && isEnemyElf) || (goblins.contains(newCell) && !isEnemyElf)) {
//        toReturn.add(newCell)
//    }
//    newCell = Pair(cell.first + 1, cell.second)
//    if ((elves.contains(newCell) && isEnemyElf) || (goblins.contains(newCell) && !isEnemyElf)) {
//        toReturn.add(newCell)
//    }
//    newCell = Pair(cell.first, cell.second + 1)
//    if ((elves.contains(newCell) && isEnemyElf) || (goblins.contains(newCell) && !isEnemyElf)) {
//        toReturn.add(newCell)
//    }
//    return toReturn
//}
