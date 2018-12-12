//package day12
//
//class Node(val index : Int,var value : Char, var prev : Node?, var next : Node?) {
//    var nextValue = '!'
//    fun insert(i : Int, v: Char) : Node {
//        val toInsert = Node(i, v, prev, this)
//        prev!!.next = toInsert
//        prev = toInsert
//        return toInsert
//    }
//
//    fun remove() : Node {
//        prev!!.next = next
//        next!!.prev = prev
//        return next!!
//    }
//}
//
//fun populate(initialState : String) : Node {
//    val toReturn = Node(0, initialState[0], null, null)
//    toReturn.prev = Node(-1, '.', null, toReturn)
//    toReturn!!.prev!!.prev = Node(-2, '.', null, toReturn.prev)
//
//    var currentNode = toReturn
//    initialState.substring(1).forEachIndexed { i, item ->
//        currentNode.next = Node(i + 1, item, currentNode, null)
//        currentNode = currentNode.next!!
//    }
//    toReturn.prev
//    return toReturn
//}
//
//fun printNode(node : Node) {
//    var currentNode = node
//    while (currentNode.prev != null) {
//        currentNode = currentNode.prev!!
//    }
//    while (currentNode != null) {
//        if (currentNode.index == 0) {
//            print("(")
//        }
//        print(currentNode.value)
//        if (currentNode.index == 0) {
//            print(")")
//        }
//        if (currentNode.next == null) {
//            break
//        }
//        currentNode = currentNode.next!!
//    }
//}
//
//fun getPattern(node : Node) : String {
//    val prev = node?.prev?.value ?: "."
//    val prevprev = node?.prev?.prev?.value ?: "."
//    val next = node?.next?.value ?: "."
//    val nextntext = node?.next?.next?.value ?: "."
//
//    return "" + prevprev + prev + node.value + next + nextntext
//}
//
//
//fun partOne(input: Pair<String,List<Pair<String,String>>>) : Int {
//    var map = input.second.map { it.first to it.second[0] }.toMap()
//
//
//    var initialState = input.first
//    var startState = populate(initialState)
//    printNode(startState)
//    println()
////    var currentNode = startState
////    while (currentNode != null) {
////        println(getPattern(currentNode))
////        if (currentNode.next == null) {
////            break
////        }
////        currentNode = currentNode.next!!
////    }
//
//
//    var index = 0
//    while (index < 20) {
//        var currentNode = startState
//        while (currentNode != null) {
//            var newValue = map[getPattern(currentNode)] ?: '.'
//            currentNode.nextValue = newValue
//            if (currentNode.next == null) {
//                newValue = map['.' + getPattern(currentNode).substring(1)] ?: '.'
//                if (newValue == '#') {
//                    currentNode
//                }
//                break
//            }
//            currentNode = currentNode.next!!
//        }
//        currentNode = startState
//        while (currentNode != null) {
//            currentNode.value = currentNode.nextValue
//            if (currentNode.next == null) {
//                break
//            }
//            currentNode = currentNode.next!!
//        }
//        printNode(startState)
//        println()
//        index++
//    }
//
//
//
//    return 0
//}
//
//
//
////fun partTwo(input: Pair<Int,Int>) : Long {
////    return bestScore(Pair(input.first, input.second * 100))
////}
