package kt.kotlinalgs.app.dynprog

println("Test")

for (steps in 0 until 35) {
    //println(countWays(steps))
    //println(countWaysMemo(steps))
    println(countWaysDp(steps))
}

fun countWays(steps: Int): Int {
    if (steps < 0) return 0
    if (steps == 0) return 1

    return countWays(steps - 1) + countWays(steps - 2) + countWays(steps - 3)
}

fun countWaysMemo(steps: Int): Int {
    val memo = IntArray(steps + 1) { -1 }

    return countWaysMemo(steps, memo)
}

fun countWaysMemo(steps: Int, memo: IntArray): Int {
    if (steps < 0) return 0
    if (steps == 0) return 1

    if (memo[steps] == -1) {
        memo[steps] = countWaysMemo(steps - 1, memo) + countWaysMemo(steps - 2, memo) + countWaysMemo(steps - 3, memo)
    }

    return memo[steps]
}

fun countWaysDp(steps: Int): Int {
    if (steps <= 2) return steps

    val dp = IntArray(steps + 1) { 0 }

    dp[0] = 0
    dp[1] = 1
    dp[2] = 2

    for (step in 3 until steps + 1) {
        dp[step] = dp[step - 1] + dp[step - 2] +dp[step - 3]
    }

    return dp[steps]
}

/*
n = 6

1
 2 ..
 3 ..
2
 3 ..
 4 ..
 5 ..
3
 4
  5
   6
 5
  6
 6
 */