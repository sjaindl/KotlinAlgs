import kotlin.math.min

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

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (n, k, x, d) = readLine()?.split(" ")?.map { it.toInt() } ?: return
        val numTechleads = n
        val minTechLeadsNeeded = k
        val meetingLen = x
        val lastHour = d

        val numMeetings = readLine()?.toIntOrNull() ?: return

        val meetings = Array<MutableList<Pair<Int, Int>>>(numTechleads) { mutableListOf() }

        for (index in 0 until numMeetings) {
            val (p, l, r) = readLine()?.split(" ")?.map { it.toInt() } ?: return
            val techLead = p - 1
            val start = l
            val end = r

            meetings[techLead].add(Pair(start, end))
        }

        var meetingsToCancel = Int.MAX_VALUE

        for (start in 0..lastHour - meetingLen) {
            val curEnd = start + meetingLen - 1
            val overlaps = IntArray(numTechleads)

            for (techlead in 0 until numTechleads) {
                meetings[techlead].forEach {
                    if ((it.second > start && it.first <= curEnd)) {
                        overlaps[techlead]++
                    }
                }
            }

            val sorted = overlaps.sortedBy { it }

            var meetingsToCancelInCurWindow = 0

            for (index in 0 until minTechLeadsNeeded) {
                meetingsToCancelInCurWindow += sorted[index]
            }

            meetingsToCancel = min(meetingsToCancel, meetingsToCancelInCurWindow)
        }

        println("Case #$testCase: $meetingsToCancel")
    }
}

main(
    arrayOf(
        "3",
        "3 2 2 6",
        "5",
        "1 3 5",
        "2 1 3",
        "2 2 6",
        "3 0 1",
        "3 3 6",
        "3 3 2 6",
        "5",
        "1 3 5",
        "2 1 3",
        "2 2 6",
        "3 0 1",
        "3 3 6",
        "3 2 3 6",
        "5",
        "1 3 5",
        "2 1 3",
        "2 2 6",
        "3 0 1",
        "3 3 6"
    )
)
