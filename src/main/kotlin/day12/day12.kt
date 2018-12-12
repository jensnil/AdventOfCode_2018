package day12

fun partOne(input: Pair<String,List<Pair<String,String>>>) : Int {
    var map = input.second.map { it.first to it.second[0] }.toMap()

    var currentPots = input.first
    var zeroIndex = 0
    for (index in 1..20) {
        zeroIndex += 3
        currentPots = "..." + currentPots + "..."
        val newState = currentPots.mapIndexed { index, c ->
            if (index >= 2 && index < currentPots.length - 2) {
                map[currentPots.substring(index - 2, index + 3)] ?: '.'
            } else {
                '.'
            }
        }
        currentPots = newState.joinToString(separator = "")
        zeroIndex -= currentPots.indexOf("#")-3
        currentPots = "..." + currentPots.substring(currentPots.indexOf("#"), currentPots.lastIndexOf("#") + 1) + "..."
    }
    return currentPots.foldIndexed(0) { index, acc, char ->
        if (char == '#')
            acc + (index - zeroIndex)
        else
            acc
    }
}

fun partTwo(input: Pair<String,List<Pair<String,String>>>) : Long {
    var map = input.second.map { it.first to it.second[0] }.toMap()

    var currentPots = input.first
    var zeroIndex = 0
    var score = 0
    var oldScore = 0
    var index = 1
    var stop = false
    var reallyStop = false

    while (!reallyStop) {
        if (stop) {
            reallyStop = true
        }
        zeroIndex += 5
        currentPots = "....." + currentPots + "....."
        val newState = currentPots.mapIndexed { index, c ->
            if (index >= 2 && index < currentPots.length - 2) {
                map[currentPots.substring(index - 2, index + 3)] ?: '.'
            } else {
                '.'
            }
        }
        currentPots = newState.joinToString(separator = "")
        zeroIndex -= currentPots.indexOf("#")-5
        currentPots = currentPots.substring(currentPots.indexOf("#"), currentPots.lastIndexOf("#") + 1)
        if (currentPots.all { it == '#' }) {
            stop = true
        }
        currentPots = "....." + currentPots + "....."
        oldScore = score
        score = currentPots.foldIndexed(0) { index, acc, char ->
            if (char == '#')
                acc + (index - zeroIndex)
            else
                acc
        }
        index++
    }
    return (50000000000 - index + 1) * (score-oldScore) + score
}
