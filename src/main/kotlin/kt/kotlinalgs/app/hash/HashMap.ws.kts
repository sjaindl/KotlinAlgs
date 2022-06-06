package kt.kotlinalgs.app.hash

println("test")
//https://anmolsehgal.medium.com/java-hashmap-internal-implementation-21597e1efec3

class MyHashMap<K, V>(
    var size: Int = 16,
    val maxTreshold: Double = 0.75,
    val minTreshold: Double = 0.25
) {
    private var numElements = 0

    private data class Node<K, V>(val key: K, var value: V)

    private var array = Array(size) { mutableListOf<Node<K, V>>() }

    public fun get(key: K): V? {
        return getNode(key)?.value
    }

    private fun getNode(key: K): Node<K, V>? {
        val index = key.hashCode() % size
        //println("index" + index)
        val list = array[index]

        return list.firstOrNull { (it.key == key) }
    }

    public fun put(key: K, value: V): V? {
        checkResize()

        val index = key.hashCode() % size
        var list = array[index]

        val oldIndex = list.indexOfFirst {
            it.key == key
        }

        if (oldIndex != -1) {
            val old = list[oldIndex].value
            list[oldIndex].value = value
            return old
        }

        numElements++

        list.add(0, Node(key, value)) //prepend
        return null
    }

    public fun contains(key: K): Boolean {
        return get(key) != null
    }

    public fun remove(key: K): Boolean {
        val index = key.hashCode() % size
        val list = array[index]

        val removed = list.removeIf {
            it.key == key
        }

        numElements--
        checkResize()

        return removed
    }

    private fun checkResize() {
        if (numElements >= size * maxTreshold) {
            size *= 2
            var newArray = Array(size) { mutableListOf<Node<K, V>>() }
            transfer(newArray)
        } else if (numElements <= size * minTreshold) {
            size = Math.max(2, size / 2)
            var newArray = Array(size) { mutableListOf<Node<K, V>>() }
            transfer(newArray)
        }
    }


    private fun transfer(newArray: Array<MutableList<Node<K, V>>>) {
        array.forEach { list ->
            list.forEach {
                val index = it.key.hashCode() % size
                val list = newArray[index]
                list.add(0, it)
            }
        }

        array = newArray
    }

}

println("map")
val map = MyHashMap<String, List<String>>()
map.put("1", listOf("2", "3"))
map.put("2", listOf("3", "4"))
map.put("1", listOf("x", "y"))

for (i in 0 until 1000) {
    map.put("$i", listOf("$i", "${i + 1}"))
}

map.contains("1")
map.contains("2")
map.contains("3")

map.remove("2")

map.get("1")
map.get("2")
map.get("299")
