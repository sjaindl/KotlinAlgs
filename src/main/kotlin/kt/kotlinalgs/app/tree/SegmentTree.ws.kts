package kt.kotlinalgs.app.tree

import kotlin.math.ceil
import kotlin.math.log2
import kotlin.math.pow

println("test")

val array = intArrayOf(1, 3, 5, 7, 9, 11)

val tree = SegmentTree(array)

println(tree.sum(0, 5)) // 36
println(tree.sum(0, 4)) // 25
println(tree.sum(2, 2)) // 5
println(tree.sum(0, 0)) // 1
println(tree.sum(5, 5)) // 11
println(tree.sum(2, 5)) // 32
println(tree.sum(6, 6)) // 0

println("Update index 2 to 10!")
tree.updateValue(2, 10)

println(tree.sum(0, 5)) // 41
println(tree.sum(0, 4)) // 30
println(tree.sum(2, 2)) // 10
println(tree.sum(0, 0)) // 1
println(tree.sum(5, 5)) // 11
println(tree.sum(2, 5)) // 37
println(tree.sum(6, 6)) // 0

// https://stackoverflow.com/questions/64190332/fenwick-tree-vs-segment-tree
// https://www.geeksforgeeks.org/segment-tree-set-1-sum-of-given-range/
class SegmentTree(val array: IntArray) {
    // size:
    // 2*n-1, if n is power of 2 (n leave nodes + n-1 internal nodes)
    // 2*x-1, if n is not a power of 2, x is next power of 2 > n (there will be gaps)
    // height = log2(n) -> 2 * 2^(log2 n) - 1 .. e.g. height 6 -> 2 * 2^(log2 6) - 1 = 2 * 2^3 - 1 = 15 (log2 6 rounded up!!)
    // height = log2(n) -> 2 * 2^(log2 n) - 1 .. e.g. height 8 -> 2 * 2^(log2 8) - 1 = 2 * 2^3 - 1 = 15
    val segments = IntArray(2 * 2.0.pow(ceil(log2(array.size.toDouble()))).toInt() - 1)

    init {
        construct()
    }

    // O(N) runtime
    private fun construct() {
        constructRec(0, array.size - 1, 0)
    }

    private fun constructRec(arrayFromIndex: Int, arrayToIndex: Int, segmentIndex: Int): Int {
        if (arrayFromIndex == arrayToIndex) {
            segments[segmentIndex] = array[arrayFromIndex]
            return segments[segmentIndex]
        }

        val center = arrayFromIndex + (arrayToIndex - arrayFromIndex) / 2
        segments[segmentIndex] = constructRec(
            arrayFromIndex,
            center,
            segmentIndex * 2 + 1
        ) + constructRec(center + 1, arrayToIndex, segmentIndex * 2 + 2)

        return segments[segmentIndex]
    }

    // O(log N) runtime
    fun updateValue(index: Int, newValue: Int) {
        if (index >= array.size || index < 0) return
        val diff = newValue - array[index]

        updateValueRec(index, 0, array.size - 1, 0, diff)
    }

    private fun updateValueRec(
        arrayIndex: Int,
        low: Int,
        high: Int,
        segmentIndex: Int,
        diff: Int
    ) {
        segments[segmentIndex] += diff

        if (high == low) return

        val center = low + (high - low) / 2
        if (arrayIndex <= center) updateValueRec(
            arrayIndex,
            low,
            center,
            segmentIndex * 2 + 1,
            diff
        )
        else updateValueRec(arrayIndex, center + 1, high, segmentIndex * 2 + 2, diff)
    }

    // O(log N) runtime
    fun sum(fromIndex: Int, toIndex: Int): Int {
        if (fromIndex > toIndex || fromIndex < 0 || toIndex >= array.size) return 0
        if (fromIndex == toIndex) return array[fromIndex]

        return sumRec(fromIndex, toIndex, 0, array.size - 1, 0)
    }

    private fun sumRec(
        fromIndex: Int,
        toIndex: Int,
        arrayFromIndex: Int,
        arrayToIndex: Int,
        segmentIndex: Int
    ): Int {
        if (arrayFromIndex >= fromIndex && arrayToIndex <= toIndex) {
            // completely within -> return sum
            return segments[segmentIndex]
        } else if (!overlaps(fromIndex, toIndex, arrayFromIndex, arrayToIndex)) {
            // completely outside -> return 0
            return 0
        } else {
            // build sum rec. left + right
            val center = arrayFromIndex + (arrayToIndex - arrayFromIndex) / 2
            return sumRec(
                fromIndex,
                toIndex,
                arrayFromIndex,
                center,
                segmentIndex * 2 + 1
            ) + sumRec(fromIndex, toIndex, center + 1, arrayToIndex, segmentIndex * 2 + 2)
        }
    }

    private fun overlaps(
        fromIndex: Int,
        toIndex: Int,
        arrayFromIndex: Int,
        arrayToIndex: Int
    ): Boolean {
        return fromIndex <= arrayToIndex && arrayFromIndex <= toIndex
    }
}
