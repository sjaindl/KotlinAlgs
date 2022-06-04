println("test")

val tree = BinarySearchTree<Int>(null)

tree.insert(2)
tree.insert(3)
tree.insert(1)

println("inorderTraversal:")
tree.inorderTraversal()

println("preorderTraversal:")
tree.preorderTraversal()

println("postorderTraversal:")
tree.postorderTraversal()

data class Node<T: Comparable<T>>(
    val value: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null
)

class BinarySearchTree<T: Comparable<T>>(var root: Node<T>?) {
    fun insert(value: T) {
        val node = Node(value)
        if (root == null) {
            root = node
            return
        }

        var cur = root
        while (cur != null) {
            if (value <= cur.value) {
                if (cur.left == null) {
                    cur.left = node
                    return
                } else {
                    cur = cur.left
                }
            } else { // go right
                if (cur.right == null) {
                    cur.right = node
                    return
                } else {
                    cur = cur.right
                }
            }
        }
    }

    fun inorderTraversal() {
        inorderTraversal(root)
    }

    fun inorderTraversal(root: Node<T>?) {
        if (root == null) return
        // 1. visit left
        inorderTraversal(root.left)

        // 2. visit root
        println(root.value)

        // 3. visit right
        inorderTraversal(root.right)
    }

    fun preorderTraversal() {
        preorderTraversal(root)
    }

    fun preorderTraversal(root: Node<T>?) {
        if (root == null) return

        // 1. visit root
        println(root.value)

        // 2. visit left
        inorderTraversal(root.left)

        // 3. visit right
        inorderTraversal(root.right)
    }

    fun postorderTraversal() {
        postorderTraversal(root)
    }

    fun postorderTraversal(root: Node<T>?) {
        if (root == null) return
        // 1. visit left
        inorderTraversal(root.left)

        // 2. visit right
        inorderTraversal(root.right)

        // 3. visit root
        println(root.value)
    }
}
