package kt.kotlinalgs.app.searching

println("test")

val matrix = arrayOf(
    intArrayOf(15, 20, 40, 85),
    intArrayOf(20, 35, 80, 95),
    intArrayOf(30, 55, 95, 105),
    intArrayOf(40, 80, 100, 120)
)

Solution().sortedMatrixSearch(matrix, 30)

for (row in matrix.indices) {
    for (col in matrix[0].indices) {
        println("check row $row col $col")
        println(Solution().sortedMatrixSearch(matrix, matrix[row][col]))
    }
}

// O(log M + log N) runtime, at each step eliminating 2 or 3 of 4 quadrants!
class Solution {
    fun sortedMatrixSearch(matrix: Array<IntArray>, searched: Int): Pair<Int, Int>? {
        if (matrix.isEmpty()) return null
        if (matrix[0].isEmpty()) return null

        return sortedMatrixSearchRec(matrix, searched, 0, matrix.size - 1, 0, matrix[0].size - 1)
    }

    private fun sortedMatrixSearchRec(
        matrix: Array<IntArray>,
        searched: Int,
        rowLow: Int,
        rowHigh: Int,
        colLow: Int,
        colHigh: Int
    ): Pair<Int, Int>? {
        if (rowHigh < rowLow || colHigh < colLow) return null

        val rowCenter = rowLow + (rowHigh - rowLow) / 2
        val colCenter = colLow + (colHigh - colLow) / 2
        val centerValue = matrix[rowCenter][colCenter]

        val rowLowerCenter = rowCenter + 1
        val colLowerCenter = colCenter + 1
        val lowerIsInBounds = isInBounds(matrix, rowLowerCenter, colLowerCenter)

        if (centerValue == searched) {
            return (rowCenter to colCenter)
        } else if (lowerIsInBounds && matrix[rowLowerCenter][colLowerCenter] == searched) {
            return (rowLowerCenter to colLowerCenter)
        } else if (centerValue > searched) {
            // search left
            val result = sortedMatrixSearchRec(
                matrix,
                searched,
                rowLow,
                rowHigh,
                colLow,
                colCenter - 1
            )
            if (result != null) return result

            // search on top of
            return sortedMatrixSearchRec(
                matrix,
                searched,
                rowLow,
                rowCenter - 1,
                colCenter,
                colCenter
            )
        } else if (lowerIsInBounds && matrix[rowLowerCenter][colLowerCenter] < searched) {
            // search right
            val result = sortedMatrixSearchRec(
                matrix,
                searched,
                rowLow,
                rowHigh,
                colLowerCenter + 1,
                colHigh
            )
            if (result != null) return result

            // search below
            return sortedMatrixSearchRec(
                matrix,
                searched,
                rowLowerCenter + 1,
                rowHigh,
                colLowerCenter,
                colLowerCenter
            )
        } else {
            // search bottom left and top right quadrant
            // bottom left:
            sortedMatrixSearchRec(
                matrix,
                searched,
                rowCenter + 1,
                rowHigh,
                colLow,
                colCenter
            )?.let {
                return it
            }
            // top right
            return sortedMatrixSearchRec(
                matrix,
                searched,
                rowLow,
                rowCenter,
                colCenter + 1,
                colHigh
            )
        }
    }

    private fun isInBounds(matrix: Array<IntArray>, row: Int, col: Int): Boolean {
        return row >= 0 && col >= 0 && row < matrix.size && col < matrix[0].size
    }
}
