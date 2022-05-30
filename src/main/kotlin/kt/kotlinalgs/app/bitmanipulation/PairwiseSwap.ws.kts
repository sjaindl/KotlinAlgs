package kt.kotlinalgs.app.bitmanipulation

println("Test")

println(toBinary(pairwiseSwap(0b10101010)))
println(toBinary(pairwiseSwap(0b01010101)))

// Swap even with odd bits
//    10101010
// -> 01010101
// odd to even: (number shl 1) and 0b10101010 10101010 10101010 10101010
// = (number shl 1) and 0xaaaaaaaa
// even to odd: (number shr 1) and 0b01010101 01010101 01010101 01010101
// (number shr 1) and 0x11111111

fun pairwiseSwap(number: Int): Int {
    val oddMask = 0x55555555.toInt() // 0x5 = 0b0101
    val evenMask = 0xaaaaaaaa.toInt() // 0xa = 0b1010

    val oddToEven = (number shl 1) and evenMask
    val evenToOdd = (number ushr 1) and oddMask //unsigned/logical right shift!!
    
    return oddToEven or evenToOdd
}

fun toBinary(x: Int, len: Int = 32): String {
    return String.format(
        "%" + len + "s",
        Integer.toBinaryString(x)
    ).replace(" ".toRegex(), "0")
}
