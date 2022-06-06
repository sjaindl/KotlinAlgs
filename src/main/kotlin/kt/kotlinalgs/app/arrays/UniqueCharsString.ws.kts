package kt.kotlinalgs.app.arrays

println("test1")

class UniqueCharsString {
    val charSet = 256 // extended ascii
    fun hasUniqueChars(str: String): Boolean {
        val bitVector = BitVector(charSet)

        str.forEach {
            if (bitVector.contains(it.toInt())) return false

            bitVector.set(it.toInt())
        }

        return true
    }
}

class BitVector(val size: Int) {
    private var array = Array<Int>(size / 32 + 1) //(if size / 32 != 0) 1 else 0
    {
        0
    }

    fun contains(bit: Int): Boolean {
        return get(bit) != 0
    }

    fun get(bit: Int): Int {
        val index = bit / 32
        val bitIndex = bit % 32
        val mask = 1 shl bitIndex

        println("get $mask at $index")

        return array[index] and mask
    }

    fun set(bit: Int) {
        val index = bit / 32
        val bitIndex = bit % 32
        val mask = 1 shl bitIndex

        println("set $mask at $index")

        array[index] = array[index] or mask
    }
}

val checker = UniqueCharsString()
println(checker.hasUniqueChars("abcd"))
println(checker.hasUniqueChars("abcdd"))