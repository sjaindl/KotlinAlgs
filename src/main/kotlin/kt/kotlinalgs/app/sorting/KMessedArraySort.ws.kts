// https://www.pramp.com/challenge/XdMZJgZoAnFXqwjJwnBZ
// https://www.bezkoder.com/kotlin-priority-queue/

package kt.kotlinalgs.app.sorting

import java.util.*

println("test")

val sorted = sortKMessesArray(
    intArrayOf(1, 4, 5, 2, 3, 7, 8, 6, 10, 9), 2
)

val minComparator: Comparator<Int> = compareBy<Int> {
    it
}//.reversed()

val maxComparator: Comparator<Int> = compareBy<Int> {
    -it
}//.reversed()

val minSorted = intArrayOf(1, 4, 5, 2, 3, 7, 8, 6, 10, 9).sortedWith(minComparator)

minSorted.forEach {
    println(it)
}

val maxSorted = intArrayOf(1, 4, 5, 2, 3, 7, 8, 6, 10, 9).sortedWith(maxComparator)

maxSorted.forEach {
    println(it)
}

sorted.forEach {
    println(it)
}

class MinIntComparator : Comparator<Int> {
    override fun compare(p0: Int, p1: Int): Int {
        return p0 - p1
    }
}

class MaxIntComparator : Comparator<Int> {
    override fun compare(p0: Int, p1: Int): Int {
        return p0 - p1
    }
}

fun sortKMessesArray(array: IntArray, k: Int): IntArray {
    //val array = array2.reversedArray()
    if (array.isEmpty() || k <= 0 || k >= array.size) return array

    val output = IntArray(array.size) { 0 }
    var outputIndex = 0

    val comparator: Comparator<Int> = compareBy<Int> {
        it
    }.reversed()

    //val pq = PriorityQueue<Int>(8, comparator)
    //val pq = PriorityQueue<Int>()
    //val pq = PriorityQueue<Int>(11, MaxIntComparator().reversed())
    val pq = PriorityQueue<Int>(11, MinIntComparator())

    for (num in 0 until k + 1) {
        pq.add(array.get(num))
    }


    for (index in k + 1 until array.size) {
        val min = pq.poll()
        output[outputIndex] = min
        outputIndex++
        pq.add(array[index])
    }

    while (!pq.isEmpty()) {
        val min = pq.poll()
        output[outputIndex] = min
        outputIndex++
    }

    return output
}