package kt.kotlinalgs.app.linkedlist

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
        val node = root ?: throw RuntimeException("No element in stack")

        root = root?.next
        if (root == null) tail = null

        return node
    }
    
    fun predecessor(node: SingleNode<T>) : SingleNode<T>? {
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

fun main(){
    println("SingleLinkedList")
    val singleList = SingleLinkedList<Int>()
    val node2 = SingleNode(2)
    singleList.add(SingleNode(1))
    singleList.add(node2)
    singleList.add(SingleNode(3))
    singleList.remove(node2)

    println(singleList.root?.value)
    println(singleList.root?.next?.value)
    println(singleList.tail?.value)
}
