package kt.kotlinalgs.app.arrays

println("test")

val ks = Knapsack()
ks.maxPossibleValueMemo(
    intArrayOf(60, 100, 120),
    intArrayOf(10, 20, 30),
    50
)

ks.maxPossibleValueDp(
    intArrayOf(60, 100, 120),
    intArrayOf(10, 20, 30),
    50
)

ks.maxPossibleValueDp(
    intArrayOf(10, 15, 40),
    intArrayOf(1, 2, 3),
    6
)

// https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/
// NP complete?
// O(2 ^ n) runtime - 2 choices at each index: Include value (if possible by weight) or not
// O(N) space - recursive stack

// with memoization: O(N*W) space + time
// with bottom-up dp: O(N*W) space + time (smart reduction to O(W) space possible)
class Knapsack {
    fun maxPossibleValueDp(values: IntArray, weights: IntArray, maxWeight: Int): Int {
        val dp = Array<IntArray>(values.size + 1) { IntArray(maxWeight + 1) { 0 } }

        for (index in 1 until values.size + 1) {
            for (weight in 1 until maxWeight + 1) {
                /*
                    max at index/weight = max of:
                        1. element only (if <= maxWeight)
                        2. incl prev.:
                 */
                if (weights[index - 1] > weight) {
                    // don't include, too heavy. Take old value.
                    dp[index][weight] = dp[index - 1][weight]
                } else {
                    // include element or not
                    val cur = values[index - 1]
                    dp[index][weight] = Math.max(
                        cur + dp[index - 1][weight - weights[index - 1]],
                        dp[index - 1][weight]
                    )
                }
            }
        }

        dp.forEach {
            println("")
            it.forEach { value ->
                print("$value, ")
            }
        }

        return dp[values.size][maxWeight]
    }

    fun maxPossibleValueMemo(values: IntArray, weights: IntArray, maxWeight: Int): Int {
        if (values.size != weights.size) return 0
        if (values.isEmpty()) return 0

        // dp for bottom up lookup of index + weight -> value
        val memo = Array<IntArray>(values.size) { IntArray(maxWeight) { -1 } }

        return maxPossibleValueRec(values, weights, maxWeight, 0, 0, 0, memo)
    }

    private fun maxPossibleValueRec(
        values: IntArray,
        weights: IntArray,
        maxWeight: Int,
        index: Int,
        usedWeight: Int,
        totalValue: Int,
        memo: Array<IntArray>
    ): Int {
        // Base case: last index processed
        if (index == values.size) {
            return totalValue
        }

        if (memo[index][usedWeight] != -1) return memo[index][usedWeight]

        var newValue = 0
        // Case 1: Don't include value at index
        val withoutIndex = maxPossibleValueRec(
            values,
            weights,
            maxWeight,
            index + 1,
            usedWeight,
            totalValue,
            memo
        )

        if (withoutIndex > newValue) newValue = withoutIndex

        // Case 2: Include value at index, if possible + update weight
        val newUsedWeight = usedWeight + weights[index]
        if (newUsedWeight <= maxWeight) {
            val withIndex = maxPossibleValueRec(
                values,
                weights,
                maxWeight,
                index + 1,
                newUsedWeight,
                totalValue + values[index],
                memo
            )

            if (withIndex > newValue) newValue = withIndex
        }

        memo[index][usedWeight] = newValue

        return memo[index][usedWeight]
    }
}
