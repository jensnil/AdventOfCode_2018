import java.util.*



class Day17(rawInput: List<String>) {

    var minX = 0
    var maxX = 0
    var minY = 0
    var maxY = 0

    val chart = mutableSetOf<Pair<Int, Int>>()
    val water = mutableSetOf<Pair<Int, Int>>()
    val allFlows = mutableSetOf<Pair<Int, Int>>()

    init {
        val parse = """([xy])=(\d+), ([xy])=(\d+)\.\.(\d+)""".toRegex()
        for (line in rawInput) {
            val (d1, n1, d2, a, b) = parse.matchEntire(line)?.destructured ?:
            throw Exception("found non-matching line: $line")
            check(d1 != d2)
            (a.toInt() .. b.toInt()).forEach {
                if (d1 == "x") {
                    chart.add(n1.toInt() to it)
                } else {
                    chart.add(it to n1.toInt())
                }
            }
        }

        minX = chart.minBy { it.first }!!.first
        maxX = chart.maxBy { it.first }!!.first
        minY = chart.minBy { it.second }!!.second
        maxY = chart.maxBy { it.second }!!.second
    }

    private fun addSpill(flow: ArrayDeque<Pair<Int, Int>>, x: Int, y: Int) {
        var y1 = y
        while (!chart.contains(x to y1) && !allFlows.contains(x to y1) && (y1 <= maxY)) {
            flow.push(x to y1)
            allFlows.add(x to y1)
            y1 += 1
        }
    }

    fun part1(): Any? {
        val flowingWater = ArrayDeque<Pair<Int, Int>>()
        addSpill(flowingWater, 500, minY)

        while (flowingWater.isNotEmpty()) {
            val (x1, y1) = flowingWater.pop()

            // find left wall
            var leftWall: Int? = null
            var leftDrop: Int? = null
            for (x in x1 downTo minX - 1)  {
                if (chart.contains(x to y1)) {
                    leftWall = x + 1
                    break
                }
                if (!chart.contains(x to y1 + 1)) {
                    leftDrop = x
                    break
                }
            }

            // find right wall
            var rightWall: Int? = null
            var rightDrop: Int? = null
            for (x in x1 .. maxX + 1)  {
                if (chart.contains(x to y1)) {
                    rightWall = x - 1
                    break
                }
                if (!chart.contains(x to y1 + 1)) {
                    rightDrop = x
                    break
                }
            }

            // fill one level
            if (leftWall != null && rightWall != null) {
                (leftWall .. rightWall).forEach { x ->
                    chart.add(x to y1)
                    water.add(x to y1)
                }
            } else {
                if (leftDrop != null && !flowingWater.contains(leftDrop to y1)) {
                    addSpill(flowingWater, leftDrop, y1)
                }
                if (rightDrop != null && !flowingWater.contains(rightDrop to y1)) {
                    addSpill(flowingWater, rightDrop, y1)
                }
                ((leftWall ?: leftDrop!!) .. (rightWall ?: rightDrop!!)).forEach {
                    allFlows.add(it to y1)
                }
            }
        }

        allFlows.addAll(water)
        return allFlows.size
    }

}