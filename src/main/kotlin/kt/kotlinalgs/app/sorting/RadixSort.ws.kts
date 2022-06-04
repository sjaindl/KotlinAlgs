package kt.kotlinalgs.app.sorting

import kotlin.math.pow

println("test")

val array1 = intArrayOf(2, 6, 4)
val array2 = intArrayOf(15, 13, 1, 10, 17, 0, 2, 2, 100)

RadixSort().sort(array1)
RadixSort().sort(array2)

// runtime: O(D*(N + B)), for finite ranges where N = array size, B = base (decimal=10), D = # of 10's places (=calls to counting sort subroutine)
// space:  O(N + K), aux arrays counts (K) + output (N)
// stable when 3rd pass of counting sort backwards

println(array1.map { it })
println(array2.map { it })

// https://www.geeksforgeeks.org/radix-sort/
// https://www.geeksforgeeks.org/counting-sort/
class RadixSort {
    fun sort(array: IntArray) { // (2,6,10)
        if (array.size <= 1) return

        var maxValue = array.max() ?: return //maxOrNull() ?: return
        var maxLen = 0 // 2
        while (maxValue > 0) { // 6
            maxLen++
            maxValue /= 10
        }

        // sort from LS to MS digits
        for (digitPlace in 1 until maxLen + 1) { // 1,2
            countingSort(array, digitPlace)
        }
    }

    private fun countingSort(array: IntArray, digitPlace: Int) { // 1
        val range = 10 // base 10, decimal system

        // e.g. 10^(1) = 10, 10^(2) = 100

        val exponent = range.toDouble().pow(digitPlace - 1).toInt() // 1

        // 1. pass: Calculate counts
        val counts = IntArray(range)
        // 0 1 2 3 4 5 6 7 8 9
        // 1 0 1 0 0 0 1 0 0 0
        array.forEach { // 2,6,10
            // (132 / 10 * (digitPlace - 1)) % 10
            // (132/10^0) % 10 = 2
            // (132/10^1) % 10 = 3
            // (132/10^2) % 10 = 1
            val index = (it / exponent) % range //2
            counts[index]++
        }

        // 2. pass: Cumulate counts
        // 0 1 2 3 4 5 6 7 8 9 index
        // 1 0 1 0 0 0 1 0 0 0 ori
        // 1 1 2 2 2 2 3 3 3 3 cum.
        for (index in 1 until counts.size) {
            counts[index] += counts[index - 1]
        }

        // 3. pass: put back in original array (count = index!)
        // stable, if we iterate backwards (because we are decreasing indices):
        // https://stackoverflow.com/questions/2572195/how-is-counting-sort-a-stable-sort
        val output = IntArray(array.size) // (10,2,6)

        // array: 2,6,10
        // counts:
        // 0 1 2 3 4 5 6 7 8 9 index
        // 1 1 2 2 2 2 3 3 3 3 orig. cum.
        // 0 1 1 2 2 2 2 3 3 3 decr.
        for (index in array.size - 1 downTo 0) { // 2,1,0
            val value = (array[index] / exponent) % range // 2
            val finalIndex = counts[value] - 1 //1
            output[finalIndex] = array[index]
            counts[value]--
        }

        output.forEachIndexed { index, value ->
            array[index] = value
        }
    }
}
