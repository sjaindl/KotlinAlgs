package kt.kotlinalgs.app.bitmanipulation

println("Test")

println(binaryToString(1.01))
println(binaryToString(1.0))
println(binaryToString(0.75))
println(binaryToString(0.5))
println(binaryToString(0.25))
println(binaryToString(0.125))
println(binaryToString(0.5 / 8))
println(binaryToString(0.2))

fun binaryToString(number: Double): String {
    // 1.0  = 1
    // 0.5  = 0.1
    // 0.25 = 0.01

    /*
    sb = StringBuilder()
    while number > 0
        val digit = if ((number & 1) == 1) 1 else 0
        number -= 1
        number *= 2
     */

    if (number < 0 || number > 1) return "ERROR"
    if (number == 1.0) return "1"

    val sb = StringBuilder()
    sb.append("0.")

    var curNumber = number * 2
    while (curNumber > 0) {
        val isOne = (curNumber.toInt() and 1) == 1
        val digit = if (isOne) 1 else 0
        if (isOne) curNumber -= 1
        curNumber *= 2

        sb.append(digit)
        if (sb.length > 32) return "ERROR" // can't accurately represent num
    }

    return sb.toString()
}