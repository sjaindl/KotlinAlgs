package kt.kotlinalgs.app.sorting

println("test")

val array1 = intArrayOf(3, 2, 1)
val array2 = intArrayOf(2000, 234, 21, 41, 1, 21, 75, 0, -4)

InsertionSort().sort(array1)
InsertionSort().sort(array2)
// runtime: O(N^2) worst + average case, O(N) best case
// space: O(1)
// stable

println(array1.map { it })
println(array2.map { it })

class InsertionSort {
    fun sort(array: IntArray) {
        if (array.size <= 1) return

        for (firstIndex in 1 until array.size) {
            val referenceValue = array[firstIndex] // 2
            var secondIndex = firstIndex // 0
            while (secondIndex > 0 && array[secondIndex - 1] > referenceValue) {
                secondIndex--
                array[secondIndex + 1] = array[secondIndex]
            }
            array[secondIndex] = referenceValue
        }
    }
}
