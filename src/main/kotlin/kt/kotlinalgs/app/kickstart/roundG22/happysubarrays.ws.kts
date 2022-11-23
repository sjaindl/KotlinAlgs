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

    // https://codingcompetitions.withgoogle.com/kickstart/round/00000000008cb2e1/0000000000c17491#analysis

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val numElements = readLine()?.toIntOrNull() ?: 0
        val array = (readLine()?.split(" ")?.map { it.toInt() }) ?: return

        var happySum = 0

        var index = 0
        for (index in 0 until array.size) {
            var curSum = array[index]
            if (curSum < 0) continue
            happySum += curSum

            for (rightIndex in index + 1 until array.size) {
                curSum += array[rightIndex]
                if (curSum < 0) break
                happySum += curSum
            }
        }

        println("Case #$testCase: $happySum")
    }
}

main(
    arrayOf(
        "2",
        "5",
        "1 -2 3 -2 4",

        "3",
        "1 0 3"
    )
)

/*
1 + x + 1

1 0 3
1,10,103,  0,03,  3 = 12
[0,0,0] -> [12,6,3]
..
[1,1,4]

2 0 3
2,20,203,  0,03,  3 = 15
[0,0,0] -> [15,6,3]
..
[2,2,5]
2 + (2 + 2-2 + 2) + (4 + 5-4 + 5) = 2 + 4 + 10 = 16
at each i: running + (cur-prev) + cur

right to left
cur   = dp[cur+1] + (len - cur) * a[cur] + a[cur]
cur 0 =         6 + (3   - 1  ) * 2      + 2      = 12


array                   [1,  -2, 3,  -2, 4]
prefix sum:             [1,  -1, 2,   0, 4]
nearest smaller value:  [-2, -2, -2, -2, x]
nearest smaller index:  [ 1,  1, 3,   3, 5]
*/