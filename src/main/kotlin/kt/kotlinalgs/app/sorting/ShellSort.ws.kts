package kt.kotlinalgs.app.sorting

println("test")

val array1 = intArrayOf(3, 2, 1)
val array2 = intArrayOf(2000, 234, 21, 41, 1, 21, 75, 0, -4)

ShellSort().sort(array1)
ShellSort().sort(array2)
// runtime: O(N^2) worst + average case, O(N) best case
// space: O(1)
// unstable

println(array1.map { it })
println(array2.map { it })

// https://www.geeksforgeeks.org/shellsort/
// Optimization of Insertion sort with large distance values / arrays
class ShellSort {
    fun sort(array: IntArray) {
        if (array.size <= 1) return

        var gap = array.size / 2
        while (gap > 0) {
            for (first in gap until array.size) {
                var second = first
                while (second - gap >= 0 && array[second - gap] > array[second]) {
                    exchange(array, second - gap, second)

                    second--
                }
            }

            gap /= 2
        }
    }

    private fun exchange(array: IntArray, firstIndex: Int, secondIndex: Int) {
        val temp = array[firstIndex]
        array[firstIndex] = array[secondIndex]
        array[secondIndex] = temp
    }
}
