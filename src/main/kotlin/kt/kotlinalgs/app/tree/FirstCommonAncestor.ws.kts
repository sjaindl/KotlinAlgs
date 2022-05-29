println("test")

val root = Node(20)
root.left = Node(10)
root.right = Node(30)
root.left?.right = Node(15)
root.left?.right?.right = Node(17)
root.left?.left = Node(5)
root.left?.left?.left = Node(3)
root.left?.left?.right = Node(7)

val tree = BinarySearchTree<Int>()
println(tree.firstCommonAncestor( // 10
    root.left!!.left!!.right!!, // 7
    root.left!!.right!!.right!!, // 17
    root // 20
))

data class Node<T>(
    val value: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null
)

class BinarySearchTree<T> {
    fun firstCommonAncestor(p: Node<T>, q: Node<T>, root: Node<T>): Node<T>? {
        // recursive search
        return firstCommonAncestorRec(p, q, root).ancestor
    }

    fun firstCommonAncestorRec(p: Node<T>, q: Node<T>, cur: Node<T>?): Result<T> {
        if (cur == null) return Result(false, false, null)

        val left = firstCommonAncestorRec(p, q, cur.left)
        if (left.ancestor != null) return left

        val right = firstCommonAncestorRec(p, q, cur.right)
        if (right.ancestor != null) return right

        val containsP = left.containsP || right.containsP || cur == p
        val containsQ = left.containsQ || right.containsQ || cur == q
        val ancestor = if(containsP && containsQ) cur else null

        return Result(containsP, containsQ, ancestor)
    }

    data class Result<T>(
        val containsP: Boolean,
        val containsQ: Boolean,
        val ancestor: Node<T>?
    )
}
