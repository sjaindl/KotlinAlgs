package kt.kotlinalgs.app.sorting

println("test")

val array = intArrayOf(2000, 234, 21, 41, 1, 21, 75, 0, -4)

BubbleSort().sort(array)
// runtime: O(N^2)
// space: O(1)
// unstable

println(array.map { it })

class BubbleSort {
    fun sort(array: IntArray) {
        if (array.size <= 1) return

        for (firstIndex in array.indices) {
            for (secondIndex in firstIndex until array.size) {
                if (array[secondIndex] < array[firstIndex]) {
                    exchange(array, firstIndex, secondIndex)
                }
            }
        }
    }

    private fun exchange(array: IntArray, firstIndex: Int, secondIndex: Int) {
        val temp = array[firstIndex]
        array[firstIndex] = array[secondIndex]
        array[secondIndex] = temp
    }
}
