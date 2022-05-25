package kt.kotlinalgs.app.linkedlist

import java.util.*

//CTCI 2.4

val list1 = SingleLinkedList<Int>()
list1.add(SingleNode(1))
list1.add(SingleNode(2))
//list1.add(SingleNode(3))
list1.add(SingleNode(2))
list1.add(SingleNode(1))

list1.printAll()
println("Palindrome checker 1..")
PalindromeChecker<Int>().isPalindromeV1(list1)
PalindromeChecker<Int>().isPalindromeV2(list1)

val list2 = SingleLinkedList<Int>()
list2.add(SingleNode(1))
list2.add(SingleNode(2))
//list2.add(SingleNode(3))
list2.add(SingleNode(2))
list2.add(SingleNode(2))

list2.printAll()
println("Palindrome checker 2..")
PalindromeChecker<Int>().isPalindromeV1(list2)
PalindromeChecker<Int>().isPalindromeV2(list2)

class PalindromeChecker<T> {
    fun isPalindromeV2(list: SingleLinkedList<T>): Boolean {
        val head: SingleNode<T>? = list.root ?: return false

        val len = listLen(head)
        val stack = Stack<T>()

        var currentHead = head
        var index = 0
        while (index < len && currentHead != null) {
            if (index < len / 2) stack.push(currentHead.value)
            else if (index > len / 2 || len % 2 == 0) {
                val next = stack.pop()
                if (next != currentHead.value) return false
            }
            currentHead = currentHead.next
            index++
        }

        return true
    }

    private fun listLen(head: SingleNode<T>?): Int {
        var current = head
        var count = 0

        while (current != null) {
            count++
            current = current.next
        }

        return count
    }

    fun isPalindromeV1(list: SingleLinkedList<T>): Boolean {
        var head: SingleNode<T>? = list.root ?: return false
        val reversed = reverseList(head)
        var reversedHead: SingleNode<T>? = reversed.head

        for (index in 0 until reversed.len / 2) {
            println("compare $index: ${head?.value}/${reversedHead?.value}")

            if (head?.value != reversedHead?.value) return false
            head = head?.next
            reversedHead = reversedHead?.next
        }

        return true
    }

    private fun reverseList(node: SingleNode<T>?): ListResult<T> {
        var prev: SingleNode<T>? = null
        var cur = node?.copy()
        var count = 0

        while (cur != null) {
            val temp = cur.next?.copy()
            cur.next = prev

            prev = cur
            cur = temp

            count++
        }

        return ListResult<T>(prev, count)
    }

    data class ListResult<T>(val head: SingleNode<T>?, val len: Int)
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
