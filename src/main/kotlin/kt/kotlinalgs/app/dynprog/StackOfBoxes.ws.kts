package kt.kotlinalgs.app.dynprog

println("Test")

val boxes = listOf(
    Box(5, 7, 5),
    Box(1, 3, 1),
    Box(2, 4, 3), // not include
    Box(3, 5, 3), // include
    Box(4, 6, 4)
)

println(Solution().maxHeight(boxes)) // 7 + 3 + 5 + 6 = 21

// 1.  Achung: Versuchen für memo arguments zu verringern
// zb nur index statt index + lastWidth + lastDepth .. in dem Fall zb vor Aufruf und in Wrapper method auf valid checken!
// 2. auch maxHeight nicht nötig mitzugeben, kann in rec. Method ausgerechnet werden!!
// schema immer: max = 0.
//  for x in .. : max = Math.max(max, rec(..)).
// ev. max += cur
// 3. und wenn möglich start bei index 0, nicht -1!! lieber mehr code/extra loop in wrapper!

data class Box(
    val width: Int, val height: Int, val depth: Int
)

class Solution {
    // create max height stack, where each below box is strictly smaller in width, height and depth

    // with memo: O(N) runtime + space .. bzw. O(N log N) wegen sorting
    // without memo: O(N^2) runtime
    fun maxHeight(boxes: List<Box>): Int {
        // 1. sort by height decreasing (eliminate 1 dimension)
        val sorted = boxes.sortedByDescending {
            it.height
        }

        println(sorted)

        val memo = IntArray(boxes.size) { -1 }

        // try each start box
        var max = 0
        for (index in boxes.indices) {
            max = maxOf(max, maxHeightRec(sorted, index, memo))
        }

        return max

        /*
     for each box:
        include (if valid - check width + depth)
        not include

     fun maxHeightRec(boxes, index, lastWidth, lastDepth, sumHeight)
        BC: if (index == boxes.size) return sumHeight

        include box (if valid - check width + depth)
        not include box
     */
    }

    private fun maxHeightRec(
        boxes: List<Box>,
        index: Int,
        memo: IntArray
    ): Int {
        //if (index == boxes.size) return sumHeight // BC

        if (memo[index] != -1) return memo[index]

        //println("$index: $sumHeight")

        val curBox = boxes[index]
        var maxHeight = 0

        for (nextBoxIndex in index + 1 until boxes.size) {
            val nextBox = boxes[nextBoxIndex]

            if (isValid(curBox, nextBox)) {
                val heightIncludingBox = maxHeightRec(
                    boxes,
                    nextBoxIndex,
                    memo
                )
                maxHeight = maxOf(maxHeight, heightIncludingBox)
            }
        }

        memo[index] = curBox.height + maxHeight

        return memo[index]
    }

    private fun isValid(cur: Box?, nextBox: Box): Boolean {
        val curBox = cur ?: return true
        return nextBox.width < curBox.width && nextBox.depth < curBox.depth && nextBox.height < curBox.height
    }
}
