import java.util.*

Solution().test()

/*
LL!!
1. Weave: bei LL immer add + removeFirst/Last, wegen O(1), kein Indexzugriff O(N)!!
    ev. 2 mal mit cur list & first/second oder andere list (eine remove, eine add)!
2. Weave: Dann am ende der recursion/BC clonen/copy!! sonst wird das result zerstört.
    oder alternative bei jedem funktionscall clonen, aber braucht mehr space.
3. Sequ.: BC return wert nicht emptyList, sondern list<linkedList>!
    sonst ist return am ende immer empty (wegen for eaches)
 */

class Solution {
    fun test() {
        println("test")

        val root = Node(50)
        root.left = Node(20)
        root.left?.right = Node(25)
        root.left?.left = Node(10)
        root.left?.left?.left = Node(5)
        root.left?.left?.right = Node(15)

        root.right = Node(60)
        root.right?.right = Node(70)
        root.right?.right?.left = Node(65)
        root.right?.right?.right = Node(80)
/*

val root = Node(1)
root.left = Node(2)
root.right = Node(3)

 */

        val tree = BinarySearchTree<Int>()
        val result = tree.bstSequences(root)
        println(result.size)
        result.forEach {
            println("")
            it.forEach { node ->
                print("${node.value} -> ")
            }
        }
    }
}

data class Node<T>(
    val value: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null
)

class BinarySearchTree<T> {
    fun bstSequences(root: Node<T>): List<LinkedList<Node<T>>> {
        return bstSequencesRec(root)
    }

    private fun bstSequencesRec(
        root: Node<T>?
    ): MutableList<LinkedList<Node<T>>> {
        // 1. BC return list with root only
        if (root == null) return mutableListOf(LinkedList())

        // 2. leftList = bstSequencesRec(root, root.left)
        val leftList = bstSequencesRec(root.left)

        // 3. rightList = bstSequencesRec(root, root.right)
        val rightList = bstSequencesRec(root.right)

        // 4. return weaveElements(leftList, rightList, 0, 0)
        //if (leftList.isEmpty()) return rightList
        //else if(rightList.isEmpty()) return leftList

        val cur: LinkedList<Node<T>> = LinkedList()
        val weaved: MutableList<LinkedList<Node<T>>> = mutableListOf() // []

        // println(leftList)
        // println(rightList)
        cur.add(root)

        leftList.forEach { left -> // [1]
            rightList.forEach { right -> // [3]
                weaveElements(cur, weaved, left, right)
                // println("weaved_: $weaved")
            }
        }

        //  println("weaved: $weaved")

        return weaved
    }

    private fun weaveElements(
        cur: LinkedList<Node<T>>, // [1,]
        result: MutableList<LinkedList<Node<T>>>, // []
        first: LinkedList<Node<T>>, // [1]
        second: LinkedList<Node<T>> // [3]
    ) {
        // println("cur: $cur, first: $first, second: $second")

        if (first.isEmpty() && second.isEmpty()) {
            // BC all processed
            //     println("add")
            result.add((cur.clone() as LinkedList<Node<T>>))

            return
        }

        if (!first.isEmpty()) {
            val firstNode = first.removeFirst()
            cur.add(firstNode)
            weaveElements(cur, result, first, second)
            cur.pollLast()
            first.addFirst(firstNode)
        }

        if (!second.isEmpty()) {
            val secondNode = second.removeFirst()
            cur.add(secondNode)
            weaveElements(cur, result, first, second)
            cur.pollLast()
            second.addFirst(secondNode)
        }
    }
}
