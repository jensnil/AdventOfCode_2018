package day04

import lib.maxIndex

data class Event (
        val year : Int,
        val month : Int,
        val day: Int,
        val hour : Int,
        val minute : Int,
        val state : String,
        val guard : Int
)

fun partOneAndTwo(input2 : List<Event>) : Pair<Int,Int> {
    val schedule = HashMap<Int, MutableList<Int>>()

    val input = input2.sortedWith(compareBy({ it.year }, { it.month }, { it.day }, {it.minute}))

    var isAsleep = false
    var guard = 0
    var i = 0
    var minute : Int
    while (i < input.size) {
        if (i < input.size && input[i].state.startsWith("Guard")) {
            guard = input[i].guard
            isAsleep = false
            if (input[i].hour == 0) {
                minute = input[i].minute
            } else {
                minute = 0
            }
            i++
        } else {
            minute = 0
        }
        while (minute < 60) {
            if (i < input.size && input[i].state.startsWith("falls") && input[i].minute == minute) {
                isAsleep = true
                i++
            } else if (i < input.size && input[i].state.startsWith("wakes") && input[i].minute == minute) {
                isAsleep = false
                i++
            }
            if (isAsleep) {
                if (schedule.containsKey(guard)) {
                    schedule[guard]!![minute] = (schedule[guard]!![minute] + 1)
                } else {
                    schedule[guard] = MutableList(60) { 0 }
                    schedule[guard]!![minute] = 1
                }
            }
            minute++
        }
    }
    val maxGuard = schedule.maxBy { it.value.sum() }!!.key
    val maxMinute = schedule.maxBy { it.value.sum() }!!.value.maxIndex()!!

    val maxGuardMinute = schedule.maxBy { it.value.max()!! }

    return Pair(maxGuard * maxMinute, maxGuardMinute!!.key * schedule[maxGuardMinute!!.key]!!.maxIndex()!!)
}
