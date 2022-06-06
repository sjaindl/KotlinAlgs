package kt.kotlinalgs.app.searching

println("test")

val array = IntArray(1000) {
    if (it == 900) 1001 // missing is 900!
    else it
}

println(Solution().missingInt(array))

/*
follow up: 1 bil. ints, 10 MB memory
1 billion ints
10MB = ~10.000KB = ~10.000.000 bytes = ~ 80 million bits .. ~ < 2^23 bytes -> 2^23 / 4 (int = 4 byte for count!) = ~ 2^21
rangeSize >= 2^32 / 2^21 -> rangeSize >= 2^10 = 1024

ranges = min ranges of around 1024 elements

1. build rangeCount -> should be 1024!!
MutableMap<Int, Int>, mapping from rangeId to count, where rangeId = num / 1024
for each num: add or incr. range count
2. iterate over ranges from 0 to x, find first missing or with count < 1024
    now we know missing int is in that range
3. BooleanArray of size 1024 .. mapping from range start to end
    iterate again over input, just check ints within range and set to true
4. iterate over BooleanArray and return first element, where array[num-1] == false
 */


// assume file with 4 billion ints, return first missing one
// 1GB memory = ~1.000MB = ~1.000.000KB = ~1.000.000.000 bytes =  ~8.000.000.000 bits
class Solution {
    fun missingInt(array: IntArray): Int {
        val bitVector = BitVector(array.size + 1)
        array.forEach {
            bitVector.setBit(it)
        }

        for (num in array.indices) {
            if (!bitVector.isBitSet(num)) return num
        }

        return array.size
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

        // val bin = toBinary(bits[index])

        // != 0 instead of > 0, as bit 32 is sign bit, which is < 0!!
        return bits[index] and mask != 0
    }

    // 31, index = 0, elIndex = 31
    // 32, index = 1, elIndex = 0
    // 33, index = 1, elIndex = 1
    fun setBit(bit: Int) {
        val index = bit / 32
        val elementIndex = bit % 32
        val mask = 1 shl elementIndex

        // val bin = toBinary(bits[index])
        bits[index] = bits[index] or mask
        // val bin2 = toBinary(bits[index])
    }

    fun toBinary(x: Int, len: Int = 32): String {
        return String.format(
            "%" + len + "s",
            Integer.toBinaryString(x)
        ).replace(" ".toRegex(), "0")
    }
}
