package kt.kotlinalgs.app.sorting

println("test")

/*
            array: (2, 6, 4)
                                0 1 2 3 4 5 6 7 8 9 10
            counts bei max 10: (0,0,1,0,1,0,1,0,0,0,0 )
            accumulated:       (0,0,1,1,2,2,3,3,3,3,3 )
            adj.:              (0,0,0,1,2,2,3,3,3,3,3 )

            output,
                for value array[0] = 2:
                    index = counts[2] - 1 = 1 - 1 = 0
                    output[index] = value -> output[0] = 2
                    counts[value]-= 1 -> counts[2]-- = 0
         */
val array1 = intArrayOf(2, 6, 4)
val array2 = intArrayOf(15, 13, 1, 10, 17, 0, 2, 2, 7)
val array3 = array1.clone()
val array4 = array2.clone()
val array5 = intArrayOf(-2, 0, 2)

CountingSort().sort(array1, 10)
CountingSort().sort(array2, 20)

CountingSort().sortWithFlexMinMax(array3)
CountingSort().sortWithFlexMinMax(array4)
CountingSort().sortWithFlexMinMax(array5)
// runtime: O(N + K), for finite ranges where N = array size & K = value range (max - min + 1)
// space:  O(N + K), aux arrays counts (K) + output (N)
// stable when 3rd pass backwards

println(array1.map { it })
println(array2.map { it })

println(array3.map { it })
println(array4.map { it })
println(array5.map { it })

// https://www.geeksforgeeks.org/counting-sort/
class CountingSort {
    fun sort(array: IntArray, maxValue: Int) {
        if (array.size <= 1) return

        // 1. pass: Calculate counts
        val counts = IntArray(maxValue + 1)
        array.forEach {
            counts[it] += 1
        }

        // 2. pass: Cumulate counts
        for (index in 1 until counts.size) {
            counts[index] += counts[index - 1]
        }

        // 3. pass: put back in original array (count = index!)
        // stable, if we iterate backwards (because we are decreasing indices):
        // https://stackoverflow.com/questions/2572195/how-is-counting-sort-a-stable-sort
        val output = IntArray(array.size)
        for (index in array.size - 1 downTo 0) {
            val value = array[index]
            val finalPos = counts[value] - 1
            output[finalPos] = value
            counts[value]--
        }

        output.forEachIndexed { index, value ->
            array[index] = value
        }
    }

    fun sortWithFlexMinMax(array: IntArray) {
        if (array.size <= 1) return

        val min = array.min() ?: return //minOrNull() ?: return
        val max = array.max() ?: return //maxOrNull() ?: return
        val range = max - min + 1

        // 1. pass: Calculate counts
        val counts = IntArray(range)
        array.forEach {
            counts[it - min] += 1
        }

        // 2. pass: Cumulate counts
        for (index in 1 until counts.size) {
            counts[index] += counts[index - 1]
        }

        // 3. pass: put back in original array (count = index!)
        // stable, if we iterate backwards (because we are decreasing indices):
        // https://stackoverflow.com/questions/2572195/how-is-counting-sort-a-stable-sort
        val output = IntArray(array.size)
        for (index in array.size - 1 downTo 0) {
            val value = array[index]
            val finalPos = counts[value - min] - 1
            output[finalPos] = value
            counts[value - min]--
        }

        output.forEachIndexed { index, value ->
            array[index] = value
        }
    }
}
