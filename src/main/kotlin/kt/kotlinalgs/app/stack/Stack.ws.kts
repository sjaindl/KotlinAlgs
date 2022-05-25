package kt.kotlinalgs.app.stack

println("test")

val stack = Stack<Int>()
stack.push(1)
stack.push(2)
println(stack.peak())
println(stack.pop())
println(stack.pop())
try {
    print(stack.pop())
} catch (e: IllegalStateException) {
    println("exception catched!")
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

class Stack<T> {
    val list = SingleLinkedList<T>()

    fun push(value: T) {
        list.addFront(SingleNode<T>(value))
    }

    fun pop(): T {
        return list.removeFirst().value
    }

    fun peak(): T {
        val node = list.root ?: throw IllegalStateException("No elements on stack")
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
        if (root == null) throw RuntimeException("No element in stack")

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
        val node = root ?: throw IllegalStateException("No element in stack")

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