/*
https://codingcompetitions.withgoogle.com/kickstart/round/00000000008f49d7/0000000000bcf2ed

Isyana is given the number of visitors at her local theme park on N consecutive days. The number of visitors on the i-th day is Vi. A day is record breaking if it satisfies both of the following conditions:

Either it is the first day, or the number of visitors on the day is strictly larger than the number of visitors on each of the previous days.
Either it is the last day, or the number of visitors on the day is strictly larger than the number of visitors on the following day.
Note that the very first day could be a record breaking day!
Please help Isyana find out the number of record breaking days.

Input
The first line of the input gives the number of test cases, T. T test cases follow. Each test case begins with a line containing the integer N. The second line contains N integers. The i-th integer is Vi and represents the number of visitors on the i-th day.

Output
For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is the number of record breaking days.

Limits
Time limit: 20 seconds.
Memory limit: 1 GB.
1≤T≤100.
0≤Vi≤2×105, for all i.
Test Set 1
1≤N≤1000.
Test Set 2
1≤N≤2×105, for at most 10 test cases.
For the remaining cases, 1≤N≤1000.
Sample
Sample Input
save_alt
content_copy
4
8
1 2 0 7 2 0 2 0
6
4 8 15 16 23 42
9
3 1 4 1 5 9 2 6 5
6
9 9 9 9 9 9
Sample Output
save_alt
content_copy
Case #1: 2
Case #2: 1
Case #3: 3
Case #4: 0
In Sample Case #1, the underlined numbers in the following represent the record breaking days: 12––07––2020.

In Sample Case #2, only the last day is a record breaking day: 4815162342–––.

In Sample Case #3, the first, the third, and the sixth days are record breaking days: 3––14––159––265.

In Sample Case #4, there is no record breaking day: 999999.
 */

var line = 0
var args: Array<String?> = arrayOf()

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

fun main(mainArgs: Array<String?>) {
    args = mainArgs

    // Read number of testcases from standard input.
    val testCases = readLine()?.toInt() ?: 0

    for (testCase in 1 until testCases + 1) {
        // O(N) runtime, O(1) space solution per testcase

        val numVisitors = readLine()?.toInt()
        val days = readLine()?.split(" ")?.map { it.toInt() }

        var recordBreakingDays = 0

        var max = 0

        days?.forEachIndexed { index, visitors ->
            // Condition 1: first day or larger then all following days
            if (index != 0 && visitors <= max) {
                return@forEachIndexed
            }

            max = visitors

            // Condition 2: last day or larger than next day
            if (index != days.size - 1 && days[index + 1] >= visitors) {
                return@forEachIndexed
            }

            recordBreakingDays++
        }

        println("Case #$testCase: $recordBreakingDays")
    }
}

main(arrayOf("1", "8", "1 2 0 7 2 0 2 0"))
