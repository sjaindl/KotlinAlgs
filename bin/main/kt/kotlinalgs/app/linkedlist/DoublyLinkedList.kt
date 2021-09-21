package kt.kotlinalgs.app.linkedlist

class DoublyLinkedList<T>(
    var root: DoublyNode<T>? = null,
    var tail: DoublyNode<T>? = null
) {
    fun add(node: DoublyNode<T>) {
        if (root == null) {
            root = node
            tail = node
        } else {
            val temp = tail
            temp?.next = node
            node.prev = temp
            tail = node
        }
    }

    fun remove(node: DoublyNode<T>) {
        if (root == null) return

        if (root == node) {
            if (root == tail) {
                root = null
                tail = null
            } else {
                root?.next?.prev = null
                root?.next = null
                root = node
            }
        } else if (tail == node) {
            val temp = tail?.prev
            tail?.prev = null
            tail = temp
        } else {
            val prev = node.prev
            val next = node.next
            prev?.next = next
            next?.prev = prev
            node.prev = null
            node.next = null
        }
    }
}

data class DoublyNode<T>(
    val value: T,
    var prev: DoublyNode<T>? = null,
    var next: DoublyNode<T>? = null
)

fun main() {
    println("DoublyLinkedList")
    val list = DoublyLinkedList<Int>()
    val node2 = DoublyNode(2)
    list.add(DoublyNode(1))
    list.add(node2)
    list.add(DoublyNode(3))
    list.remove(node2)

    println(list.root?.value)
    println(list.root?.next?.value)
    println(list.tail?.value)
}
