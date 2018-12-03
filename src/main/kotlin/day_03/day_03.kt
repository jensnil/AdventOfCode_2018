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
        (it.left until it.left + it.width).forEach {row ->
            (it.top until it.top + it.height).forEach { col ->
                val position = Pair(row, col)
                squares[position] = (squares[position]?:0) + 1
            }
        }
    }
    return squares.values.count { it > 1 }
}

fun partTwo(input : List<Claim>) : Int {
    val squares = mutableMapOf<Pair<Int,Int>, MutableList<Int>>()
    input.forEach {
        (it.left until it.left + it.width).forEach {row ->
            (it.top until it.top + it.height).forEach { col ->
                val position = Pair(row, col)
                squares[position] = squares[position]?.apply { add(it.id) }
                        ?: mutableListOf(it.id)
            }
        }
    }
    val stillNotOverlapping = input.map { it.id }.toMutableSet()
    squares.filter { it.value.size > 1 }.forEach {
        it.value.forEach {id ->
            stillNotOverlapping.remove(id)
        }
    }
    return stillNotOverlapping.first()
}
