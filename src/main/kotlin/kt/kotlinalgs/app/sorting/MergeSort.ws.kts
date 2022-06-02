package kt.kotlinalgs.app.sorting

println("test")

val array1 = intArrayOf(3, 2, 4, 1)
val array2 = intArrayOf(2000, 234, 21, 41, 1, 21, 75, 0, -4)

MergeSort().sort(array1)
MergeSort().sort(array2)

// runtime: O(N log N)
// space: O(N)
// stable

println(array1.map { it })
println(array2.map { it })

class MergeSort {
    fun sort(array: IntArray) {
        if (array.size <= 1) return

        mergeSort(array, 0, array.lastIndex)
    }

    private fun mergeSort(array: IntArray, low: Int, high: Int) {
        if (high <= low) return

        val center = low + (high - low) / 2
        mergeSort(array, low, center)
        mergeSort(array, center + 1, high)
        merge(array, low, center, high)
    }

    private fun merge(array: IntArray, low: Int, center: Int, high: Int) {
        val aux = IntArray(high - low + 1) {
            array[low + it]
        }

        var leftAux = 0
        var rightAux = center + 1 - low
        val centerAux = center - low

        // println(array.map { it })
        // println("from $low to $high")
        // println(aux.map { it })

        var curArrayIndex = low

        while (curArrayIndex <= high) {
            if (leftAux > centerAux) array[curArrayIndex] = aux[rightAux++] // left exhausted
            else if (rightAux >= aux.size) array[curArrayIndex] = aux[leftAux++] // right exhausted
            else if (aux[leftAux] < aux[rightAux]) array[curArrayIndex] = aux[leftAux++] // left smaller
            else array[curArrayIndex] = aux[rightAux++] // right smaller, or equal

            curArrayIndex++
        }
    }
}
