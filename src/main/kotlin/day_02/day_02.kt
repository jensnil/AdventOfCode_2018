package day_02

fun partOne(input : List<String>) : Int {
    val grouped = input.map { it.toCharArray().groupBy { it }}
    val twos = grouped.map { it.count { it.value.count() == 2 } }.count { it > 0 }
    val threes = grouped.map { it.count { it.value.count() == 3 } }.count { it > 0 }
    return twos * threes
}

fun partTwo(input : List<String>) : String? {
    for (i in input.indices) {
        for (j in i+1 until input.size) {
            val first = input[i]
            val second = input[j]
            val temp = first.foldIndexed("") {index , acc, value ->
                if (value == second[index]) acc + value else acc}
            if (temp.length == first.length - 1) {
                return temp
            }
        }
    }
    return null
}
