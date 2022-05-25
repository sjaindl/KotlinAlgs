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

class TransitiveClosure() {
    fun reachabilityMatrix(graph: DirectedWeightedGraphWithAdjMatrix): Array<BooleanArray> {
        val size = graph.matrix.size

        val matrix = Array<BooleanArray>(size) { BooleanArray(size) }
        for (row in graph.matrix.indices) {
            for (col in graph.matrix[0].indices) {
                matrix[row][col] = if (graph.matrix[row][col] != Int.MAX_VALUE) true else false
            }
        }

        for (start in 0 until size) {
            for (intermediate in 0 until size) {
                for (end in 0 until size) {
                    if (matrix[start][intermediate] && matrix[intermediate][end]) {
                        matrix[start][end] = true
                    }
                }
            }
        }

        return matrix
    }

    fun reachabilityMatrixDFS(graph: DirectedWeightedGraphWithAdjMatrix): Array<BooleanArray> {
        val size = graph.matrix.size
        val transitiveClosureMatrix = Array<BooleanArray>(size) { BooleanArray(size) }

        // 1. call DFS for each vertice not in tc matrix (not marked itself)
        // 2. rec DFS func:
        //      mark node pair as reachable
        //      check neighbours, if not already marked

        for (vertice in graph.matrix.indices) {
            reachabilityMatrixDFS(graph, vertice, vertice, transitiveClosureMatrix)
        }

        return transitiveClosureMatrix
    }

    private fun reachabilityMatrixDFS(
        graph: DirectedWeightedGraphWithAdjMatrix,
        start: Int,
        current: Int,
        tc: Array<BooleanArray>
    ) {
        tc[start][current] = true

        for (nb in graph.matrix.indices) {
            if (graph.matrix[current][nb] != Int.MAX_VALUE && !tc[start][nb]) {
                reachabilityMatrixDFS(graph, start, nb, tc)
            }
        }
    }

    fun printShortestPaths(matrix: Array<BooleanArray>) {
        for (from in matrix.indices) {
            for (to in matrix.indices) {
                println("$from -> $to: Dist ${matrix[from][to]}")
            }
        }
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

// https://www.geeksforgeeks.org/transitive-closure-of-a-graph/
// with graph from: https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
// O(V^3) runtime

val transitive = TransitiveClosure()
val outputMatrix = transitive.reachabilityMatrix(graph)

transitive.printShortestPaths(outputMatrix)

// https://www.geeksforgeeks.org/transitive-closure-of-a-graph-using-dfs/
// O(V * (E+V))

val tcMatrix = transitive.reachabilityMatrixDFS(graph)
transitive.printShortestPaths(tcMatrix)
