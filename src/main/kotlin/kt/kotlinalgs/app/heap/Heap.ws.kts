package kt.kotlinalgs.app.heap

println("test")

val sorted = heapSort(intArrayOf(2, 1, 5, 123, 1, 4))

sorted.forEach {
    println(it)
}

fun heapSort(array: IntArray): IntArray {
    val heap = MinHeap()

    array.forEach {
        heap.insert(it)
    }

    heap.array.forEach {
        println(it)
    }

    val output = IntArray(array.size) { 0 }

    for (index in output.indices) {
        output[index] = heap.extractMin()
    }

    return output
}

class MinHeap(var capacity: Int = 7) {
    var array = Array(capacity) { 0 }
    private var size = 0

    fun insert(value: Int) {
        expand(size + 1)

        array[size] = value
        bubbleUp(size)
        size++
    }

    fun extractMin(): Int {
        if (size == 0) throw IllegalStateException("Cannot extract from empty heap")

        val value = array[0]
        array[0] = array[size - 1]
        array[size - 1] = 0

        size--
        bubbleDown(0)
        shrink(size)

        return value
    }

    private fun bubbleUp(from: Int) {
        var cur = from
        while (cur > 0) {
            val parent = cur / 2
            if (array[parent] <= array[cur]) return

            exchange(parent, cur)
            cur /= 2
        }
    }

    private fun bubbleDown(from: Int) {
        var cur = from
        while (cur * 2 + 1 < size) {
            val left = cur * 2 + 1
            val right = cur * 2 + 2

            val min =
                when {
                    right >= size -> left
                    array[left] < array[right] -> left
                    else -> right
                }

            if (array[cur] <= array[min]) return

            exchange(min, cur)
            cur = min
        }
    }

    private fun exchange(from: Int, to: Int) {
        val temp = array[from]
        array[from] = array[to]
        array[to] = temp
    }

    private fun expand(to: Int) {
        if (to == capacity) {
            val newArray = Array<Int>(capacity * 2) {
                0
            }
            for (index in 0 until capacity) {
                newArray[index] = array[index]
            }
            array = newArray
            capacity *= 2
        }
    }

    private fun shrink(to: Int) {
        if (to < capacity / 2) {
            capacity /= 2
            val newArray = Array<Int>(capacity) { 0 }
            for (index in 0 until capacity) {
                newArray[index] = array[index]
            }
            array = newArray
        }
    }
}