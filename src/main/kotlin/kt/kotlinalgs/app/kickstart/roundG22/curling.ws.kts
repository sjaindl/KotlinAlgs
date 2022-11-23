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

    // https://codingcompetitions.withgoogle.com/kickstart/round/00000000008cb2e1/0000000000c17c82

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (radiusStone, radiusHouse) = (readLine()?.split(" ")?.map { it.toInt() }) ?: return

        val numStonesInHouseRed = readLine()?.toIntOrNull() ?: 0
        val distancesRed: MutableList<Double> = mutableListOf()
        calcValidDistances(distancesRed, numStonesInHouseRed, radiusHouse, radiusStone)

        val numStonesInHouseYellow = readLine()?.toIntOrNull() ?: 0
        val distancesYellow: MutableList<Double> = mutableListOf()
        calcValidDistances(distancesYellow, numStonesInHouseYellow, radiusHouse, radiusStone)

        distancesRed.sort()
        distancesYellow.sort()

        var pointsRed = 0
        var pointsYellow = 0

        if (distancesRed.isEmpty()) {
            pointsYellow = distancesYellow.size
        } else if (distancesYellow.isEmpty()) {
            pointsRed = distancesRed.size
        } else if (distancesYellow[0] < distancesRed[0]) {
            pointsYellow = calcPoints(distancesYellow, distancesRed)
        } else {
            pointsRed = calcPoints(distancesRed, distancesYellow)
        }

        println("Case #$testCase: $pointsRed $pointsYellow")
    }
}

fun calcPoints(winning: MutableList<Double>, loosing: MutableList<Double>): Int {
    var points = 0
    var minOpponent = loosing[0]
    var index = 0
    while (index < winning.size && winning[index] < minOpponent) {
        points++
        index++
    }

    return points
}

fun calcValidDistances(
    distances: MutableList<Double>, numStones: Int, radiusHouse: Int, radiusStone: Int
) {
    //println("radiusHouse: $radiusHouse, radiusStone: $radiusStone")

    for (num in 0 until numStones) {
        val (x0, y0) = (readLine()?.split(" ")?.map { it.toInt() }) ?: return
        var dist = euclideanDistance(x0, y0) - radiusStone.toDouble()
        //println("x0: $x0, y0: $y0, dist: $dist")
        if (dist <= radiusHouse) {
            distances.add(dist)
        }
    }
}

fun euclideanDistance(x0: Int, y0: Int, x1: Int = 0, y1: Int = 0): Double {
    return Math.sqrt(
        Math.pow(x0.toDouble() - x1.toDouble(), 2.0) + Math.pow(y0.toDouble() + y1.toDouble(), 2.0)
    )
}

main(
    arrayOf(
        "2",
        "1 5",
        "4",
        "1 -1",
        "6 1",
        "0 6",
        "-5 0",
        "0",
        "10 100",
        "2",
        "-3 -4",
        "200 200",
        "0"
    )
)

main(
    arrayOf(
        "2",
        "1 5",
        "2",
        "1 0",
        "-3 0",
        "1",
        "0 2",

        "10 50",
        "2",
        "-40 -31",
        "-35 70",

        "3",
        "59 0",
        "-10 0",
        "30 40"
    )
)