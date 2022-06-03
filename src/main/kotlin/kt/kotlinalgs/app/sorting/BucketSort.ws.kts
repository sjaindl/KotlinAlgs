package kt.kotlinalgs.app.sorting

println("test")

val array = doubleArrayOf(0.665, 0.897, 0.3434, 0.7, 0.656, 0.1234, 0.664, 0.0, 0.99, 1.0)

BucketSort().sort(array)
// runtime: O(N) best + average if uniform distributed, O(N^2) worst case otherwise
// space: O(N)
// stable

println(array.map { it })

class BucketSort {
    fun sort(array: DoubleArray) {
        if (array.size <= 1) return

        // 1. Put each value in a bucket - O(N)
        val buckets = Array<DoublyLinkedList>(array.size + 1) { DoublyLinkedList() }

        // could also use arraylist/mutablelist for buckets, as it is asymp. O(1) for resizing built-in sort functions can be used
        //val buckets2 = Array<MutableList<Double>>(array.size + 1) { mutableListOf() }

        array.forEach {
            val index = (it * array.size).toInt()
            val node = DoublyNode(it)
            buckets[index].add(node)

            //buckets2[index].add(it)
        }

        buckets.forEachIndexed { index, bucket ->
            println("")
            print("$index: ")
            var cur = bucket.root
            while (cur != null) {
                print("${cur.value} -> ")
                cur = cur.next
            }
        }

        // 2. sort each bucket - O(N) average - If we have a uniform distribution, bucket sort works efficient
        buckets.forEach { bucket ->
            sort(bucket)
        }

        /*
        buckets2.forEach { bucket ->
            bucket.sort()
        }
         */

        // 3. append buckets / write back to array - O(N)
        var index = 0
        buckets.forEach { bucket ->
            var curNode = bucket.root
            while (curNode != null) {
                array[index] = curNode.value
                curNode = curNode.next
                index++
            }
        }

        /*
        index = 0
        buckets2.forEach { bucket ->
            bucket.forEach {
                array[index] = it
                index++
            }
        }
         */
    }

    private fun sort(bucket: DoublyLinkedList) {
        // Using insertion sort. It's stable and can therefore be used for bucket sort and is efficient on a small amount of data
        var curNode = bucket.root

        while (curNode != null) {
            var curSortingNode: DoublyNode? = curNode
            var curSortingNodePrev = curSortingNode?.prev
            while (curSortingNode != null && curSortingNodePrev != null && curSortingNode.value < curSortingNodePrev.value) {
                val temp = curSortingNode.value
                curSortingNode.value = curSortingNodePrev.value
                curSortingNodePrev.value = temp

                curSortingNode = curSortingNodePrev
                curSortingNodePrev = curSortingNodePrev.prev
            }

            curNode = curNode?.next
        }
    }
}

class DoublyLinkedList(
    var root: DoublyNode? = null,
    var tail: DoublyNode? = null
) {
    fun add(node: DoublyNode) {
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

    fun remove(node: DoublyNode) {
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

data class DoublyNode(
    var value: Double,
    var prev: DoublyNode? = null,
    var next: DoublyNode? = null
)