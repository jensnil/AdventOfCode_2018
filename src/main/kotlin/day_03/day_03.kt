package day_03

data class Claim (
        val id : Int,
        val left : Int,
        val top : Int,
        val width : Int,
        val height : Int
)


fun partOne(input : List<Claim>) : Int {
    val squares = mutableMapOf<Pair<Int,Int>, Int>()
    input.forEach {
        for (i in it.left until it.left + it.width) {
            for (j in it.top until it.top + it.height) {
                var position = Pair(i, j)
                if (squares.containsKey(position)) {
                  squares.put(position, squares[position]!! + 1)
                } else {
                    squares.put(position, 1)
                }
            }
        }
    }
    return squares.values.count { it > 1 }
}

fun partTwo(input : List<Claim>) : Int {
    val squares = mutableMapOf<Pair<Int,Int>, MutableList<Int>>()
    input.forEach {
        for (i in it.left until it.left + it.width) {
            for (j in it.top until it.top + it.height) {
                var position = Pair(i, j)
                if (squares.containsKey(position)) {
                    squares[position]!!.add(it.id)
                } else {
                    squares.put(position, mutableListOf(it.id))
                }
            }
        }
    }
    val stillNotOverlapping = input.map { it.id }.toMutableSet()
    squares.forEach {
        if (it.value.size > 1) {
            it.value.forEach {
                stillNotOverlapping.remove(it)
            }
        }
    }
    return stillNotOverlapping.first()
}
