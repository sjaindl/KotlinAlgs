package kt.kotlinalgs.app.sorting

val array1 = intArrayOf(5, 8, 6, 2, 3, 4, 6)
val array2 = intArrayOf(2, 3, 4, 5, 6, 6, 8)
val array3 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 1)
val peakValueSorter = PeakValueSorter()
peakValueSorter.sortNotWorking(array1)
peakValueSorter.sortNotWorking(array2)
peakValueSorter.sortNotWorking(array3)

array1.map { println(it) }
array2.map { println(it) }
array3.map { println(it) }


val array4 = intArrayOf(5, 8, 6, 2, 3, 4, 6)
val array5 = intArrayOf(2, 3, 4, 5, 6, 6, 8)
val array6 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 1)
peakValueSorter.sortWorking(array4)
peakValueSorter.sortWorking(array5)
peakValueSorter.sortWorking(array6)

array4.map { println(it) }
array5.map { println(it) }
array6.map { println(it) }

/*
(5,8,6,2,3,4,6)
-> sorted:
(2,3,4,5,6,6,8)
-> interweave front/back:
(2,8,3,6,4,6,5) ..  O(N log N) runtime + O(1) space with e.g. QuickSort

O(N) runtime - cut out sorting step!
greedy approach, change if not matching at index
(2,3,4,5,6,6,8) ->
0: (2,3,4,5,6,6,8) OK (start with valley)
1: (2,4,3,5,6,6,8) Peak -(change with index + 1, if not matching)
2: (2,4,3,5,6,6,8) Valley - OK
3: (2,4,3,6,5,6,8) Peak - change with index + 1
4: (2,4,3,6,5,6,8) Valley - OK
5: (2,4,3,6,5,8,6) Peak - change with index + 1
6: (2,4,3,6,5,8,6) Valley - OK
 */

class PeakValueSorter {
    fun sortNotWorking(array: IntArray) {
        if (array.size < 2) return

        var isValley = true
        for (index in 1 until array.size - 1 step 2) {
            if (!isValid(array, isValley, index)) exchange(array, index, index + 1)
            isValley = !isValley
        }
    }

    private fun isValid(array: IntArray, isValley: Boolean, index: Int): Boolean {
        if (index == 0) {
            if (isValley && array[index + 1] < array[index]) return false
            if (!isValley && array[index + 1] > array[index]) return false
            return true
        } else {
            if (isValley) {
                return array[index - 1] > array[index] && array[index + 1] > array[index]
            } else {
                return array[index - 1] < array[index] && array[index + 1] < array[index]
            }
        }
    }

    private fun exchange(array: IntArray, index1: Int, index2: Int) {
        val temp = array[index1]
        array[index1] = array[index2]
        array[index2] = temp
    }

    fun sortWorking(array: IntArray) {
        if (array.size < 2) return

        for (index in 1 until array.size - 1 step 2) {
            val biggestIndex = biggest(array, index)
            if (biggestIndex != index) exchange(array, index, biggestIndex)
        }
    }

    private fun biggest(array: IntArray, index: Int): Int {
        val prev = array[index - 1]
        val cur = array[index]
        val next = if (index + 1 >= array.size) Int.MIN_VALUE else array[index + 1]

        val max = maxOf(maxOf(prev, cur), next)
        if (prev == max) return index - 1
        if (cur == max) return index
        return index + 1
    }
}
