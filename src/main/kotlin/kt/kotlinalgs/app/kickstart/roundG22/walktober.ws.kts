var line = 0
var args: Array<String> = arrayOf()

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

fun main(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    // https://codingcompetitions.withgoogle.com/kickstart/round/00000000008cb2e1/0000000000c174f2#problem

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (numParticipants, numDays, johnsLastYearId) = (readLine()?.split(" ")
            ?.map { it.toInt() })
            ?: return

        val maxPerDay = IntArray(numDays)
        val johnsScoresPerDay = IntArray(numDays)

        for (participant in 1 until numParticipants + 1) {
            val scores = (readLine()?.split(" ")?.mapNotNull { it.toIntOrNull() }) ?: return

            scores.forEachIndexed { index, value ->
                maxPerDay[index] = Math.max(maxPerDay[index], value)
                if (participant == johnsLastYearId) {
                    johnsScoresPerDay[index] = value
                }
            }
        }

        var missingPoints = 0
        for (index in 0 until numDays) {
            val diff = maxPerDay[index] - johnsScoresPerDay[index]
            if (diff > 0) {
                missingPoints += diff
            }
        }

        println("Case #$testCase: $missingPoints")
    }
}

main(
    arrayOf(
        "2",
        "3 2 3",
        "1000 2000",
        "1500 4000",
        "500 4000",
        "3 3 2",
        "1000 2000 1000",
        "1500 2000 1000",
        "500 4000 1500"
    )
)




