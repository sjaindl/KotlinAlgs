package kt.kotlinalgs.app.linkedlist

//CTCI 2.4

val list = SingleLinkedList<Int>()
list.add(SingleNode(3))
list.add(SingleNode(5))
list.add(SingleNode(8))
list.add(SingleNode(5))
list.add(SingleNode(10))
list.add(SingleNode(2))
list.add(SingleNode(1))

list.printAll()
println("k to last ..")
LastChecker<Int>().kToLast(list, 2)

class LastChecker<T> {
    fun kToLast(list: SingleLinkedList<T>, k: Int): T? {
        if (k < 0) return null

        return kToLastRec(list.root, k)?.node?.value
    }

    private fun kToLastRec(node: SingleNode<T>?, k: Int): ResultWrapper<T> {
        if (node == null) return ResultWrapper(0, null)

        val result = kToLastRec(node.next, k)
        if (result.node != null) return result

        if (result.index == k) {
            return ResultWrapper(result.index + 1, node)
        }

        return ResultWrapper(result.index + 1, null)
    }

    data class ResultWrapper<T>(val index: Int, val node: SingleNode<T>?)
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