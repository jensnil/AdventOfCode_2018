package day05

fun reduce(input : String) : String {
    val diff = 'A' - 'a'
    var i = 0
    var foundHit = true
    var toReturn = input

    while (foundHit) {
        foundHit = false
        i = 0
        while (i < toReturn.length) {
            if (i + 1 < toReturn.length && ((toReturn[i] + diff) == toReturn[i + 1] || (toReturn[i]) == toReturn[i + 1] + diff)) {
                toReturn = toReturn.substring(0, i) + toReturn.substring(i+2)
                foundHit = true
                i = 0
                break
            }
            i++
        }
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
