var line = 0
var args: Array<String> = arrayOf()

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

class MaxProfit {
    var maxProfit = 0
}

fun mainV4ReverseWithDays(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        var maxProfit = 0

        // d, n, x
        val (numDaysWarmSeason, vegetableTypes, maxSeedsPerDay) = (readLine()?.split(" ")
            ?.map { it.toInt() })
            ?: return

        val vegetables: MutableList<Vegetable> = mutableListOf()

        for (type in 0 until vegetableTypes) {
            // q, l, v
            val (numOfSeeds, daysToGrow, value) = (readLine()?.split(" ")
                ?.map { it.toInt() })
                ?: return

            vegetables.add(Vegetable(numOfSeeds, daysToGrow, value))
        }

        vegetables.sortBy {
            it.daysToGrow
        }



        println("Case #$testCase: $maxProfit")
    }
}

fun mainV3BottomUp(mainArgs: Array<String>) {

    /*
                 daysToGrow value numOfSeeds
         spinach 2          3     1
         pumpkin 3          10    1
          carrot 4          5     1
         cabbage 2          2     1

             day 0 1 2 3 4 5
         spinach 0
         pumpkin 0
          carrot 0
         cabbage 0
             max 0
     */

}

// TLE on testset 1 + some bug
fun mainV2Backtrack(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    /*
         max X seeds per day
         N types of seeds
     */

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {

        val profit = MaxProfit()

        // d, n, x
        val (numDaysWarmSeason, vegetableTypes, maxSeedsPerDay) = (readLine()?.split(" ")
            ?.map { it.toInt() })
            ?: return

        val vegetables: MutableList<Vegetable> = mutableListOf()

        for (type in 0 until vegetableTypes) {
            // q, l, v
            val (numOfSeeds, daysToGrow, value) = (readLine()?.split(" ")
                ?.map { it.toInt() })
                ?: return

            vegetables.add(Vegetable(numOfSeeds, daysToGrow, value))
            /*
            plant if:
                daysToMature <= numDaysWarmSeason - curDay

            prio:
                value

             */
        }

        backtrack(vegetables, numDaysWarmSeason, maxSeedsPerDay, maxSeedsPerDay, 0, profit)

        println("Case #$testCase: ${profit.maxProfit}")
    }
}

fun backtrack(
    vegetables: MutableList<Vegetable>,
    daysLeft: Int,
    maxSeedsPerDay: Int,
    leftSeedsForDay: Int,
    profitSoFar: Int,
    profit: MaxProfit,
) {
    if (daysLeft == 0) {
        profit.maxProfit = Math.max(profit.maxProfit, profitSoFar)
        return
    }

    if (leftSeedsForDay == 0) {
        backtrack(vegetables, daysLeft - 1, maxSeedsPerDay, maxSeedsPerDay, profitSoFar, profit)
        return
    }

    var checkedVeggy = false
    for (vegetable in vegetables) {
        if (vegetable.daysToGrow >= daysLeft) continue
        if (vegetable.numOfSeeds == 0) continue

        checkedVeggy = true

        val maxSeeds = Math.min(leftSeedsForDay, vegetable.numOfSeeds)
        val newProfit = profitSoFar + vegetable.value

        vegetable.numOfSeeds -= maxSeeds

        backtrack(vegetables, daysLeft, maxSeedsPerDay, leftSeedsForDay - 1, newProfit, profit)

        vegetable.numOfSeeds += maxSeeds
    }

    if (!checkedVeggy) {
        profit.maxProfit = Math.max(profit.maxProfit, profitSoFar)
    }

    if (leftSeedsForDay > 0) {
        backtrack(vegetables, daysLeft - 1, maxSeedsPerDay, maxSeedsPerDay, profitSoFar, profit)
    }
}

// wrong
fun mainV1(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    /*
         max X seeds per day
         N types of seeds
     */

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        // d, n, x
        val (numDaysWarmSeason, vegetableTypes, maxSeedsPerDay) = (readLine()?.split(" ")
            ?.map { it.toInt() })
            ?: return

        val vegetables: MutableList<Vegetable> = mutableListOf()

        for (type in 0 until vegetableTypes) {
            // q, l, v
            val (numOfSeeds, daysToGrow, value) = (readLine()?.split(" ")
                ?.map { it.toInt() })
                ?: return

            vegetables.add(Vegetable(numOfSeeds, daysToGrow, value))
            /*
            plant if:
                daysToMature <= numDaysWarmSeason - curDay

            prio:
                value

             */
        }

        vegetables.sortByDescending { it.value }

        var maxProfit = 0

        for (day in 0 until numDaysWarmSeason) {
            var seedsLeft = maxSeedsPerDay
            val daysLeft = numDaysWarmSeason - day

            for (vegetable in vegetables) {
                if (vegetable.daysToGrow > daysLeft) continue
                if (vegetable.numOfSeeds < 1) continue

                val maxSeeds = Math.min(seedsLeft, vegetable.numOfSeeds)
                seedsLeft -= maxSeeds
                vegetable.numOfSeeds -= maxSeeds

                maxProfit += vegetable.value
            }
        }

        println("Case #$testCase: $maxProfit")
    }
}

data class Vegetable(var numOfSeeds: Int, val daysToGrow: Int, val value: Int)

mainV3BottomUp(arrayOf(
    "2",
    "5 4 1",
    "1 2 3",
    "1 3 10",
    "1 4 5",
    "1 2 2",
    "5 1 1",
    "1 1 1",
))
