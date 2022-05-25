package kt.kotlinalgs.app.graph

println("Test")

/*
    relax all intermediate nodes
    O(V^3)

    1. create adj matrix directed weighted graph
    2. for each intermediate in 0 until V
        for each start in 0 until V
            for each end in 0 until V
                relax if matrix[i][k] + matrix[k][j] < matrix[i][j]
 */

data class DirectedWeightedGraphWithAdjMatrix(val matrix: Array<IntArray>)

class FloydWarshall() {
    fun allShortestPaths(graph: DirectedWeightedGraphWithAdjMatrix): Array<IntArray> {
        val matrix = graph.matrix.copyOf()
        val size = matrix.size

        for (start in 0 until size) {
            for (intermediate in 0 until size) {
                for (end in 0 until size) {
                    if (matrix[start][intermediate] != Int.MAX_VALUE && matrix[intermediate][end] != Int.MAX_VALUE) {
                        val curPathCost = matrix[start][intermediate] + matrix[intermediate][end]
                        if (curPathCost < matrix[start][end]) {
                            matrix[start][end] = curPathCost
                        }
                    }
                }
            }
        }

        return matrix
    }

    fun printShortestPath(matrix: Array<IntArray>, from: Int, to: Int) {
        println("$from -> $to: Dist ${matrix[from][to]}")
    }
}

val INF = Int.MAX_VALUE

val graph = DirectedWeightedGraphWithAdjMatrix(
    arrayOf(
        intArrayOf(0, 5, INF, 10),
        intArrayOf(INF, 0, 3, INF),
        intArrayOf(INF, INF, 0, 1),
        intArrayOf(INF, INF, INF, 0)
    )
)

val floyd = FloydWarshall()
floyd.allShortestPaths(graph)

floyd.printShortestPath(graph.matrix, 0, 1)
floyd.printShortestPath(graph.matrix, 0, 2)
floyd.printShortestPath(graph.matrix, 0, 3)
floyd.printShortestPath(graph.matrix, 1, 0)
floyd.printShortestPath(graph.matrix, 1, 2)
floyd.printShortestPath(graph.matrix, 1, 3)
floyd.printShortestPath(graph.matrix, 2, 0)
floyd.printShortestPath(graph.matrix, 2, 1)
floyd.printShortestPath(graph.matrix, 2, 2)
floyd.printShortestPath(graph.matrix, 3, 0)
floyd.printShortestPath(graph.matrix, 3, 1)
floyd.printShortestPath(graph.matrix, 3, 2)
