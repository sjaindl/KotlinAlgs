package kt.kotlinalgs.app.queue

println("test")

val queue = Queue<Int>()
queue.enqueue(1)
queue.enqueue(2)
queue.enqueue(3)
queue.dequeue()
queue.dequeue()
queue.dequeue()

try {
    queue.dequeue()
} catch (e: IllegalStateException) {
    println("catched exception")
}

class Queue<T> {
    private var list = SingleLinkedList<T>()

    fun enqueue(value: T) {
        val node = SingleNode<T>(value)
        list.add(node)
    }

    fun dequeue(): T {
        if (list.tail == null) throw IllegalStateException("No elements on queue")
        val node = list.removeFirst()
        return node.value
    }
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
        if (root == null) throw IllegalStateException("No element in queue")

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
        val node = root ?: throw IllegalStateException("No element in queue")

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
            curNode = curNode.next
        }

        return null
    }
}

data class SingleNode<T>(
    val value: T,
    var next: SingleNode<T>? = null
)