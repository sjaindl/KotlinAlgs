println("test")

val root = Node(2)
root.left = Node(1)
root.right = Node(3)

val tree = BinarySearchTree<Int>(root)

println("inorderTraversal:")
tree.inorderTraversal()

println("preorderTraversal:")
tree.preorderTraversal()

println("postorderTraversal:")
tree.postorderTraversal()

data class Node<T>(
    val value: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null
)

class BinarySearchTree<T>(var root: Node<T>?) {
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
