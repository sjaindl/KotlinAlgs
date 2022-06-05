package kt.kotlinalgs.app.searching

println("test")

val array1 = intArrayOf(3, 2, 1)
val array2 = intArrayOf(2000, 234, 21, 41, 1, 21, 75, 0, -4)

println(QuickSelect().select(intArrayOf(3, 2, 1), 1))
println(QuickSelect().select(intArrayOf(3, 2, 1), 2))
println(QuickSelect().select(intArrayOf(3, 2, 1), 3))

println(QuickSelect().select(intArrayOf(3, 2, 1), 2))

// runtime: O(N) best + average, O(N^2) worst case
// space: O(log N) best + average, O(N) worst case
// unstable

class QuickSelect {
    fun select(array: IntArray, k: Int): Int? {
        if (array.isEmpty() || k < 1 || k > array.size) return null
        if (k == 1 && array.size == 1) return array[0]

        // Improvement: Randomize array (avoid worst case when array is sorted)
        return select(array, k - 1, 0, array.lastIndex)
    }

    private fun select(array: IntArray, k: Int, low: Int, high: Int): Int? {
        if (low == high) {
            return if (low == k) array[k]
            else null
        }

        val pivot = partition(array, low, high)

        return when {
            pivot == k -> array[k]
            pivot < k -> select(array, k, pivot + 1, high)
            else -> select(array, k, low, pivot - 1)
        }
    }

    private fun partition(array: IntArray, low: Int, high: Int): Int {
        val pivotIndex = low + (high - low) / 2
        val pivot = array[pivotIndex]

        exchange(array, low, pivotIndex)
        var left = low + 1
        var right = high

        while (left <= right) {
            while (left < array.size && array[left] < pivot) left++
            while (right >= 0 && array[right] > pivot) right--

            if (left <= right) {
                exchange(array, left, right)
                left++
                right--
            }
        }

        exchange(array, low, right)

        return right
    }

    private fun exchange(array: IntArray, firstIndex: Int, secondIndex: Int) {
        val temp = array[firstIndex]
        array[firstIndex] = array[secondIndex]
        array[secondIndex] = temp
    }
}
