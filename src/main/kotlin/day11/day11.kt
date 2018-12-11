package day11

fun getSingleFuelLevel(x : Int, y : Int, serialNumber : Int) : Int
{
    val rackId = x + 10
    return (((((rackId * y) + serialNumber) * rackId) / 100) % 10) - 5
}

fun getFuelLevel3(x : Int, y : Int, serialNumber : Int, size : Int, onlyAll : Boolean) : Pair<Int,Int> {
    var maxSum = Int.MIN_VALUE
    var maxIndex = -1
    var sum = 0
    (1 .. size).map { i ->
        sum += (0 until i).map {
            getSingleFuelLevel(x + it, y + i - 1, serialNumber)
        }.sum()
        sum += (0 until i - 1).map {
            getSingleFuelLevel(x + i - 1, y + it, serialNumber)
        }.sum()
        if (sum > maxSum) {
            maxSum = sum
            maxIndex = i
        }
    }
    if (onlyAll) {
        return Pair(sum, size)
    } else {
        return Pair(maxSum, maxIndex - 1)
    }
}

fun partOne(input: Pair<Int,Int>) : Pair<Int,Int> {
    var maxFuel : Int = Int.MIN_VALUE
    var maxFueledCell = Pair(-1,-1)

    (1 .. input.first - 2).forEach { i ->
        (1 .. input.first - 2).forEach { j ->
            val newFuleLevel = getFuelLevel3(i, j, input.second, 3, true).first
            if (newFuleLevel > maxFuel) {
                maxFuel = newFuleLevel
                maxFueledCell = Pair(i, j)
            }
        }
    }
    return maxFueledCell
}

fun partTwo(input: Pair<Int,Int>) : Triple<Int,Int,Int> {
    var max = input.first
    var maxFuel : Int = Int.MIN_VALUE
    var maxFueledCell = Triple(-1,-1, -1)
    var maxSize = -1
    (1 until input.first).forEach { i ->
        (1 until input.first).forEach { j ->
            val stop = Math.min(max - i + 1, max - j + 1)
            val newFuelLevel = getFuelLevel3(i, j, input.second, Math.min(max, stop), false)
            if (newFuelLevel.first > maxFuel) {
                maxFuel = newFuelLevel.first
                maxFueledCell = Triple(i, j, maxSize)
                maxSize = newFuelLevel.second
            }
        }
    }
    return maxFueledCell
}
