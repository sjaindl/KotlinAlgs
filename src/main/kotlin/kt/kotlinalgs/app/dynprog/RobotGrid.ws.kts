package kt.kotlinalgs.app.dynprog

println("Test")

val maze: Array<BooleanArray> = arrayOf(
    booleanArrayOf(true, false, true, false, true),
    booleanArrayOf(true, true, true, true, true),
    booleanArrayOf(false, true, true, true, false),
    booleanArrayOf(true, true, true, true, true),
    booleanArrayOf(true, true, true, true, true)
)

PathFinder().getPath(maze).forEach {
    print("${it.row}-${it.col} -> ")
}

PathFinder().getPathDP(maze).forEach {
    print("${it.row}-${it.col} -> ")
}

data class MazePoint(val row: Int, val col: Int)

class PathFinder {
    // O(R*C) time + space, no call stack
    fun getPathDP(maze: Array<BooleanArray>): List<MazePoint> {
        if (!maze[0][0]) return emptyList()

        val dp = Array<BooleanArray>(maze.size) { BooleanArray(maze[0].size) } // dp
        for (row in maze.indices) {
            for (col in maze[0].indices) {
                if (!maze[row][col]) dp[row][col] = false
                else if (row == 0 && col == 0) dp[row][col] = maze[row][col]
                else if (row == 0) dp[row][col] = dp[row][col - 1]
                else if (col == 0) dp[row][col] = dp[row - 1][col]
                else dp[row][col] = dp[row][col - 1] || dp[row - 1][col]
            }
        }

        if (!dp[maze.size - 1][maze[0].size - 1]) return emptyList()

        return buildPath(dp)
    }

    private fun buildPath(dp: Array<BooleanArray>): List<MazePoint> {
        val list: MutableList<MazePoint> = mutableListOf()
        var row = 0
        var col = 0
        while (row < dp.size && col < dp[0].size) {
            list.add(MazePoint(row, col))
            if (row < dp.size - 1 && dp[row + 1][col]) row++
            else col++
        }

        return list
    }

    fun getPath(maze: Array<BooleanArray>): List<MazePoint> {
        if (!maze[0][0]) return emptyList()

        val list: MutableList<MazePoint> = mutableListOf()
        val checked = Array<BooleanArray>(maze.size) { BooleanArray(maze[0].size) } // memo

        return getPathRec(maze, 0, 0, list, checked) ?: emptyList()
    }

    fun getPathRec(
        maze: Array<BooleanArray>,
        row: Int,
        col: Int,
        path: MutableList<MazePoint>,
        checked: Array<BooleanArray> // improving O(2^R+C) to O(R*C) time. O(R*C) space.
    ): List<MazePoint>? {
        if (!canBeVisited(maze, row, col)) return null
        if (checked[row][col]) return null

        val point = MazePoint(row, col)

        path.add(point)

        //println(point)

        if (row == maze.size - 1 && col == maze[0].size - 1) {
            return path
        }

        getPathRec(maze, row + 1, col, path, checked)?.let {
            return it
        }

        val downPath = getPathRec(maze, row, col + 1, path, checked)

        if (downPath == null) {
            path.remove(point)
            checked[row][col] = true
        }

        return downPath
    }

    private fun canBeVisited(
        maze: Array<BooleanArray>,
        row: Int,
        col: Int
    ): Boolean {
        if (row >= maze.size || col >= maze[0].size || !maze[row][col]) return false

        return true
    }
}


