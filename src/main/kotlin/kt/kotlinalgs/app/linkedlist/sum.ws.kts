package kt.kotlinalgs.app.linkedlist

//CTCI 2.5

val list1 = SingleLinkedList<Int>()
list1.add(SingleNode(7))
list1.add(SingleNode(1))
list1.add(SingleNode(6))

val list2 = SingleLinkedList<Int>()
list2.add(SingleNode(5))
list2.add(SingleNode(9))
list2.add(SingleNode(2))

list1.printAll()
list2.printAll()
println("sum ..")
val list = sumLists(list1, list2)
println("summed ..")
list.printAll()

fun sumLists(list1: SingleLinkedList<Int>, list2: SingleLinkedList<Int>) : SingleLinkedList<Int> {
    if (list1.root == null) return list2
    if (list2.root == null) return list1

    val root = sumNodes(list1.root, list2.root)
    return SingleLinkedList(root, null)
}

fun sumNodes(node1: SingleNode<Int>?, node2: SingleNode<Int>?, carry: Boolean = false) : SingleNode<Int>? {
    if (node1 == null && node2 == null) return null

    val value = sumUp(node1, node2, carry)
    val curNode = SingleNode<Int>(value % 10)
    val carryover = value >= 10

    curNode.next = sumNodes(node1?.next, node2?.next, carryover)
    return curNode
}

fun sumUp(node1: SingleNode<Int>?, node2: SingleNode<Int>?, carry: Boolean): Int {
    var value = if (carry) 1 else 0
    if (node1 != null) value += node1.value
    if (node2 != null) value += node2.value

    return value
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