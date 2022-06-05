package kt.kotlinalgs.app.searching

println("test")

// array with elements from 1 to N incl., N <= 32.000
// print all dups
// memory av. 4KB = 4.096 Bytes = 32.384 bits (> 32.000)

printDups(
    intArrayOf(
        1, 2, 5, 32000, 23123, 7, 2, 17, 32000
    )
)

fun printDups(array: IntArray) {
    val bitVector = BitVector(32000 + 1) // excl. 0
    for (num in array) {
        if (bitVector.isBitSet(num)) {
            println("dup: $num")
        }
        bitVector.setBit(num)
    }
}

class BitVector(val size: Int) {
    val bits = IntArray(arraySize)

    val arraySize: Int
        get() = when {
            size % 32 == 0 -> size / 32
            else -> size / 32 + 1
        }

    fun isBitSet(bit: Int): Boolean {
        val index = bit / 32
        val elementIndex = bit % 32
        val mask = 1 shl elementIndex

        val bin = toBinary(bits[index])

        return bits[index] and mask != 0
    }

    // 31, index = 0, elIndex = 31
    // 32, index = 1, elIndex = 0
    // 33, index = 1, elIndex = 1
    fun setBit(bit: Int) {
        val index = bit / 32
        val elementIndex = bit % 32
        val mask = 1 shl elementIndex

        val bin = toBinary(bits[index])
        bits[index] = bits[index] or mask
        val bin2 = toBinary(bits[index])
    }

    fun toBinary(x: Int, len: Int = 32): String {
        return String.format(
            "%" + len + "s",
            Integer.toBinaryString(x)
        ).replace(" ".toRegex(), "0")
    }
}
