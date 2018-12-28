package day25

fun getDistance(first : List<Int>, second : List<Int>) : Int {
    return first.zip(second).map { Math.abs(it.first - it.second) }.sum()
}

fun partOne(input: List<List<Int>>) : Int {
    var allGroups = mutableListOf<MutableSet<List<Int>>>()

    input.forEach { point ->
        val partitions = allGroups.partition { constellation ->
            constellation.any { constellationPoint -> getDistance(point, constellationPoint) <= 3 }
        }
        val newConstellation = partitions.first.flatten().toMutableSet()
        newConstellation.add(point)
        allGroups = mutableListOf(newConstellation)
        allGroups.addAll(partitions.second)
    }
    return allGroups.size
}

fun partTwo(input: List<List<Int>>) : Int {
    return 0
}
