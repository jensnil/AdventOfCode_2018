package day08

class TreeNode(val children : MutableList<TreeNode> = mutableListOf(), val metadata : MutableList<Int> = mutableListOf()) {
    fun getSum() : Int {
        return metadata.sum() + children.map { it.getSum() }.sum()
    }
    fun getSum2() : Int {
        if (children.size == 0) {
            return metadata.sum()
        } else {
            return metadata.map {
                if (0 < it && it < children.size + 1) {
                    children[it - 1].getSum2()
                } else {
                    0
                }
            }.sum()
        }
    }
}

fun getChildren(input : List<Int>, start : Int) : Pair<TreeNode,Int> {
    val toReturn = TreeNode()
    val childCount = input[start]
    val metadataCount = input[start + 1]
    var index = start + 2
    for (i in 0 until childCount) {
        val treeNodeAndEnd = getChildren(input, index)
        index = treeNodeAndEnd.second
        toReturn.children.add(treeNodeAndEnd.first)
    }
    toReturn.metadata.addAll(input.subList(index, index + metadataCount))
    return Pair(toReturn, index + metadataCount)
}


fun partOne(input: List<Int>) : Int {
    val root = getChildren(input, 0).first
    return root.getSum()
}

fun partTwo(input: List<Int>) : Int {
    val root = getChildren(input, 0).first
    return root.getSum2()
}
