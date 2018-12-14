package day14

fun printList(items : List<Int>, elf1 : Int, elf2 : Int) {
    items.forEachIndexed { index, item ->
        if (elf1 == index) {
            print("(")
        }
        if (elf2 == index) {
            print("[")
        }
        print(item.toString())
        if (elf2 == index) {
            print("]")
        }
        if (elf1 == index) {
            print(")")
        }
        print(" ")
    }
    println()
}

fun partOne(input: String) : String {
    var match = input.toInt()
    var items = mutableListOf(3,7)
    var elf1 = 0
    var elf2 = 1

    while (true) {
        printList(items, elf1, elf2)
        items.addAll((items[elf1] + items[elf2]).toString().map {
            it.toString().toInt()
        })
        elf1 = (elf1 + items[elf1] + 1) % items.size
        elf2 = (elf2 + items[elf2] + 1 ) % items.size


        if (match + 10 <= items.size) {
            return items.subList(match, match + 10).joinToString (separator =  "")
        }
    }
    return ""
}

fun partTwo(input: String) : Int {
    var items = StringBuffer()
    items.append("37")
    var elf1 = 0
    var elf2 = 1

    while (true) {
        items.append("" + (("" + items[elf1]).toInt() + ("" + items[elf2]).toInt()))
        elf1 = (elf1 + (""+items[elf1]).toInt() + 1) % items.length
        elf2 = (elf2 + (""+items[elf2]).toInt() + 1 ) % items.length
        if (items.length-2 >= input.length &&
                (items.substring(items.length - input.length - 1, items.length - 1).equals(input)
                || items.substring(items.length - input.length, items.length).equals(input))
                        ) {
            return items.lastIndexOf(input)
        }
    }
    return 0
}
