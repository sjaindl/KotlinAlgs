var line = 0
var args: Array<String> = arrayOf()

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

// https://codingcompetitions.withgoogle.com/kickstart/round/00000000008cb1b6/0000000000c4766e

// O(N) time, O(1) space
fun main(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (trackLen, numRuns) = readLine()?.split(" ")?.map { it.toInt() } ?: return

        var lapCount = 0L

        var lastRunWasClockWise = true
        var runsClockWise = true

        var into = 0

        for (index in 0 until numRuns) {
            val (r, dir) = readLine()?.split(" ") ?: return
            val runUnits = r.toIntOrNull() ?: return
            if (index == 0) lastRunWasClockWise = dir == "C"
            runsClockWise = dir == "C"

            if (runsClockWise == lastRunWasClockWise) {
                into += runUnits
            } else {
                into = runUnits - into
            }

            lapCount += (into / trackLen)
            into %= trackLen

            lastRunWasClockWise = runsClockWise
        }

        println("Case #$testCase: $lapCount")
    }
}

main(
    arrayOf(
        "2",
        "5 3",
        "8 C",
        "3 C",
        "6 C",
        "8 4",
        "5 C",
        "9 C",
        "8 C",
        "20 C"
    )
)

main(
    arrayOf(
        "3",
        "5 3",
        "8 C",
        "4 A",
        "5 C",
        "4 5",
        "2 C",
        "8 A",
        "3 A",
        "5 C",
        "8 A",
        "4 3",
        "3 C",
        "2 A",
        "5 C"
    )
)
