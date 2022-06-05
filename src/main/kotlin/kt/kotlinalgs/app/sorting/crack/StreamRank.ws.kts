package kt.kotlinalgs.app.sorting

val streamRank = StreamRank()

for (value in intArrayOf(5, 1, 4, 4, 5, 9, 7, 13, 3)) {
    streamRank.track(value)
}

/*
Tree:
                                5(5)
      1(0)                                   9(1)
                   4(2)                7(0)          13(0)
              4(1)      5(0)
         3(0)
 */

println(streamRank.numTree.inorderTraversal())

println(streamRank.getRankOfNumber(0)) // 0
println(streamRank.getRankOfNumber(1)) // 0
println(streamRank.getRankOfNumber(3)) // 1
println(streamRank.getRankOfNumber(4)) // 3
println(streamRank.getRankOfNumber(13)) // 8
println(streamRank.getRankOfNumber(5)) // 5
println(streamRank.getRankOfNumber(6)) // 6
println(streamRank.getRankOfNumber(12)) // 8
println(streamRank.getRankOfNumber(14)) // 9

// O(log N) track + getRank if tree would be balanced, else O(N) runtime
// O(N) space
class StreamRank {
    val numTree = BinarySearchTree<Int>()

    fun track(num: Int) {
        numTree.insert(num)
    }

    fun getRankOfNumber(num: Int): Int {
        return numTree.rank(num)
    }
}

data class Node<T : Comparable<T>>(
    val value: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null,
    var sizeOfSmallerElements: Int = 0
)

class BinarySearchTree<T : Comparable<T>>(var root: Node<T>? = null) {
    fun rank(value: T): Int {
        var rank = 0
        var curNode = root

        while (curNode != null) {
            if (curNode.value == value) {
                return rank + curNode.sizeOfSmallerElements
            } else if (curNode.value > value) {
                // go left, doesn't change rank
                curNode = curNode.left
            } else {
                // go right, increase rank by skipped elements count
                rank += curNode.sizeOfSmallerElements + 1 // + 1 for root node

                curNode = curNode.right
            }
        }

        return rank
    }

    fun insert(value: T) {
        val node = Node(value)
        if (root == null) {
            root = node
            return
        }

        var cur = root
        while (cur != null) {
            if (value <= cur.value) {
                cur.sizeOfSmallerElements++

                if (cur.left == null) {
                    cur.left = node
                    return
                } else {
                    cur = cur.left
                }
            } else { // go right
                if (cur.right == null) {
                    cur.right = node
                    return
                } else {
                    cur = cur.right
                }
            }
        }
    }

    fun inorderTraversal() {
        inorderTraversal(root)
    }

    private fun inorderTraversal(root: Node<T>?) {
        if (root == null) return
        // 1. visit left
        inorderTraversal(root.left)

        // 2. visit root
        println("${root.value}: ${root.sizeOfSmallerElements}")

        // 3. visit right
        inorderTraversal(root.right)
    }
}
