package com.sjaindl.kotlinalgsandroid.graph

/*
    https://www.geeksforgeeks.org/detect-cycle-undirected-graph
 */
// Time Complexity: O(V+E)  DFS with adj. list.
// Space Complexity: O(V)   visited set

class CycleDetection {
    fun hasCycle(graph: Graph): Boolean {
        val visited: MutableSet<Int> = mutableSetOf()

        graph.vertices.forEach {
            if (!visited.contains(it.value)) {
                if (hasCycleRec(graph, null, it, visited)) return true
            }
        }

        return false
    }

    private fun hasCycleRec(
        graph: Graph,
        parent: Vertice?,
        vertice: Vertice,
        visited: MutableSet<Int>
    ): Boolean {
        if (visited.contains(vertice.value)) return true

        visited.add(vertice.value)

        graph.neighbours(vertice).forEach {
            if (it.value == parent?.value) return@forEach

            if (hasCycleRec(graph, vertice, it, visited)) return true
        }

        return false
    }
}

class Graph(val vertices: List<Vertice>) {
    var adjList: MutableMap<Int, MutableList<Vertice>> = mutableMapOf()

    fun addEdge(from: Vertice, to: Vertice) {
        val list = adjList.getOrDefault(from.value, mutableListOf())
        list.add(to)
        adjList[from.value] = list
    }

    fun neighbours(vertice: Vertice): List<Vertice> {
        return adjList.getOrDefault(vertice.value, listOf())
    }
}

data class Vertice(
    val value: Int
)

val vertice0 = Vertice(0)
val vertice1 = Vertice(1)
val vertice2 = Vertice(2)
val vertice3 = Vertice(3)

val vertices = listOf(
    vertice0, vertice1, vertice2, vertice3
)

val graph = Graph(vertices)

graph.addEdge(vertice0, vertice1)
graph.addEdge(vertice0, vertice2)
graph.addEdge(vertice1, vertice2)
graph.addEdge(vertice2, vertice0)
graph.addEdge(vertice3, vertice3)

println(CycleDetection().hasCycle(graph))
