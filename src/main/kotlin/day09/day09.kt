package day09

class Node(val value : Int, var prev : Node?, var next : Node?) {
    fun insert(v : Int) : Node {
        val toInsert = Node(v, prev, this)
        prev!!.next = toInsert
        prev = toInsert
        return toInsert
    }

    fun remove() : Node {
        prev!!.next = next
        next!!.prev = prev
        return next!!
    }

    companion object {
        fun createCircle() : Node {
            var circle = Node(0,null,null)
            circle.next = circle
            circle.prev = circle
            return circle
        }
    }
}

fun bestScore(input: Pair<Int,Int>) : Long {
    var players = MutableList(input.first) {0L}
    var round = 1
    var circle = Node.createCircle()

    while (round <= input.second) {
        if (round % 23 == 0) {
            players[round % players.size] = players[round % players.size] + round
            for (i in 1..7) {
                circle = circle.prev as Node
            }
            players[round % players.size] = players[round % players.size] + circle.value
            circle = circle.remove()
        } else {
            for (i in 1..2) {
                circle = circle.next as Node
            }
            circle = circle.insert(round)
        }
        round++
    }
    return players.max() ?: -1
}

fun partOne(input: Pair<Int,Int>) : Long {
    return bestScore(input)
}



fun partTwo(input: Pair<Int,Int>) : Long {
    return bestScore(Pair(input.first, input.second * 100))
}
