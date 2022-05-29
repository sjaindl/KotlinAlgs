package kt.kotlinalgs.app.graph

import java.util.*

/*
    Achtung: Immer map etc. zurücksetzen!!
    BC: from root, cur element .. test cases!
 */

Solution().test()

/*
10
15,5
17,7,2
18,8,3,1

alt.. store complete sum in set<Int>
10, 15, 17, 18
    .. check at each node if (set.contains(runningSum - k)) -> incr. count

sums = Map number to #number count

start at root
rec pathsWithSum(root: Node<T>, sum: Int, sums: MutableMap<Int, Int>, int runningSum)
    ctr = 0
    sums.addOrIncrease(runningSum + root.value)

    // start new path or continue path(s)
    check if value == sum -> ctr++
          if (set.contains(node.value - k)) -> ctr += sums[..]  (count)
    return ctr + pathsWithSum(root.left,..) + pathsWithSum(root.right,..) +


1,-1, 8
.. 1,0,8  -> 2

1,-2,8
.. 1,-1,7 -> 1
 */

class Solution {
    fun test() {
        println("test")

        val root = Node(10)
        root.left = Node(5)
        root.left?.left = Node(3)
        root.left?.left?.left = Node(3)
        root.left?.left?.right = Node(-2)
        root.left?.right = Node(1)
        root.left?.right?.right = Node(2)

        root.right = Node(-3)
        root.right?.right = Node(11)

        val tree = BinarySearchTree()
        println(tree.pathsWithSum(root, 3))
        println(tree.pathsWithSum(root, 4))
        println(tree.pathsWithSum(root, 8))
        println(tree.pathsWithSum(root, 10))
    }
}

data class Node(
    val value: Int,
    var left: Node? = null,
    var right: Node? = null
)

class BinarySearchTree {
    fun pathsWithSum(root: Node, sum: Int): Int {
        val sums: MutableMap<Int, Int> = mutableMapOf()
        return pathsWithSumRec(root, sum, sums, 0)
    }

    private fun pathsWithSumRec(
        root: Node?, // 3
        targetSum: Int, // 8
        sums: MutableMap<Int, Int>, // [10:1, 15:1, 18:1]
        runningSum: Int) : Int { // 15
        if (root == null) return 0

        /*
         1,-1, 8
      RS   .. 1,0,8  -> 2

         1,-2,8
      RS   .. 1,-1,7 -> 1
         */

        val newSum = runningSum + root.value
        val searchedSum = newSum - targetSum

        var paths = if(newSum == targetSum) 1 else 0

        sums[searchedSum]?.let { count ->
            // covers also case if (root.value == targetSum)
            /*
            e.g. with targetSum == 8 at index 2:
            1,-2,8
     RS   .. 1,-1,7 -> 1
            rs - ts = 7 - 8 = -1
             */
            paths += count
        }

        addOrIncrease(sums, newSum)

        paths += pathsWithSumRec(root.left, targetSum, sums, newSum)
        paths += pathsWithSumRec(root.right, targetSum, sums, newSum)

        // Achtung: Immer zurücksetzen!!
        removeOrDecrease(sums, newSum)

        return paths
    }

    private fun addOrIncrease(sums: MutableMap<Int, Int>, value: Int) {
        val oldCount = sums.getOrDefault(value, 0)
        sums[value] = oldCount + 1
    }

    private fun removeOrDecrease(sums: MutableMap<Int, Int>, value: Int) {
        val oldCount = sums.getOrDefault(value, 0)
        if (oldCount > 1) sums[value] = oldCount - 1
        else sums.remove(value)
    }
}
