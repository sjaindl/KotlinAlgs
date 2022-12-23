package com.sjaindl.kotlinalgsandroid.graph

/*
    HamiltonianCycle - Circular path through all vertices (0 -> N -> 0)

 */

//https://www.geeksforgeeks.org/hamiltonian-cycle-backtracking-6/
class HamiltonianCycle {
    fun path(graph: Array<IntArray>): List<Int> {
/*
1. init path (size V)
2. add v0
3. for v1-n: check if valid (not already in path + adj to prev. vertice)
4. if valid: add to path
5. if all invalid: backtrack

 */
        if (graph.isEmpty() || graph[0].size != graph.size) return emptyList()

        val path: MutableList<Int> = mutableListOf()
        path.add(0)

        pathRecursive(graph, path, 0)
        return path
    }

    private fun pathRecursive(
        graph: Array<IntArray>,
        path: MutableList<Int>,
        lastVertice: Int
    ): Boolean {
        if (path.size == graph.size) {
            if (graph[lastVertice][0] != 0) {
                //found a hamiltonian cycle! include first node.
                path.add(0)
                return true
            }
            return false
        }

        for (vertice in 1 until graph.size) {
            if (isValid(graph, path, lastVertice, vertice)) {
                path.add(vertice)
                if (pathRecursive(graph, path, vertice)) return true
                path.remove(vertice)
            }
        }

        return false
    }

    //valid (not already in path + adj to prev. vertice)
    private fun isValid(
        graph: Array<IntArray>,
        path: MutableList<Int>,
        lastVertice: Int,
        vertice: Int
    ): Boolean {
        if (vertice == lastVertice || graph[lastVertice][vertice] == 0 || path.contains(vertice)) return false
        return true
    }
}

val graph = arrayOf(
    intArrayOf(0, 1, 0, 1, 0),
    intArrayOf(1, 0, 1, 1, 1),
    intArrayOf(0, 1, 0, 0, 1),
    intArrayOf(1, 1, 0, 0, 1),
    intArrayOf(0, 1, 1, 1, 0)
)

val mst = HamiltonianCycle().path(graph)

mst.forEach {
    println("$it")
}
