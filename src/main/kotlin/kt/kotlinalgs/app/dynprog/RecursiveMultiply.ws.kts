package kt.kotlinalgs.app.dynprog

println("Test")

val mp = Multiplier()
println(mp.multiply(7, 8))
println(mp.multiply(8, 7))
println(mp.multiply(0, 7))
println(mp.multiply(8, 0))
println(mp.multiply(1, 7))
println(mp.multiply(7, 2))
println(mp.multiply(-8, 7))

/*
 31, 35 ->
    31, 17 * 2 + 35
    31, 8 * 2 + 35
    31, 4 * 2
    31, 2 * 2
    31, 1 * 2
    .. even needs no memo then, so O(smaller) runtime + space (rec. call stack)
 */

class Multiplier {
    // 7 * 8
    fun multiply(first: Int, second: Int): Int {
        if (first < 0 || second < 0) return 0

        val bigger = if (first > second) first else second
        val smaller = if (first < second) first else second
        val memo: MutableMap<Int, Int> = mutableMapOf()

        return multiplyRec(bigger, smaller, memo)
    }

    private fun multiplyRec(bigger: Int, smaller: Int, memo: MutableMap<Int, Int>): Int {
        if (smaller == 0) return 0
        if (smaller == 1) return bigger

        memo[smaller]?.let {
            return it
        }

        if (smaller shr 1 shl 1 == smaller) { // even, could also check smaller % 2 == 0
            memo[smaller] = multiplyRec(bigger, smaller shr 1, memo) shl 1
        } else { // odd
            //memo[smaller] = multiplyRec(bigger, smaller shr 1, memo) + multiplyRec(bigger, (smaller shr 1) + 1, memo)
            // improvement:
            val evenResult = multiplyRec(bigger, smaller shr 1, memo) shl 1
            memo[smaller] = evenResult + bigger
        }

        return memo.getValue(smaller)
    }
}
