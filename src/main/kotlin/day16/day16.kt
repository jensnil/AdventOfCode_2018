package day16

val instructionNumberToFunction = listOf(
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], register[instruction[1]] + register[instruction[2]]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], register[instruction[1]] + instruction[2]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], register[instruction[1]] * register[instruction[2]]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], register[instruction[1]] * instruction[2]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], register[instruction[1]] and register[instruction[2]]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], register[instruction[1]] and instruction[2]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], register[instruction[1]] or register[instruction[2]]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], register[instruction[1]] or instruction[2]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], register[instruction[1]]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], instruction[1]) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], if (instruction[1] > register[instruction[2]]) 1 else 0) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], if (register[instruction[1]] > instruction[2]) 1 else 0) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], if (register[instruction[1]] > register[instruction[2]]) 1 else 0) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], if (instruction[1] == register[instruction[2]]) 1 else 0) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], if (register[instruction[1]] == instruction[2]) 1 else 0) } },
        { register : List<Int>, instruction : List<Int> -> register.toMutableList().apply { set(instruction[3], if (register[instruction[1]] == register[instruction[2]]) 1 else 0 ) } }
)

fun partOne(input: List<Triple<List<Int>,List<Int>,List<Int>>>) : Int {
    return input.map {
        val counter = instructionNumberToFunction.fold(0) {acc, instruction ->
            if (instruction!!(it.first, it.second) == it.third) {
                acc + 1
            } else {
                acc
            }
        }
        if (counter >= 3) 1 else 0
    }.sum()
}

fun partTwo(input: List<Triple<List<Int>,List<Int>,List<Int>>>, instructions : List<List<Int>>) : Int {
    val possibleInstructions = instructionNumberToFunction.indices.map { it to instructionNumberToFunction.toMutableSet() }.toMap().toMutableMap()

    input.forEach {
        instructionNumberToFunction.forEach {instruction ->
            var newRegister = instruction!!(it.first, it.second)
            if (it.third != newRegister) {
                possibleInstructions[it.second[0]]!!.remove(instruction)
            }
        }
    }

    val finalMapping = mutableMapOf<Int, (List<Int>,List<Int>) -> List<Int>>()
    while (!possibleInstructions.isEmpty()) {
        val currentKey = possibleInstructions.filter { (key, value) -> value.size == 1 }.keys.first()
        val toRemove = possibleInstructions[currentKey]!!.first()
        finalMapping[currentKey] = toRemove
        possibleInstructions.keys.forEach{
            possibleInstructions[it]!!.remove(toRemove)
        }
        possibleInstructions.remove(currentKey)
    }

    var register = listOf(0,0,0,0)
    instructions.forEach {
        register = finalMapping[it[0]]!!(register, it)
    }
    return register[0]
}
