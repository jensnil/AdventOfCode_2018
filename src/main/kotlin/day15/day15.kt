package day15

class Player(val isElf : Boolean) {
    var hitPoints = 200
}

var attackPoints = 3
var rounds = 0
var board = mutableListOf(mutableListOf<Char>())
var all = mutableMapOf<Pair<Int,Int>, Player>()

fun populate(input : List<String>) {
    var elves = mutableMapOf<Pair<Int,Int>, Player>()
    var goblins = mutableMapOf<Pair<Int,Int>, Player>()
    rounds = 0
    board = MutableList(input.size) { MutableList(input[0].length) {' '}}
    input.forEachIndexed { y, line ->
        for (x in line.indices) {
            if (line[x] == 'E') {
                elves[Pair(x, y)] =  Player(true)
                board[y][x] = '.'
            } else if (line[x] == 'G') {
                goblins[Pair(x, y)] =  Player(false)
                board[y][x] = '.'
            } else {
                board[y][x] = line[x]
            }
        }
    }
    all = mutableMapOf<Pair<Int,Int>, Player>()
    all.putAll(elves)
    all.putAll(goblins)
}

fun printGame() {
    println("After round ${rounds}")
    board.forEachIndexed { y, line ->
        line.forEachIndexed { x, cell ->
            all.filter { it.key.first == x && it.key.second == y }.values.firstOrNull()?.let {
                if (it.isElf) {
                    print("E")
                } else {
                    print("G")
                }
            } ?: print(cell)
        }
        print("   ")
        all.filter { it.key.second == y }.forEach {
            print(" ${if (it.value.isElf) "E" else "G"}(${it.value.hitPoints}), ")
        }
        println()
    }
    println()
}

fun getType(cell : Pair<Int,Int>) : Char {
    return board[cell.second][cell.first]
}

fun getSurroundingEmpty(cell : Pair<Int,Int>) : Set<Pair<Int,Int>> {
    var toReturn = mutableSetOf<Pair<Int,Int>>()
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

fun inRange(isEnemyElf : Boolean) : Set<Pair<Int,Int>> {
    val enemies = all.filter { it.value.isElf == isEnemyElf }.map { enemy -> getSurroundingEmpty(enemy.key).distinct()}.flatten().toSet()
    return enemies
}

fun getNearestAndChosen(player : Pair<Int,Int>, reachableEnemies : Set<Pair<Int,Int>>) : Pair<Int,Int>? {
    val toBeVisited = getSurroundingEmpty(player).toMutableList()
    val shortestPaths = toBeVisited.map { it to Pair(it, 1) }.toMap().toMutableMap()
    while (!toBeVisited.isEmpty()) {
        val current = toBeVisited.removeAt(0)
        val surroundingEmpty = getSurroundingEmpty(current)
        surroundingEmpty.forEach {newPosition ->
            if (!shortestPaths.containsKey(newPosition) || shortestPaths[current]!!.second + 1 < shortestPaths[newPosition]!!.second) {
                shortestPaths[newPosition] = Pair(shortestPaths[current]!!.first, shortestPaths[current]!!.second + 1)
                toBeVisited.add(newPosition)
            }
        }
    }
    return shortestPaths.filterKeys { reachableEnemies.contains(it)}.map { it.value to it.key }.sortedWith( compareBy ({ it.first.second }, { it.second.second }, { it.second.first } )).map { it.first.first }.firstOrNull()
}

fun canHit(cell : Pair<Int,Int>, player : Player) : Boolean {
    var newCell = Pair(cell.first, cell.second - 1)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        return true
    }
    newCell = Pair(cell.first - 1, cell.second)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        return true
    }
    newCell = Pair(cell.first + 1, cell.second)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        return true
    }
    newCell = Pair(cell.first, cell.second + 1)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        return true
    }
    return false
}

fun hit(cell : Pair<Int,Int>, player : Player) {
    val inRange = mutableListOf<Pair<Int,Int>>()
    var newCell = Pair(cell.first, cell.second - 1)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        inRange.add(newCell)
    }
    newCell = Pair(cell.first - 1, cell.second)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        inRange.add(newCell)
    }
    newCell = Pair(cell.first + 1, cell.second)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        inRange.add(newCell)
    }
    newCell = Pair(cell.first, cell.second + 1)
    if (all[newCell] != null && all[newCell]!!.isElf != player.isElf) {
        inRange.add(newCell)
    }

    inRange.minBy { all[it]!!.hitPoints }?.let {
        all[it]!!.hitPoints -= if (player.isElf) attackPoints else 3
    }
}

fun removeCorpses() : Boolean {
    all = all.filter { it.value.hitPoints > 0 }.toMutableMap()
    return true
}

fun runGame() : Int {
    while (true) {
        all = all.toSortedMap( compareBy ({it.second}, {it.first} ))
        //printGame()
        all.toMap().forEach {
            if (it.value.hitPoints > 0) {
                if (all.filter { it.value.isElf }.isEmpty() || all.filter { !it.value.isElf }.isEmpty()) {
                    //printGame()
                    //println("$rounds * ${all.map { it.value.hitPoints }.sum()}")
                    return rounds * all.map { it.value.hitPoints }.sum()
                }
                if (!canHit(it.key, it.value)) {
                    val enemiesInRange = inRange(!it.value.isElf)
                    val nextPosition = getNearestAndChosen(it.key, enemiesInRange)

                    nextPosition?.let {next ->
                        all.remove(it.key)
                        all[next] = it.value

                        hit(next, it.value)
                    }
                } else {
                    hit(it.key, it.value)
                }
                removeCorpses()
            } else {
                //println()
            }
        }
        rounds++
    }
    return 0
}

fun partOne(input: List<String>) : Int {
    populate(input)
    return runGame()
}


fun partTwo(input: List<String>) : Int {
    attackPoints = 0
    while (true) {
        populate(input)
        var elfCount = all.count { it.value.isElf }
        val result = runGame()
        if (elfCount == all.count { it.value.isElf }) {
            return result
        }
        attackPoints++
    }
}
