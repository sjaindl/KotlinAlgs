package kt.kotlinalgs.app.arrays

println("test1")

class MyArrayList<T>(var capacity: Int = 8, var minTreshold: Double = 0.25) {

    private var array = Array<Any?>(capacity) { null }
    private var numElements = 0

    fun add(element: T) {
        ensureCapacity(numElements + 1)

        array[numElements] = element
        numElements++
    }

    fun get(index: Int): T {
        if (index < 0 || index >= numElements) throw IndexOutOfBoundsException()
        val element = array[index] as? T ?: throw IllegalStateException("Element not in array")
        return element
    }

    fun remove(element: T) {
        val index = array.indexOfFirst {
            it == element
        }

        removeAt(index)
    }

    fun removeAt(index: Int): Boolean {
        if (index < 0 || index >= numElements) return false

        array[index] = null
        for (backIndex in index until numElements) {
            array[backIndex] = array[backIndex + 1]
        }

        ensureCapacity(numElements - 1)
        numElements--

        return true
    }

    private fun ensureCapacity(neededCapacity: Int) {
        if (neededCapacity > capacity) {
            //expand
            println("expand to ${capacity * 2}")
            var newArray = Array(capacity * 2) {
                if (it < capacity) array[it]
                else null
            }

            array = newArray
            capacity = capacity * 2
        } else if (numElements < capacity * minTreshold && capacity > 8) {
            // shrink
            println("shrink to ${capacity / 2}")
            println(neededCapacity)
            var newArray = Array(capacity / 2) {
                array[it]
            }

            array = newArray
            capacity = capacity / 2
        }
    }
}

val list = MyArrayList<Int>()

for (i in 0 until 50)
    list.add(i)

for (i in 0 until 50) {
    val value = list.get(i)
    println(value)
}

for (i in 50 downTo 2) {
    val value = list.removeAt(i)
    println(value)
}

for (i in 0 until 2) {
    val value = list.get(i)
    println(value)
}
