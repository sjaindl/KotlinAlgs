package kt.kotlinalgs.app.tree

println("test")

val tree = BinarySearchTree<Int>(null)

/*
[5, 20] max = 20
[10, 30] max = 30
[12, 15] max = 15
[15, 20] max = 40
[17, 19] max = 40
[30, 40] max = 40
 */

tree.insert((15 to 20))
tree.insert((10 to 30))
tree.insert((17 to 19))
tree.insert((5 to 20))
tree.insert((12 to 15))
tree.insert((30 to 40))

println(tree.inorderTraversal())

val intervalTree = IntervalTree<Int>()
println(intervalTree.overlappingInterval(tree.root, 10 to 20))
println(intervalTree.overlappingInterval(tree.root, 0 to 4))
println(intervalTree.overlappingInterval(tree.root, 0 to 5))
println(intervalTree.overlappingInterval(tree.root, 0 to 500))
println(intervalTree.overlappingInterval(tree.root, 30 to 40))
println(intervalTree.overlappingInterval(tree.root, 39 to 50))
println(intervalTree.overlappingInterval(tree.root, 18 to 25))

// https://www.geeksforgeeks.org/interval-tree/
class IntervalTree<T : Comparable<T>> {
    fun overlappingInterval(root: Node<T>?, searchedInterval: Pair<T, T>): Pair<T, T>? {
        val cur = root ?: return null

        val left = cur.left

        return if (overlaps(cur, searchedInterval)) cur.value
        else if (left != null && left.max >= searchedInterval.first) overlappingInterval(left, searchedInterval)
        else overlappingInterval(root.right, searchedInterval)
    }

    private fun overlaps(root: Node<T>, searchedInterval: Pair<T, T>): Boolean {
        val low = searchedInterval.first
        val high = searchedInterval.second

        /*
        return if (root.high < low) false // interval too low (no intersection)
        else if (root.low > high) false // interval too high (no intersection)
        else if (root.low <= low && root.high >= high) true // inside
        else if (root.low >= low && root.high <= high) true // left + right outside (complete overlap)
        else if (root.low in low..high) true // left overlap
        else if (root.low <= low && root.high <= high) true // right overlap
        else false // is there any case?
        */

        // Simplified:
        return root.low <= high && low <= root.high
    }
}

data class Node<T : Comparable<T>>(
    val value: Pair<T, T>,
    var max: T,
    var left: Node<T>? = null,
    var right: Node<T>? = null
) {
    var low = value.first
    var high = value.second
}

class BinarySearchTree<T : Comparable<T>>(var root: Node<T>?) {
    fun insert(value: Pair<T, T>) { // low, high
        val node = Node(value, value.second)
        if (root == null) {
            root = node
            return
        }

        var cur = root
        while (cur != null) {
            cur.max = maxOf(node.high, cur.max)

            if (node.low <= cur.low) {
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
}
