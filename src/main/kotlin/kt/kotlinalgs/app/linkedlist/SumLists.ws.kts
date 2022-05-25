package kt.kotlinalgs.app.linkedlist

//CTCI 2.4

val node1Head = SingleNode<Int>(1)
node1Head.next = SingleNode<Int>(2)
node1Head.next!!.next = SingleNode<Int>(3)

val node2Head = SingleNode<Int>(3)
node2Head.next = SingleNode<Int>(4)

println("sumListReverse ..")
val result = ListSummer().sumListReverse(node1Head, node2Head)
val list = SingleLinkedList<Int>()
//list.add(result!!)

println("Result: ")
list.printAll()

class ListSummer {
    // e.g. 1 -> 2 -> 0 + 3 + 4 -> 8 = 171
    fun sumListReverse(
        head1: SingleNode<Int>,
        head2: SingleNode<Int>
    ): SingleNode<Int>? {
        println("gogogo")
        println(head1)
        println(head2)

        println("gogogo 2")
        val list1Len = len(head1)
        val list2Len = len(head2)

        println(list1Len)
        println(list2Len)

        var first = head1
        var second = head2

        if (list1Len > list2Len) {
            second = padWithZeroes(head2, list1Len - list2Len)
        } else if (list2Len > list1Len) {
            first = padWithZeroes(head1, list2Len - list1Len)
        }

        return sumListReverseRec(first, second).sumNode
    }

    private fun len(node: SingleNode<Int>?): Int {
        var len = 0
        var cur = node

        while (cur != null) {
            len++
            cur = cur.next
        }

        return len
    }

    private fun padWithZeroes(node: SingleNode<Int>, padCount: Int): SingleNode<Int> {
        var curNode = node
        for (cur in 0 until padCount) {
            val newNode = SingleNode(0)
            newNode.next = curNode
            curNode = newNode
        }

        return curNode
    }

    private fun sumListReverseRec(node1: SingleNode<Int>?, node2: SingleNode<Int>?): SumResult {
        if (node1 == null || node2 == null) return SumResult(false, null)

        val result = sumListReverseRec(node1.next, node2.next)

        val sum = node1.value + node2.value + if (result.carry) 1 else 0
        val carry = sum >= 10
        val newNode = SingleNode(sum % 10)

        newNode.next = result.sumNode
        return SumResult(carry, newNode)
    }

    data class SumResult(var carry: Boolean, var sumNode: SingleNode<Int>?)
}

public class SingleLinkedList<T>(
    var root: SingleNode<T>? = null,
    var tail: SingleNode<T>? = null
) {
    fun add(node: SingleNode<T>) {
        if (root == null) {
            root = node
            tail = node
        } else {
            tail?.next = node
            tail = node
        }
    }

    fun addFront(node: SingleNode<T>) {
        if (root == null) {
            root = node
            tail = node
        } else {
            node.next = root
            root = node
        }
    }

    fun remove(node: SingleNode<T>) {
        if (root == null) throw RuntimeException("No root")

        if (node == root) {
            if (root == tail) {
                root = null
                tail = null
            } else {
                val temp = root?.next
                root?.next = null
                root = temp
            }
        } else {
            val pred = predecessor(node)
            if (node == tail) {
                pred?.next = null
                tail = pred
            } else {
                val next = node.next
                node.next = null
                pred?.next = next
            }
        }
    }

    fun removeFirst(): SingleNode<T> {
        val node = root ?: throw RuntimeException("No root")

        root = root?.next
        if (root == null) tail = null

        return node
    }

    fun predecessor(node: SingleNode<T>): SingleNode<T>? {
        var curNode = root
        var prev: SingleNode<T>? = null
        while (curNode != null) {
            if (curNode == node) return prev
            prev = curNode
            curNode = curNode?.next
        }

        return null
    }

    fun printAll() {
        var curNode = root
        while (curNode != null) {
            println(curNode?.value)
            curNode = curNode?.next
        }
    }
}

data class SingleNode<T>(
    val value: T,
    var next: SingleNode<T>? = null
)