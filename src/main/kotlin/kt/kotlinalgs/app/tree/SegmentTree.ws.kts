package com.sjaindl.kotlinalgsandroid.tree

// https://www.youtube.com/watch?v=xztU7lmDLv8

class SegmentTree(array: IntArray, private val operation: (first: Int, second: Int) -> Int) {
    // index starts at 1!
    private val tree = IntArray(array.size * 2)

    init {
        for (index in array.size until tree.size) {
            tree[index] = array[index - array.size] // last tree half contains original array
        }

        var index = array.size - 1
        while (index >= 0) {
            tree[index] = operation(tree[2 * index], tree[2 * index + 1])
            index--
        }
    }

    fun update(index: Int, value: Int) {
        if (index < 0 || index >= tree.size / 2) return

        var curIndex = index + tree.size / 2
        tree[curIndex] = value
        curIndex /= 2

        while (curIndex > 0) {
            val newValue = operation(tree[2 * index], tree[2 * index + 1])
            if (newValue == tree[index]) return // no change!
            tree[index] = newValue
            curIndex /= 2
        }
    }

    fun rangeResult(from: Int, to: Int): Int? {
        if (from > to || from < 0 || to < 0 || from > tree.size / 2 || to > tree.size / 2) return null

        var result: Int? = null
        var leftIndex = from + tree.size / 2
        var rightIndex = to + tree.size / 2
        if (leftIndex == rightIndex) return tree[rightIndex]

        while (leftIndex < rightIndex) {
            if (leftIndex and 1 != 0) { // from is odd
                result = if (result != null) {
                    operation(result, tree[leftIndex])
                } else {
                    tree[leftIndex]
                }
                leftIndex++
            }

            if (rightIndex and 1 != 0) { // to is odd
                result = if (result != null) {
                    operation(result, tree[rightIndex])
                } else {
                    tree[rightIndex]
                }
                rightIndex--
            }

            leftIndex /= 2
            rightIndex /= 2
        }

        return result
    }
}

val array = intArrayOf(0, 6, 10, 5, 2, 7, 1, 0, 9)
val segmentTree = SegmentTree(array) { first, second -> Math.max(first, second) }
segmentTree.rangeResult(0, 2)
segmentTree.rangeResult(0, 9)
segmentTree.rangeResult(3, 8)
segmentTree.rangeResult(3, 3)
segmentTree.rangeResult(3, 4)
segmentTree.rangeResult(3, 10)
