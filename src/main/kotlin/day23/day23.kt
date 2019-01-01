package day23

fun getDistance(a : Triple<Long,Long,Long>, b : Triple<Long,Long,Long>) : Long {
    return Math.abs(a.first - b.first) + Math.abs(a.second - b.second) + Math.abs(a.third - b.third)
}

fun isInBotRangeCount(current : Triple<Long,Long,Long>, input : List<Pair<Triple<Long,Long,Long>, Long>>) : Long {
    return input.fold(0L) { acc, b ->
        acc + if (getDistance(current, b.first) <= b.second) {
            1
        } else {
            0
        }
    }
}

fun partOne(input: List<Pair<Triple<Long,Long,Long>, Long>>) : Long {
    val nanobotWithLargestRadius = input.maxBy { it.second }!!
    return input.count { Math.abs(it.first.first - nanobotWithLargestRadius.first.first) + Math.abs(it.first.second - nanobotWithLargestRadius.first.second) + Math.abs(it.first.third - nanobotWithLargestRadius.first.third) <= nanobotWithLargestRadius.second } as Long
}

fun partTwo(input: List<Pair<Triple<Long,Long,Long>, Long>>) : Long {
    var origo = Triple(0L, 0L, 0L)
    val gridSize = 1

    var maxBotPoints = input.map { bot ->
        var size = bot.second / gridSize
        var maxBotPoint = bot.first
        var maxBotPointCount = Long.MIN_VALUE
        while (size > 0) {
            val sortedPoints = (-gridSize..gridSize).mapNotNull { x ->
                (-gridSize..gridSize).mapNotNull {y ->
                    (-gridSize..gridSize).mapNotNull {z ->
                        val point = Triple(maxBotPoint.first + x * size, maxBotPoint.second + y * size,maxBotPoint.third + z * size)
                        if (getDistance(bot.first, point) < bot.second) {
                            point to isInBotRangeCount(point, input)
                        } else {
                            null
                        }
                    }
                }
            }.flatten().flatten().sortedWith(compareBy ( {it.second},  { -getDistance(it.first, origo)} ))
            maxBotPoint = sortedPoints!!.last().first
            maxBotPointCount = sortedPoints!!.last().second
            size /= 2
        }
        maxBotPoint to maxBotPointCount
    }
    val sortedPoints = maxBotPoints.sortedWith(compareBy ( {it.second},  { -getDistance(it.first, origo)} ))
    val maxBotPointTotal = sortedPoints.last().first
    println(maxBotPointTotal)
    return Math.abs(maxBotPointTotal.first) + Math.abs(maxBotPointTotal.second) + Math.abs(maxBotPointTotal.third)
}

