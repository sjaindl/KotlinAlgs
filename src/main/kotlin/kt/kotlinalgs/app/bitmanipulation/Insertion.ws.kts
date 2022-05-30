package kt.kotlinalgs.app.bitmanipulation

println("Test")

println(
    updateBits(
        0b10000000000, //1024
        0b10011, //19
        6,
        2
    )
)

println("-1: ${toBinary(-1)}")
println("clear bit: ${clearBit(0b11111, 3).toString(2)}")
println("leadingOneMask: ${toBinary(leadingOneMask(6))}")
println("trailingOneMask: ${toBinary(trailingOneMask(2))}")
println("leadingAndTrailingOneMask: ${toBinary(leadingAndTrailingOneMask(6, 2))}")
println("updateBit: ${toBinary(updateBit(0b10000, 2, isOne = true))}")


println(toBinary(-1))

fun clearBit(num: Int, bit: Int): Int {
    // 11111, 3 -> 10111
    val mask = (1 shl bit).inv()
    return num and mask
}

fun leadingOneMask(by: Int): Int {
    // 111000
    return (-1 shl by)
    //return (Int.MAX_VALUE shr by shl (by + 1))
}

fun trailingOneMask(by: Int): Int {
    // 000111
    return (1 shl (by + 1)) - 1
}

fun leadingAndTrailingOneMask(lead: Int, trail: Int): Int {
    // 111000111
    val lead = leadingOneMask(lead)
    val trail = trailingOneMask(trail)
    return lead or trail
}

fun updateBit(num: Int, pos: Int, isOne: Boolean): Int {
    // 1110x0111
    val mask = leadingAndTrailingOneMask(pos + 1, pos - 1)
    val withClearedBit = num and mask

    val bit = if (isOne) 1 else 0
    val oneMask = bit shl pos
    return withClearedBit or oneMask
}

fun updateBits(of: Int, with: Int, startBit: Int, endBit: Int): Int {
    // of = 10000000000
    // with = 10011
    // start - end: 6-2
    // returns 10001001100

    /*
    https://www.programiz.com/kotlin-programming/bitwise

    1. clear bits start - end in of
        of = of & 1110000111..
        frontMask = (-1 < start + 1)  ... 1110000000
        endMask = (1 < start) - 1  ...    0000000011
        mask = frontMask | endMask ... 1110000000 | 0000000011 = 1110000011
    2. insert with in of (shifted left)
        of = of & (with << endBit)
     */

    val frontMask = (-1) shl (startBit + 1)
    //val frontMask = (Int.MAX_VALUE) shr (startBit + 1) shl (startBit + 1)
    val endMask = (1 shl endBit) - 1
    val combinedMask = frontMask or endMask
    val stripped = of and combinedMask

    //println("frontMask=${frontMask.toString(2)}")
    println("frontMask=   ${toBinary(frontMask)}")
    println("endMask=     ${toBinary(endMask)}")
    println("combinedMask=${toBinary(combinedMask)}")
    println("stripped=    ${toBinary(stripped)}")

    val withMask = with shl endBit

    println("withMask=    ${toBinary(withMask)}")

    val result = stripped or withMask
    println("result=      ${toBinary(result)}: $result")

    return result
}

fun toBinary(x: Int, len: Int = 32): String {
    return String.format(
        "%" + len + "s",
        Integer.toBinaryString(x)
    ).replace(" ".toRegex(), "0")
}
