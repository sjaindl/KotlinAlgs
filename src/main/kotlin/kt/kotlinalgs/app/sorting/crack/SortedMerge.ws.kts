package kt.kotlinalgs.app.sorting

val arrayA = intArrayOf(1, 4, 5, 7, 9, 10, 12, -1, -1, -1, -1, -1)
val arrayB = intArrayOf(0, 2, 5, 7, 13)

sortedMerge(arrayA, 7, arrayB)

println(arrayA.map { it })

// array A holds a large enough buffer at the end to hold B, insert B sorted in A
fun sortedMerge(arrayA: IntArray, arrayASortedLen: Int, arrayB: IntArray) {
    if (arrayA.size - arrayASortedLen < arrayB.size) return

    var indexA = arrayASortedLen - 1
    var indexB = arrayB.size - 1
    var insertIndex = arrayA.size - 1

    while (indexB >= 0) {
        if (indexA >= 0 && arrayA[indexA] > arrayB[indexB]){
            arrayA[insertIndex] = arrayA[indexA]
            indexA--
        } else {
            arrayA[insertIndex] = arrayB[indexB]
            indexB--
        }

        insertIndex--
    }
}
