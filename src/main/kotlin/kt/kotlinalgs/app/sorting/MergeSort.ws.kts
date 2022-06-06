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
        val aux: IntArray = array.copyOf()

        mergeSort(array, 0, array.lastIndex, aux)
    }

    private fun mergeSort(array: IntArray, low: Int, high: Int, aux: IntArray) {
        if (high <= low) return

        val center = low + (high - low) / 2
        mergeSort(array, low, center, aux)
        mergeSort(array, center + 1, high, aux)
        merge(array, low, center, high, aux)
    }

    private fun merge(array: IntArray, low: Int, center: Int, high: Int, aux: IntArray) {
        for (index in low until high + 1) aux[index] = array[index]

        /*
        // alternative: new aux array with diff. indices:
        val aux = IntArray(high - low + 1) {
            array[low + it]
        }

        var leftAux = 0
        var rightAux = center + 1 - low
        val centerAux = center - low

        var curArrayIndex = low

        while (curArrayIndex <= high) {
            if (leftAux > centerAux) array[curArrayIndex] = aux[rightAux++] // left exhausted
            else if (rightAux >= aux.size) array[curArrayIndex] = aux[leftAux++] // right exhausted
            else if (aux[leftAux] < aux[rightAux]) array[curArrayIndex] = aux[leftAux++] // left smaller
            else array[curArrayIndex] = aux[rightAux++] // right smaller, or equal

            curArrayIndex++
        }

         */

        // println(array.map { it })
        // println("from $low to $high")
        // println(aux.map { it })

        var left = low
        var right = center + 1

        var curIndex = low

        // while (left < center || right <= high) {
        while (curIndex <= high) {
            if (left > center) array[curIndex++] = aux[right++] // left exhausted
            else if (right > high) array[curIndex++] = aux[left++] // right exhausted
            else if (aux[left] < aux[right]) array[curIndex++] = aux[left++] // left smaller
            else array[curIndex++] = aux[right++] // right smaller, or equal
        }
    }
}
