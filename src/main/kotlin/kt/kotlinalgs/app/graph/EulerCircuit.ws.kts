package kt.kotlinalgs.app.graph

Solution().test()

data class DirectedWeightedGraphWithAdjMatrix(val matrix: Array<IntArray>)

class Solution {
    fun test() {
        println("Test")

        val graph = DirectedWeightedGraphWithAdjMatrix(
            arrayOf(
                intArrayOf(0, 1, 1, 1, 1),
                intArrayOf(1, 0, 1, 0, 0),
                intArrayOf(1, 1, 0, 0, 0),
                intArrayOf(1, 0, 0, 0, 1),
                intArrayOf(1, 0, 0, 1, 0)
            )
        )

        val es = EulerianCycle()
        val path = es.hasCycle(graph)
        println(path)
    }
}

class EulerianCycle {
    // https://www.geeksforgeeks.org/eulerian-path-and-circuit/
    // O(V+E) for connectivity + degree checks
    fun hasCycle(graph: DirectedWeightedGraphWithAdjMatrix): Boolean {
        /* eulerian cycle:
            all vertices have even degree
            all vertices (except 0-edge) connected

        eulerian path:
            all vertices except 2 have even degree
            all vertices (except 0-edge) connected
         */

        val degress = countDegrees(graph)
        val oddCount = degress.count { it % 2 == 1 }

        println(degress.map { it })
        println(oddCount)

        if (oddCount > 0) return false

        val visited = BooleanArray(graph.matrix.size)
        val firstWithEdges = degress.indexOfFirst { it > 0 }
        if (firstWithEdges == -1) return true // no edges -> counts as cycle!

        dfs(graph, visited, firstWithEdges)

        println(visited.map {
            it
        })

        for (vertice in graph.matrix.indices) {
            // unconnected node with edges -> no cycle possible!
            if (!visited[vertice] && degress[vertice] != 0) return false
        }

        //   all vertices have even degree
        // + all vertices (except 0-edge) connected
        return true
    }

    private fun dfs(
        graph: DirectedWeightedGraphWithAdjMatrix,
        visited: BooleanArray,
        vertice: Int
    ) {
        visited[vertice] = true

        for (nb in graph.matrix.indices) {
            if (graph.matrix[vertice][nb] == 1 && !visited[nb]) {
                dfs(graph, visited, nb)
            }
        }
    }

    private fun countDegrees(graph: DirectedWeightedGraphWithAdjMatrix): IntArray {
        val degrees = IntArray(graph.matrix.size) { 0 }

        for (vertice in graph.matrix.indices) {
            for (nb in graph.matrix.indices) {
                if (graph.matrix[vertice][nb] == 1) {
                    degrees[vertice]++
                }
            }
        }

        return degrees
    }
}

/*
Directed graph:
https://www.geeksforgeeks.org/euler-circuit-directed-graph/

1) All vertices with nonzero degree belong to a single strongly connected component. -> Kosaraju's algo
2) In degree is equal to the out degree for every vertex. --> out = adjList size, in = array/vertice property
O(E+V)

 */
