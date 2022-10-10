import kotlin.math.pow

/*
https://codingcompetitions.withgoogle.com/kickstart/round/00000000008f49d7/0000000000bcf0aa

Watson and Sherlock are gym buddies.

Their gym trainer has given them three numbers, A, B, and N, and has asked Watson and Sherlock to pick two different strictly positive integers i and j, where i and j are both less than or equal to N. Watson is expected to eat exactly iA sprouts every day, and Sherlock is expected to eat exactly jB sprouts every day.

Watson and Sherlock have noticed that if the total number of sprouts eaten by them on a given day is divisible by a certain integer K, then they get along well that day.

So, Watson and Sherlock need your help to determine how many such pairs of (i,j) exist, where i≠j and they get along well that day. As the number of pairs can be really high, please output it modulo 10^9+7(1000000007).
Input
The first line of the input gives the number of test cases, T. T test cases follow. Each test case consists of one line with 4 integers A, B, N and K, as described above.

Output
For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is the required answer.

Limits
Time limit: 60 seconds.
Memory limit: 1 GB.
1≤T≤100.
0≤A≤106.
0≤B≤106.
Test Set 1
1≤K≤104.
1≤N≤103.
Test Set 2
1≤K≤105.
1≤N≤1018.
Sample
Sample Input
save_alt
content_copy
3
1 1 5 3
1 2 4 5
1 1 2 2
Sample Output
save_alt
content_copy
Case #1: 8
Case #2: 3
Case #3: 0
In Case #1, the possible pairs are (1,2), (1,5), (2,1), (2,4), (4,2), (4,5), (5,1), and (5,4).

In Case #2, the possible pairs are (1,2), (1,3), and (4,1).

In Case #3, No possible pairs are there, as i≠j.
 */


var line = 0
var args: Array<String> = arrayOf()

val M: Long = 1000000007

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

fun main(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toInt() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (a, b, n, k) = (readLine()?.split(" ")?.map { it.toLong() }) ?: return

        // pick i <= N
        // pick j <= N

        // Watson: eat ->   W = i^a
        // Sherlock: eat -> S = j^b

        // well, if (W + S) % K == 0

        // Only 1 loop, we know:
        // j^b % k == -i^a % k
        // e.g. a = 5, b = 3, k = 3 -> 2^3 % 3 = 8 % 3 = 2; == -i^5 % 3 =
        /*
            i	a	k	-i^a % k	i^a % k
            1	5	3	2	        1 .. match
            2	5	3	1	        2
            3	5	3	0	        0
            4	5	3	2	        1 .. match
            5	5	3	1	        2
            L[j] = [..] -> L[2] = [1,4]

            j == 2, b == 3
            2^3 % 3 = 8 % 3 = 2;

            // Watson: eat ->   W = i^a			j^b = 2^3	8	mod k	2
            // Sherlock: eat -> S = j^b

            // well, if (W + S) % K == 0
                  w+s mod k	8+1 mod 3	  == 0
                            8+1024 mod 3  == 0
        */

        val sizeToCheck = Math.min(n, k) + 1
        val precomputedCounts = LongArray(size = k.toInt()) //stores counts of mod results
        for (j in 1 until sizeToCheck) {
            val num: Long = ((n - j) / k + 1) % M
            val w = exponentiationBySquaring(base = j, exponent = b, mod = k)
            precomputedCounts[w.toInt()] = (precomputedCounts[w.toInt()] + num) % M
        }

        var wellCases: Long = 0

        for (i in 1 until sizeToCheck) {
            val num: Long = ((n - i) / k + 1) % M
            val targetMod = (k - exponentiationBySquaring(base = i, exponent = a, mod = k)) % k
            wellCases += (precomputedCounts[targetMod.toInt()] * num) % M

            // subtract where i == j
            if (exponentiationBySquaring(i, b, k) == targetMod) {
                wellCases += M - num
            }
        }

        wellCases %= M

        // if ((w + s) % k == 0) wellCases++

        println("Case #$testCase: $wellCases")
    }
}

// Improve exponentiation to log by using exponentiation by squaring
// time: O(N^2 * (log A + log B))
fun mainSlow(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toInt() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (a, b, n, k) = (readLine()?.split(" ")?.map { it.toLong() }) ?: return

        // pick i <= N
        // pick j <= N

        // Watson: eat ->   W = i^a
        // Sherlock: eat -> S = j^b

        // well, if (W + S) % K == 0

        var wellCases = 0

        for (i in 1 until n + 1) {
            val w = exponentiationBySquaring(base = i, exponent = a, mod = k)
            for (j in 1 until n + 1) {
                if (i == j) continue

                val s = exponentiationBySquaring(base = j, exponent = b, mod = k)
                if ((w + s) % k == 0L) wellCases++
            }
        }

        println("Case #$testCase: $wellCases")
    }
}

