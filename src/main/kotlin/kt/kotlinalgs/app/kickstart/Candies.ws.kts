
var line = 0
var args: Array<String> = arrayOf()

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

fun main(mainArgs: Array<String>) {
    args = mainArgs
    // Read number of testcases from standard input.
    val testCases = readLine()!!.toInt()

    for (testCase in 1 until testCases + 1) {
        val (numBags, numKids) = readLine()!!.split(" ").map { (it.toInt()) }

        val candyBags = readLine()!!.split(" ").map { it.toInt() }
        val sumCandies = candyBags.reduce { acc, candiesInBag -> acc + candiesInBag }

        val remainingCandies = sumCandies % numKids

        println("Case $testCase: $remainingCandies")
    }
}
