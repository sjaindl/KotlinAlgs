package kt.kotlinalgs.app.sorting

println("test")

val array = intArrayOf(2000, 234, 21, 41, 1, 21, 75, 0, -4)

SelectionSort().sort(array)
// runtime: O(N^2)
// space: O(1)
// unstable

println(array.map { it })

class SelectionSort {
    fun sort(array: IntArray) {
        if (array.size <= 1) return

        for (firstIndex in array.indices) {
            var min = firstIndex
            for (secondIndex in firstIndex until array.size) {
                if (array[secondIndex] < array[firstIndex]) min = secondIndex
            }
            if (min != firstIndex) exchange(array, firstIndex, min)
        }
    }

    private fun exchange(array: IntArray, firstIndex: Int, secondIndex: Int) {
        val temp = array[firstIndex]
        array[firstIndex] = array[secondIndex]
        array[secondIndex] = temp
    }
}
