package kt.kotlinalgs.app.searching

println("test")

println(sortedSearch(intArrayOf(2, 2, 2, 3, 4, 2), 3))
println(sortedSearch(intArrayOf(2, 2, 2, 3, 4, 2), 4))
println(sortedSearch(intArrayOf(2, 2, 2, 3, 4, 2), 2))

println(sortedSearch(intArrayOf(15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14), 16))
println(sortedSearch(intArrayOf(15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14), 10))
println(sortedSearch(intArrayOf(15, 16, 19, 20, 25, 1, 3, 4, 5, 7, 10, 14), 8))

// runtime O(log N) if no duplicates, O(N) if many dups
// O(log N) space due to rec. call stack
fun sortedSearch(array: IntArray, searched: Int): Int {
    if (array.isEmpty()) return -1

    return sortedSearchRec(array, searched, 0, array.size - 1)
}

fun sortedSearchRec(array: IntArray, searched: Int, low: Int, high: Int): Int {
    if (high < low) return -1
    val center = low + (high - low) / 2
    if (array[center] == searched) return center

    val leftIsSorted = array[center] > array[low] // TODO check >, >=
    val rightIsSorted = array[high] > array[center] // TODO check >, >=

    if (leftIsSorted) {
        if (searched >= array[low] && searched < array[center]) {
            return sortedSearchRec(array, searched, low, center - 1)
        } else {
            return sortedSearchRec(array, searched, center + 1, high)
        }
    } else if (rightIsSorted) {
        if (searched > array[center] && searched <= array[high]) {
            return sortedSearchRec(array, searched, center + 1, high)
        } else {
            return sortedSearchRec(array, searched, low, center - 1)
        }
    } else if (array[low] == array[center]) {
        if (array[high] != array[center]) {
            return sortedSearchRec(array, searched, center + 1, high)
        } else {
            val leftIndex = sortedSearchRec(array, searched, low, center - 1)
            if (leftIndex != -1) return leftIndex
            return sortedSearchRec(array, searched, center + 1, high)
        }
    } else { // array[high] == array[center]
        if (array[low] != array[center]) {
            return sortedSearchRec(array, searched, low, center - 1)
        } else {
            val leftIndex = sortedSearchRec(array, searched, low, center - 1)
            if (leftIndex != -1) return leftIndex
            return sortedSearchRec(array, searched, center + 1, high)
        }
    }
}
