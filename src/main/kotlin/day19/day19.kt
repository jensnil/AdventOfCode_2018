package day19
var register = mutableListOf(0,0,0,0,0,0)

val instructionNumberToFunction = mapOf(
        "addr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] + register[instruction[2]]) } },
        "addi" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] + instruction[2]) } },
        "mulr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] * register[instruction[2]]) } },
        "muli" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] * instruction[2]) } },
        "banr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] and register[instruction[2]]) } },
        "bani" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] and instruction[2]) } },
        "borr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] or register[instruction[2]]) } },
        "bori" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]] or instruction[2]) } },
        "setr" to { instruction : List<Int> -> register.apply { set(instruction[3], register[instruction[1]]) } },
        "seti" to { instruction : List<Int> -> register.apply { set(instruction[3], instruction[1]) } },
        "gtir" to { instruction : List<Int> -> register.apply { set(instruction[3], if (instruction[1] > register[instruction[2]]) 1 else 0) } },
        "gtri" to { instruction : List<Int> -> register.apply { set(instruction[3], if (register[instruction[1]] > instruction[2]) 1 else 0) } },
        "gtrr" to { instruction : List<Int> -> register.apply { set(instruction[3], if (register[instruction[1]] > register[instruction[2]]) 1 else 0) } },
        "eqir" to { instruction : List<Int> -> register.apply { set(instruction[3], if (instruction[1] == register[instruction[2]]) 1 else 0) } },
        "eqri" to { instruction : List<Int> -> register.apply { set(instruction[3], if (register[instruction[1]] == instruction[2]) 1 else 0) } },
        "eqrr" to { instruction : List<Int> -> register.apply { set(instruction[3], if (register[instruction[1]] == register[instruction[2]]) 1 else 0 ) } }
)


fun partOne(input: Pair<Int, List<List<Any>>>) : Int {
    val ipIndex = input.first
    val instructions = input.second
    var counter = 0
    while (0 <= register[ipIndex] && register[ipIndex] < instructions.size) {
        var instruction = input.second[register[ipIndex]]
        var instructionList = getInstructionList(instruction, ipIndex)
        //var newRegister =
        val old = register.toList()
        instructionNumberToFunction[instruction[0]]!!(instructionList)
        //println("ip=${register[ipIndex]} $register $instruction $newRegister")
        //register = newRegister
        register[ipIndex] = register[ipIndex] + 1
        if (old[0] != register[0] && old[0] != 0) {
            println("$old $instruction $register")
        }
        if (counter < 30) {
            println("$old $instruction $register")
        }
        counter++
    }
    return register[0]
}

private fun getInstructionList(instruction: List<Any>, ipIndex: Int): MutableList<Int> {
    var instructionList = instruction.drop(1).map { it.toString().toInt() }.toMutableList()
    instructionList.add(0, register[ipIndex])
    return instructionList
}


fun partTwoOld(input: Pair<Int, List<List<Any>>>) : Int {
    register[0] = 1
    val ipIndex = input.first
    val instructions = input.second
    var counter = 0
    while (0 <= register[ipIndex] && register[ipIndex] < instructions.size) {
        var instruction = input.second[register[ipIndex]]
        var instructionList = getInstructionList(instruction, ipIndex)
        //var newRegister =
        val old = register.toList()
        instructionNumberToFunction[instruction[0]]!!(instructionList)
        println("$old $instruction $register")
        //register = newRegister
        register[ipIndex] = register[ipIndex] + 1
        if (old[0] != register[0]) {
            println("$old $instruction $register")
        }
        if (counter == 100) {
            break
        }
        counter++
    }
    return register[0]

}

fun partTwo(input: Pair<Int, List<List<Any>>>) : Int {
    val number = 10551361
    //val number = 962
    //val number = 13
    var sum = 0
    for (i in 1..number) {
        if (number % i == 0) {
            println(i)
            sum += i
        }
    }
    return sum
}