package com.sjaindl.kotlinalgsandroid.graph

/*
    EulerianCycle - Circular path through all edges
    O(E+V) for DFS, using properties
 */

//https://www.geeksforgeeks.org/eulerian-path-and-circuit/
class EulerianCycle {
    /*
        Eulerian Cycle (undirected graph):
        1. Graph is connected (=all non-zero-edge vertices)
        2. all vertices have even degree

        Eulerian Path (undirected graph):
        1. Graph is connected (=all non-zero-edge vertices)
        2. only 0 (=cycle!) or 2 vertices with odd degree (1 isn't possible)
    */

    fun hasCycle(graph: Array<IntArray>): Boolean {
        println(isConnected(graph))
        println(oddDegreeCount(graph))

        if (!isConnected(graph)) return false
        val oddCount = oddDegreeCount(graph)
        return oddCount == 0 || oddCount == 2
    }

    private fun isConnected(graph: Array<IntArray>): Boolean {
        val visited: MutableSet<Int> = mutableSetOf()
        visited.add(0)
        return isConnectedRecursive(graph, visited, 0)
    }

    private fun isConnectedRecursive(
        graph: Array<IntArray>,
        visited: MutableSet<Int>,
        vertice: Int
    ): Boolean {
        if (graph.size == visited.size) return true

        for (neighbour in graph.indices) {
            if (!visited.contains(neighbour) && graph[vertice][neighbour] != 0) {
                visited.add(neighbour)
                if (isConnectedRecursive(graph, visited, neighbour)) return true
            }
        }

        return false
    }

    private fun oddDegreeCount(graph: Array<IntArray>): Int {
        var count = 0
        for (vertice in graph.indices) {
            var neighbourCount = 0
            for (possibleNeighbour in graph.indices) {
                if (possibleNeighbour != 0) neighbourCount++
            }
            if (neighbourCount % 2 == 1) count++
        }

        return count
    }
}

val graph = arrayOf(
    intArrayOf(0, 1, 1, 1, 1),
    intArrayOf(1, 0, 1, 0, 0),
    intArrayOf(1, 1, 0, 0, 0),
    intArrayOf(1, 0, 0, 0, 1),
    intArrayOf(1, 0, 0, 1, 0)
)

EulerianCycle().hasCycle(graph)
