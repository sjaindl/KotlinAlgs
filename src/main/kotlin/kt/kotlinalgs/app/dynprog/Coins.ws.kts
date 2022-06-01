package kt.kotlinalgs.app.dynprog

Solution().test()

/*
11:
10,1
5,5,1
5,1,1,1,1,1,1
1,1,1,1,1,1,1,1,1,1,1

10: 1 or 0
5: 0,1 or 2
1: 0,1,2,3,4,5,6

ways(1x10) + ways(0x10)
ways(1x10) = ways(1x1) = 1
    ways(1x1) = 1
ways(0x10) = ways(2x5) + ways(1x5) + ways(0x5)
    ways(2x5) = ways(1x1)
    ways(1x5) = ways(6x5)
    ways(0x5) = ways(11x1)
 */

class Solution {
    fun test() {
        println(Coins().waysToCollectCoins(0))
        println(Coins().waysToCollectCoins(1))
        println(Coins().waysToCollectCoins(11))
        println(Coins().waysToCollectCoins(15))
        // 10+5, 10+1+1+1+1+1, 5+5+5, 5+5+1+1+1+1+1, 5+1+1+.., 1+1+1+..
        println(Coins().waysToCollectCoins(25))
    }
}

/*
11, [25, 10, 5, 1]
= 4 ways:
0*25 + 1*10 + 1*1
0*25 + 0*10 + 2*5 + 1*1
0*25 + 0*10 + 1*5 + 6*1
0*25 + 0*10 + 0*5 + 11*1

memo: O(C*D)

dp:
D = 10, 5, 1
C = 10

dp
    0   1   5   10  D
0   0   1   1   1
1   0   1
2   0   1
3   0   1
4   0   1
5   0   1
6   0   1
7   0   1
8   0   1
9   0   1
10  0   1
C
 */

class Coins {
    fun waysToCollectCoins(cents: Int, denominators: List<Int> = listOf(25, 10, 5, 1)): Int {
        val included: MutableList<MutableMap<Int, Int>> = mutableListOf()
        included.add(mutableMapOf())

        val memo = Array(denominators.size) { IntArray(cents + 1) { -1 } }

        val res = waysToCollectCoinsRec(cents, 0, included, memo, denominators)
        //println(included)

        return res
    }

    private fun waysToCollectCoinsRec(
        cents: Int, // 11
        denomIndex: Int, // 0
        included: MutableList<MutableMap<Int, Int>>,
        memo: Array<IntArray>,
        denominators: List<Int> = listOf(25, 10, 5, 1)
    ): Int {
        val denom = denominators[denomIndex]

        if (denomIndex == denominators.size - 1 && cents % denom == 0) {
            included.add(mutableMapOf())
            return 1
        }

        if (cents < 0 || denomIndex >= denominators.size) {
            //println("invalid")
            return 0
        }

        if (memo[denomIndex][cents] != -1) return memo[denomIndex][cents]

        var ways = 0 // 0

        val maxOfDenomToInclude = cents / denom + 1
        for (times in 0 until maxOfDenomToInclude) { // 0 until 11 / 10 = 0
            val remaining = cents - denominators[denomIndex] * times

            //println("cents: $cents, $times x ${denominators[denomIndex]}, rem.: $remaining")

            if (times >= 0) {
                val map = included[included.lastIndex]
                map[denominators[denomIndex]] = times
                included[included.lastIndex] = map
            }

            ways += waysToCollectCoinsRec(remaining, denomIndex + 1, included, memo, denominators)
        }

        //println("$cents - $ways")

        memo[denomIndex][cents] = ways

        return ways
    }
}
