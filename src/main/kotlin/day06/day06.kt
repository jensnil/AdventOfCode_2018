package day06

fun partOne(input: List<Pair<Int,Int>>) : Int {
    val width = input.map { it.first }.max()!!
    val height = input.map { it.second}.max()!!
    //val array = Array(width+1, {IntArray(height+1) {-1} })
    var array = mutableMapOf<Pair<Int,Int>, Int>()
    var bordering = mutableSetOf<Int>()

    for (x in 0..width) {
        for (y in 0..height) {
            val distances = input.map {
                Math.abs(x - it.first) + Math.abs(y - it.second)
            }
            if (distances.indexOf(distances.min()) == distances.lastIndexOf(distances.min()))  {
                array[Pair(x, y)] = distances.indexOf(distances.min())
                if (x== 0 || x == width || y == 0|| y == height) {
                    bordering.add(array[Pair(x, y)]!!)
                }
            }
        }
    }
    return array.values.filter { !bordering.contains(it) }.groupBy { it }.map { it.value.size }.max()!!
}

fun partTwo(input: List<Pair<Int,Int>>) : Int {
    val width = input.map { it.first }.max()!!
    val height = input.map { it.second}.max()!!
    var safeRegions = mutableListOf<Int>()

    for (x in 0..width) {
        for (y in 0..height) {
            val distances = input.map {
                Math.abs(x - it.first) + Math.abs(y - it.second)
            }
            safeRegions.add(distances.sum())
        }
    }

    return safeRegions.filter { it < 10000 }.count()
}
