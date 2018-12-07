package day07

private val states = mutableMapOf<Char,MutableList<Char>>()
private val dependencies = mutableMapOf<Char,MutableList<Char>>()
private var currentStates = mutableSetOf<Char>()
private val toReturn = StringBuilder()

private fun populate(input: List<Pair<Char, Char>>) {
    dependencies.clear()
    currentStates.clear()
    states.clear()
    toReturn.setLength(0)
    input.forEach {

        if (!states.containsKey(it.first)) {
            states[it.first] = mutableListOf()
        }
        states[it.first]!!.add(it.second)

        if (!dependencies.containsKey(it.second)) {
            dependencies[it.second] = mutableListOf()
        }
        dependencies[it.second]!!.add(it.first)
    }
    currentStates.addAll(states.keys.minus(dependencies.keys))
}

private fun getNewStates(currentState : Char) {
    currentStates = currentStates.toSortedSet()

    currentStates.remove(currentState)

    toReturn.append(currentState)
    if (states.contains(currentState)) {
        val nextStates = states[currentState]!!

        nextStates.forEach {
            if (dependencies[it]!!.all {it2 -> toReturn.contains(it2) }) {
                currentStates.add(it)
            }
        }
        currentStates = currentStates.toSortedSet()
    }
}

fun partOne(input: List<Pair<Char, Char>>) : String {
    populate(input)

    while (!currentStates.isEmpty()) {
        val currentState = currentStates.first()
        getNewStates(currentState)
    }

    return toReturn.toString()
}

fun partTwo(input: List<Pair<Char, Char>>, workerCount : Int, workTime : Int) : Int {
    val workers = mutableListOf<Pair<Char,Int>>()
    var currentTime = 0

    populate(input)

    while (!currentStates.isEmpty() || !workers.isEmpty())
    {
        workers.toList().forEach {
            if (it.second  == currentTime) {
                getNewStates(it.first)
                workers.remove(it)
            }
        }
        while (!currentStates.isEmpty() && workers.size < workerCount) {
            workers.add(Pair(currentStates.first(), currentTime + (currentStates.first() - 'A') + 1 + workTime))
            currentStates.remove(currentStates.first())
        }
        currentTime++
    }
    return currentTime - 1
}