// 2^3 = 8
fun exponentiationBySquaring(base: Long, exponent: Long, mod: Long): Long {
    if (exponent == 0L) return 1 % mod
    if (exponent == 1L) return base % mod

    val half = exponentiationBySquaring(base = base, exponent = exponent / 2, mod = mod) // 2

    return if (exponent % 2 == 0L) (half * half) % mod
    else (half * half * base) % mod
}

// time: O(N^2 * (A + B))
fun mainVerySlow(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toInt() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (a, b, n, k) = (readLine()?.split(" ")?.map { it.toInt() }) ?: return

        // pick i <= N
        // pick j <= N

        // Watson: eat ->   W = i^a
        // Sherlock: eat -> S = j^b

        // well, if (W + S) % K == 0

        var wellCases = 0

        for (i in 1 until n + 1) {
            for (j in 1 until n + 1) {
                if (i == j) continue

                val w = Math.pow(i.toDouble(), a.toDouble()).toInt() // i.toDouble().pow(a).toInt()
                val s = Math.pow(j.toDouble(), b.toDouble()).toInt() //j.toDouble().pow(b).toInt()
                if ((w + s) % k == 0) wellCases++
            }
        }

        println("Case #$testCase: $wellCases")
    }
}

val testCase = arrayOf(
    "3",
    "1 1 5 3",
    "1 2 4 5",
    "1 1 2 2"
)

val testSet1 = arrayOf(
    "100",
    "785993 353158 450 9945",
    "995057 258912 897 2777",
    "469380 201246 5 9983",
    "163115 338256 694 3620",
    "764310 801538 180 9971",
    "899317 58626 313 9252",
    "413451 670413 306 9912",
    "465388 687541 433 2730",
    "0 0 1000 10000",
    "91470 970933 201 2090",
    "911301 0 240 1",
    "743939 629618 630 2922",
    "898046 754524 907 9928",
    "69270 0 905 5624",
    "839037 75654 340 9965",
    "332771 390852 857 7492",
    "362208 558351 329 9940",
    "0 0 490 1",
    "677822 578403 164 3030",
    "591775 231413 490 9964",
    "5994 9029 955 9991",
    "354756 670563 848 9903",
    "957323 710768 975 9945",
    "29490 760694 795 4976",
    "620040 633055 248 4326",
    "0 0 371 982",
    "922264 470151 936 9979",
    "68369 598781 478 9951",
    "761383 256684 922 9963",
    "473072 514795 634 5062",
    "0 9856 884 7480",
    "713069 571203 629 8095",
    "200796 348988 431 9985",
    "224707 585930 552 9980",
    "835328 674377 14 1710",
    "875384 585525 177 9966",
    "47124 956341 806 9961",
    "777656 45642 148 9913",
    "371389 715883 723 5881",
    "316494 157787 347 9943",
    "486061 999693 790 9996",
    "371620 938672 339 9908",
    "679743 704574 56 9996",
    "657449 370715 292 9953",
    "710642 757231 459 1370",
    "7006 656712 739 9905",
    "89786 172078 685 1346",
    "426964 630005 820 2722",
    "420994 592532 865 9972",
    "258229 755394 197 5509",
    "135354 966790 908 9364",
    "471774 285645 337 4366",
    "433601 566311 642 8549",
    "318112 685193 399 1019",
    "736793 132462 263 9926",
    "442957 209365 41 1249",
    "205683 828431 331 9980",
    "818604 697608 364 7887",
    "869490 498575 646 3610",
    "461436 907454 886 3715",
    "255578 945387 673 8012",
    "672259 334016 916 2282",
    "231355 554034 184 7118",
    "80951 990221 919 2502",
    "668150 867155 839 9951",
    "187332 685141 630 9984",
    "762033 567892 782 9944",
    "88506 188347 689 743",
    "947896 360753 80 200",
    "698117 794638 743 3057",
    "494651 211678 460 9960",
    "521612 658310 716 8003",
    "95843 667316 280 5167",
    "269436 624765 487 9926",
    "712284 922971 892 8442",
    "775764 806730 253 8702",
    "934590 775907 388 9990",
    "501571 486050 544 5969",
    "304526 666222 134 9946",
    "0 411828 180 1",
    "440212 869741 294 9907",
    "166846 198650 174 9910",
    "535353 211186 843 9913",
    "0 0 100 199",
    "388411 686574 690 9903",
    "439643 191762 515 1550",
    "898613 656932 104 712",
    "960796 839879 408 9910",
    "0 0 10 5",
    "886380 777329 329 1215",
    "490251 45056 175 7751",
    "846104 828623 667 9958",
    "931666 638275 48 9922",
    "470127 316560 106 9997",
    "20662 245002 182 6308",
    "425747 0 597 1",
    "782272 669065 965 3145",
    "700309 197836 13 9959",
    "687243 106766 307 9970",
    "185158 390107 374 1057"
)

mainVerySlow(testCase)
mainSlow(testCase)
main(testSet1)
