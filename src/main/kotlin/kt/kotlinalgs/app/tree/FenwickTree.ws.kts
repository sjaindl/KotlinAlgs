package com.sjaindl.kotlinalgsandroid.tree

// https://www.youtube.com/watch?v=uSFzHCZ4E-8

class FenwickTree(array: IntArray) {
    // index starts at 1!
    private var tree = array

    init {
        for (index in 1 until tree.size) {
            val parent = index + (index and -index)
            if (parent < tree.size) {
                tree[parent] += tree[index]
            }
        }
    }

    fun sum(from: Int, to: Int): Int? {
        if (from < 0 || to < 0 || from > to || from >= tree.size || to >= tree.size) return null

        val fromSum = sum(from) ?: return null
        val toSum = sum(to) ?: return null

        return toSum - fromSum
    }

    fun sum(index: Int): Int? {
        if (index < 0 || index >= tree.size) return null
        var sum = 0
        var curIndex = index

        while (curIndex > 0) {
            sum += tree[curIndex]
            curIndex -= curIndex and -curIndex
        }

        return sum
    }

    fun update(index: Int, value: Int) {
        if (index < 0 || index >= tree.size) return

        var curIndex = index

        while (curIndex < tree.size) {
            tree[curIndex] += value
            curIndex += curIndex and -curIndex
        }
    }
}

val array = intArrayOf(0, 5, 2, 9, -3, 5, 20, 10, -7, 2, 3, -4, 0, -2, 15, 5)
val tree = FenwickTree(array)
tree.sum(2)
tree.sum(7)
tree.update(7, 1)
tree.sum(7)
tree.sum(2, 7)
