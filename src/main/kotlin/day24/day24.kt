package day24


fun partOne(input: List<Pair<Triple<Int,Int,Int>, Int>>) : Int {
    val nanobotWithLargestRadius = input.maxBy { it.second }!!
    return input.count { Math.abs(it.first.first - nanobotWithLargestRadius.first.first) + Math.abs(it.first.second - nanobotWithLargestRadius.first.second) + Math.abs(it.first.third - nanobotWithLargestRadius.first.third) <= nanobotWithLargestRadius.second }
}


/*fun partTwo(input: List<Pair<Triple<Int,Int,Int>, Int>>) : Int {
    val minX = input.map { it.first.first }.min()!!
    val maxX = input.map { it.first.first }.max()!!
    val minY = input.map { it.first.second }.min()!!
    val maxY = input.map { it.first.second }.max()!!
    val minZ = input.map { it.first.third }.min()!!
    val maxZ = input.map { it.first.third }.max()!!
//
//    val allPoints0 = mutableSetOf<Triple<Int,Int,Int>>()
//    for (r in 0..input[2].second) {
//        for (x in 0..r) {
//            for (y in 0..r-x) {
//                allPoints0.add(Triple(input[2].first.first + x, input[2].first.second + y, input[2].first.third + (r - x - y)))
//                allPoints0.add(Triple(input[2].first.first + x, input[2].first.second + y, input[2].first.third - (r - x - y)))
//                allPoints0.add(Triple(input[2].first.first + x, input[2].first.second - y, input[2].first.third + (r - x - y)))
//                allPoints0.add(Triple(input[2].first.first + x, input[2].first.second - y, input[2].first.third - (r - x - y)))
//                allPoints0.add(Triple(input[2].first.first - x, input[2].first.second + y, input[2].first.third + (r - x - y)))
//                allPoints0.add(Triple(input[2].first.first - x, input[2].first.second + y, input[2].first.third - (r - x - y)))
//                allPoints0.add(Triple(input[2].first.first - x, input[2].first.second - y, input[2].first.third + (r - x - y)))
//                allPoints0.add(Triple(input[2].first.first - x, input[2].first.second - y, input[2].first.third - (r - x - y)))
//            }
//        }
//    }

    val allPoints = (minX..maxX).map { x ->
        (minY..maxY).map {y ->
            (minZ..maxZ).map {z ->
                input.count { Math.abs(x - it.first.first) + Math.abs(y - it.first.second) + Math.abs(z - it.first.third) <= it.second } to Triple(x,y,z)
            }
        }
    }.flatten().flatten()
    val bestPoint = allPoints.groupBy { it.first }.maxBy { it.key }!!.value[0]
    return bestPoint.second.first + bestPoint.second.second + bestPoint.second.third
}*/

fun partTwo(input: List<Pair<Triple<Int,Int,Int>, Int>>) : Int {
    val minX = input.map { it.first.first }.min()!!
    val maxX = input.map { it.first.first }.max()!!
    val minY = input.map { it.first.second }.min()!!
    val maxY = input.map { it.first.second }.max()!!
    val minZ = input.map { it.first.third }.min()!!
    val maxZ = input.map { it.first.third }.max()!!

    val allPoints = (-10..10).map { x ->
        (-10..10).map {y ->
            (-10..10).map {z ->
                input.count { Math.abs(x - it.first.first) + Math.abs(y - it.first.second) + Math.abs(z - it.first.third) <= it.second } to Triple(x,y,z)
            }
        }
    }.flatten().flatten()
    val bestPoint = allPoints.groupBy { it.first }.maxBy { it.key }!!.value[0]
    return bestPoint.second.first + bestPoint.second.second + bestPoint.second.third
}

