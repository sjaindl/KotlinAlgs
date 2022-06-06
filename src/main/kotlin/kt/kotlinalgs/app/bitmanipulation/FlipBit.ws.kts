package kt.kotlinalgs.app.bitmanipulation

println("Test")

println(flipBit(0b11011101111))
println(flipBit(Integer.MAX_VALUE))
println(flipBit(0))
println(flipBit(0b11111101111))
println(flipBit(0b111111001111))

fun flipBit(number: Int): Int {
    // can flip 1 bit, return max len sequence of 1's possible
    var onlyOnes: Int? = null // 5
    var withOneZero: Int? = null // 0
    var max = 1

    // 0b11011101111
    for (bit in 0 until 32) { //8
        val isOne = number and (1 shl bit) != 0

        if (isOne) {
            if (onlyOnes != null && withOneZero == null) withOneZero = bit
            if (onlyOnes == null) onlyOnes = bit
        } else {
            if (withOneZero != null) {
                max = maxOf(max, bit - withOneZero) // 5
            } else if (onlyOnes != null) {
                max = maxOf(max, bit - onlyOnes) // 4
            }

            withOneZero = onlyOnes
            onlyOnes = null
        }
    }

    if (onlyOnes != null) max = Math.max(max, 32 - onlyOnes)
    if (withOneZero != null) max = Math.max(max, 32 - withOneZero)

    return max
}