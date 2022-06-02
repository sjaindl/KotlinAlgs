package kt.kotlinalgs.app.sorting

println("test")

val array1 = intArrayOf(3, 2, 1)
val array2 = intArrayOf(2000, 234, 21, 41, 1, 21, 75, 0, -4)

QuickSort().sort(array1)
QuickSort().sort(array2)
// runtime: O(N log N) best + average, O(N^2) worst case
// space: O(log N) best + average, O(N) worst case
// unstable

println(array1.map { it })
println(array2.map { it })

class QuickSort {
    fun sort(array: IntArray) {
        if (array.size <= 1) return

        // Improvement: Randomize array (avoid worst case when array is sorted)
        quickSort(array, 0, array.lastIndex)
    }

    // [3, 2, 1], 0, 2
    private fun quickSort(array: IntArray, low: Int, high: Int) {
        if (high <= low) return

        val pivot = partition(array, low, high)
        quickSort(array, low, pivot - 1)
        quickSort(array, pivot + 1, high)
    }

    // [3, 2, 1], 0, 2
    private fun partition(array: IntArray, low: Int, high: Int): Int {
        val pivotIndex = low + (high - low) / 2 // improvement: median of 3
        val pivot = array[pivotIndex] // 2
        exchange(array, low, pivotIndex) // [2, 1, 3]

        var left = low + 1 // 2
        var right = high // 2

        // pivot = 3
        // [4,5,3,1,2] -> [3,5,4,1,2] --> [3,5,1,4,2]
        // [2,1,10,3,4] -> [10,1,2,3,4]
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
