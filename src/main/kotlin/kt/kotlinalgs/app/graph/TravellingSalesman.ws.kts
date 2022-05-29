package kt.kotlinalgs.app.graph

Solution().test()

data class DirectedWeightedGraphWithAdjMatrix(val matrix: Array<IntArray>)

class Solution {
    fun test() {
        println("Test")

        val graph = DirectedWeightedGraphWithAdjMatrix(
            arrayOf(
                intArrayOf(0, 10, 15, 20),
                intArrayOf(10, 0, 35, 25),
                intArrayOf(15, 35, 0, 30),
                intArrayOf(20, 25, 30, 0)
            )
        )

        val ts = TravellingSalesman()
        val trip = ts.shortestRoundtrip(graph, 0)
        println(trip)
    }
}

// https://www.geeksforgeeks.org/traveling-salesman-problem-tsp-implementation/
// Time: O(N * N!)
// Space: O(N)
class TravellingSalesman {
    fun shortestRoundtrip(graph: DirectedWeightedGraphWithAdjMatrix, start: Int): PathWithSum {
        val visited = BooleanArray(graph.matrix.size)
        val path: MutableList<Int> = mutableListOf()

        return shortestRoundtripRec(graph, start, start, visited, path, 0)
    }

    private fun shortestRoundtripRec(
        graph: DirectedWeightedGraphWithAdjMatrix,
        start: Int,
        cur: Int,
        visited: BooleanArray,
        path: MutableList<Int>,
        sum: Int
    ): PathWithSum {
        visited[cur] = true
        path.add(cur)

        if (graph.matrix.size == path.size) {
            // include starting point as ending point again
            path.add(start)
            val pathWithSum = PathWithSum(path.toMutableList(), sum + graph.matrix[cur][start])

            path.removeAt(path.lastIndex) // remove end
            path.removeAt(path.lastIndex) // remove last city before end

            visited[cur] = false

            return pathWithSum
        }

        var shortest = PathWithSum(path, Int.MAX_VALUE)

        graph.matrix.indices.forEach {
            // no self-loops, early returns to start, or revisits:
            if (it == cur || it == start || visited[it]) return@forEach

            val candidate = shortestRoundtripRec(
                graph, start, it, visited, path, sum + graph.matrix[cur][it]
            )

            shortest = if (candidate.sum < shortest.sum) candidate else shortest
        }

        path.remove(cur)
        visited[cur] = false

        return shortest
    }

    data class PathWithSum(
        val path: MutableList<Int>,
        val sum: Int
    )
}
