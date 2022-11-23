var line = 0
var args: Array<String> = arrayOf()

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

// https://codingcompetitions.withgoogle.com/kickstart/round/00000000008cb1b6/0000000000c47e79

// O(L log L) time, O(L) space (dp)
fun main(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val numLillies = readLine()?.toIntOrNull() ?: return

        val coinsNeeded = lillies(numLillies)

        println("Case #$testCase: $coinsNeeded")
    }
}

fun lillies(numLillies: Int): Int { // 5
    if (numLillies < 2) return numLillies

    val dp = IntArray(numLillies + 1) { Int.MAX_VALUE } // coins needed for lillies
    dp[0] = 0
    dp[1] = 1
    // [0, 1, 2, 3, 6, x]

    for (lilly in 2 until numLillies + 1) { // 3
        // case 1: Use 1 coin for 1 lilly
        dp[lilly] = Math.min(dp[lilly], dp[lilly - 1] + 1)
        // case 2: Use 1 x 4 coins and k x 2 coins
        // we update all the following indices
        var multiple = 2 * lilly // 4
        while (multiple < numLillies + 1) {
            dp[multiple] = Math.min(dp[multiple], dp[lilly] + 4 + 2 * (multiple / lilly - 1))
            multiple += lilly
        }
    }

    return dp[numLillies]
}

// O(3^L) time in theory, O(L) space (rec.)
fun mainSlow(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val numLillies = readLine()?.toIntOrNull() ?: return

        val coinsNeeded = lilliesRec(0, numLillies, numLillies, null, 0)

        println("Case #$testCase: $coinsNeeded")
    }
}

/*
If you toss one coin into the well, the well will toss out one lily into your basket.
If you toss four coins at once into the well, the well will take note of how many lilies it has tossed out into your basket so far.
If you toss two coins at once into the well, the well will toss out as many lilies into your basket as it had last taken note of.
 */
fun lilliesRec(coinsUsed: Int, numLillies: Int, lilliesLeft: Int, lastCoinUsed: Int?, lookedUp: Int): Int {
    if (lilliesLeft < 0) return Int.MAX_VALUE
    if (lilliesLeft == 0) return coinsUsed

    // 1 coin
    val oneCoin =
        lilliesRec(coinsUsed = coinsUsed + 1, numLillies, lilliesLeft = lilliesLeft - 1, lastCoinUsed = 1, lookedUp)

    // First coin to use
    if (lastCoinUsed == null) {
        // 2, 4 coins don't make sense. Use 1 coin.
        return oneCoin
    }

    var best = oneCoin

    if (lookedUp > 0) {
        // 2 coins possible
        val twoCoins = lilliesRec(coinsUsed = coinsUsed + 2,
            numLillies,
            lilliesLeft = lilliesLeft - lookedUp,
            lastCoinUsed = 1,
            lookedUp)
        best = Math.min(best, twoCoins)
    }

    // 4 coins
    if (lastCoinUsed != 4) {
        val fourCoins =
            lilliesRec(coinsUsed = coinsUsed + 4,
                numLillies,
                lilliesLeft = lilliesLeft,
                lastCoinUsed = 4,
                numLillies - lilliesLeft)
        best = Math.min(best, fourCoins)
    }

    return best
}

main(arrayOf("2", "5", "20"))
mainSlow(arrayOf("2", "5", "20"))
