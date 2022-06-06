package kt.kotlinalgs.app.graph

Solution().test()

data class DirectedWeightedGraphWithAdjMatrix(val matrix: Array<IntArray>)

class Solution {
    fun test() {
        println("Test")

        val graph = DirectedWeightedGraphWithAdjMatrix(
            arrayOf(
                intArrayOf(0, 1, 0, 1, 0),
                intArrayOf(1, 0, 1, 1, 1),
                intArrayOf(0, 1, 0, 0, 1),
                intArrayOf(1, 1, 0, 0, 1),
                intArrayOf(0, 1, 1, 1, 0)
            )
        )

        val hs = HamiltonianCycle()
        val path = hs.path(graph)
        println(path)
    }
}

class HamiltonianCycle {
    // https://www.geeksforgeeks.org/hamiltonian-cycle-backtracking-6/
    // worst case O(V * V!) .. but backtracking improves this naive algo much
    fun path(graph: DirectedWeightedGraphWithAdjMatrix): List<Int>? {
        // backtracking
        if (graph.matrix.isEmpty()) return emptyList()
        if (graph.matrix.size == 1) return listOf(0)

        val visited: MutableSet<Int> = mutableSetOf()
        val builtPath: MutableList<Int> = mutableListOf()

        return pathRec(graph, builtPath, visited, 0)
    }

    private fun pathRec(
        graph: DirectedWeightedGraphWithAdjMatrix,
        builtPath: MutableList<Int>,
        visited: MutableSet<Int>,
        current: Int
    ): List<Int>? {
        if (builtPath.size == graph.matrix.size) {
            // BC
            if (graph.matrix[current][0] == 0) return null

            builtPath.add(0)
            val result = builtPath.toList()
            builtPath.removeAt(builtPath.lastIndex)

            return result
        }

        builtPath.add(current)
        visited.add(current)

        println(builtPath)

        graph.matrix[current].forEach { neighbour ->
            if (neighbour == current
                || graph.matrix[current][neighbour] == 0
                || visited.contains(neighbour)
            ) return@forEach

            println("check $current - $neighbour")

            pathRec(graph, builtPath, visited, neighbour)?.let {
                return it
            }
        }

        builtPath.remove(current)
        visited.remove(current)

        return null
    }
}
