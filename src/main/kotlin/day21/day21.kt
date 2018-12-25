package day21

var register = mutableListOf(0L,0L,0L,0L,0L,0L)

val instructionNumberToFunction = mapOf(
        "addr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] + register[instruction[2]]) } },
        "addi" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] + instruction[2]) } },
        "mulr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] * register[instruction[2]]) } },
        "muli" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] * instruction[2]) } },
        "banr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] and register[instruction[2]]) } },
        "bani" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] and instruction[2].toLong()) } },
        "borr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] or register[instruction[2]]) } },
        "bori" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] or instruction[2].toLong()) } },
        "setr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]]) } },
        "seti" to { instruction : List<Int> -> register.apply { set(instruction[3], instruction[1].toLong()) } },
        "gtir" to { instruction : List<Int> -> register.apply { set(instruction[3], if (instruction[1] > register[instruction[2]]) 1 else 0) } },
        "gtri" to { instruction : List<Int> -> register.apply { set(instruction[3], if (register[instruction[1]] > instruction[2]) 1 else 0) } },
        "gtrr" to { instruction : List<Int> -> register.apply { set(instruction[3], if (register[instruction[1]] > register[instruction[2]]) 1 else 0) } },
        "eqir" to { instruction : List<Int> -> register.apply { set(instruction[3], if (instruction[1].toLong() == register[instruction[2]]) 1 else 0) } },
        "eqri" to { instruction : List<Int> -> register.apply { set(instruction[3], if (register[instruction[1]] == instruction[2].toLong()) 1 else 0) } },
        "eqrr" to { instruction : List<Int> -> register.apply { set(instruction[3], if (register[instruction[1]] == register[instruction[2]]) 1 else 0 ) } }
)

private fun getInstructionList(instruction: List<Any>, ipIndex: Int): MutableList<Int> {
    var instructionList = instruction.drop(1).map { it.toString().toInt() }.toMutableList()
    instructionList.add(0, register[ipIndex].toInt())
    return instructionList
}


fun partOne(input: Pair<Int, List<List<Any>>>) : Int {
    register[0] = 0
    val ipIndex = input.first
    val instructions = input.second
    var counter = 0
    while (0 <= register[ipIndex] && register[ipIndex] < instructions.size) {
        var instruction = input.second[register[ipIndex].toInt()]
        var instructionList = getInstructionList(instruction, ipIndex)
        val old = register.toList()
        instructionNumberToFunction[instruction[0]]!!(instructionList)
        //println("$old $instruction $register")
        register[ipIndex] = register[ipIndex] + 1
        if (old[0] != register[0]) {
            println("$old $instruction $register")
        }
        if (register[3] == 28L) {
            return register[4].toInt()
        }
        counter++
    }
    println("count = $counter")
    return register[0].toInt()
}



fun partTwoOld(input: Pair<Int, List<List<Any>>>) : Int {
    register[0] = 0
    val ipIndex = input.first
    val instructions = input.second
    var counter = 0
    val valuesToCompareWithRegister0 = mutableSetOf<Long>()
    var latest = -1L
    while (0 <= register[ipIndex] && register[ipIndex] < instructions.size) {
        var instruction = input.second[register[ipIndex].toInt()]
        var instructionList = getInstructionList(instruction, ipIndex)
        //var newRegister =
        val old = register.toList()
        instructionNumberToFunction[instruction[0]]!!(instructionList)
        //println("$old $instruction $register")
        if (register[3] == 28L) {
//            println(register[4])
            if (valuesToCompareWithRegister0.contains(register[4])) {
                println(latest.toString() + "*")
                break
            } else {
                latest = register[4]
                valuesToCompareWithRegister0.add(register[4])
            }
        }
        if (register.any { it < 0 }) {
            println("negative")
        }
        //register = newRegister
        register[ipIndex] = register[ipIndex] + 1
        if (old[4] != register[4]) {
            println("$old $instruction $register")
        }
        counter++
    }
    println("count = $counter")
    return register[0].toInt()
}


fun partTwo(input: Pair<Int, List<List<Any>>>) : Int {
    val ipIndex = input.first
    val instructions = input.second
    var counter = 0
    val valuesToCompareWithRegister0 = mutableSetOf<Long>()
    var latest = -1L
    while (0 <= register[ipIndex] && register[ipIndex] < instructions.size) {
        var instruction = input.second[register[ipIndex].toInt()]
        var instructionList = getInstructionList(instruction, ipIndex)
        val old = register.toList()
        if (register[3] == 24L) {
            instructionNumberToFunction[instruction[0]]!!(instructionList)
            register[5] = register[1] / 256
        } else {
            instructionNumberToFunction[instruction[0]]!!(instructionList)
        }
        //println("$old $instruction $register")

        if (register[3] == 28L) {
//            println(register[4])
            if (valuesToCompareWithRegister0.contains(register[4])) {
                return latest.toInt()
            } else {
                latest = register[4]
                valuesToCompareWithRegister0.add(register[4])
            }
        }
        register[ipIndex] = register[ipIndex] + 1
        if (old[0] != register[0]) {
            println("$old $instruction $register")
        }
        counter++
    }
    return -1
}

