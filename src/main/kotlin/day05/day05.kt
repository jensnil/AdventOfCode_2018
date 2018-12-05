package day05

fun reduce(input : String) : String {
    val diff = 'A' - 'a'
    var i = 0
    var toReturn = input
    while (i < toReturn.length) {
        if (0 <= i && i + 1 < toReturn.length && ((toReturn[i] + diff) == toReturn[i + 1] || (toReturn[i]) == toReturn[i + 1] + diff)) {
            toReturn = toReturn.substring(0, i) + toReturn.substring(i+2)
            i -= 2
        }
        i++
    }
    println(toReturn)
    return toReturn
}

fun partOne(input : String) : Int {
    return reduce(input).length

}


fun partTwo(input : String) : Int {
    var smallest = Int.MAX_VALUE
    ('a'..'z').forEach {
        val newSize = reduce(input.replace(it + "", "", true)).length
        if (newSize < smallest) {
            smallest = newSize
        }
    }
    return smallest
}
