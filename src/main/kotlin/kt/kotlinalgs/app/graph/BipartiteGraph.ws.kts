package com.sjaindl.kotlinalgsandroid.graph

import java.util.*

// !! https://www.geeksforgeeks.org/top-10-interview-question-depth-first-search-dfs/
/*
    Bipartite Graph
    M-coloring backtracking with max colors = 2 -> O(2^V)
    BFS: O(E+V)
    DFS: O(E+V)
 */

//https://www.geeksforgeeks.org/bipartite-graph/
class BipartiteGraph<T> {
    fun isBipartiteBFS(graph: Graph<T>): Boolean {
        if (graph.vertices.size <= 2) return true // [0:0, 1:1, 2:-1, 3:-1, 4:-1, 5:-1]

        val queue = LinkedList<Vertice<T>>() // [0]

        graph.vertices.forEach {
            if (it.color != -1) return@forEach

            queue.add(graph.vertices[0])

            while (!queue.isEmpty()) {
                val next = queue.remove()
                if (next.color == -1) next.color = 0

                for (neighbour in graph.neighboursOf(next)) { //1,5
                    if (neighbour.from.color == neighbour.to.color) return false
                    if (neighbour.to.color == -1) {
                        neighbour.to.color = if (neighbour.from.color == 0) 1 else 0
                        queue.add(neighbour.to)
                    }
                }
            }
        }

        return true
    }

    fun isBipartiteDFS(graph: Graph<T>): Boolean {
        if (graph.vertices.size <= 2) return true

        graph.vertices.forEach {
            if (it.color == -1) {
                if (!isBipartiteDFSRec(graph, it, 0)) return false
            }
        }

        return true
    }

    private fun isBipartiteDFSRec(graph: Graph<T>, vertice: Vertice<T>, color: Int): Boolean {
        vertice.color = color
        println("DFS: ${vertice.value}: ${vertice.color}")
        graph.neighboursOf(vertice).forEach {
            println("DFS NGB: ${it.to.value}: ${it.to.color}")
            if (it.to.color == color) return false
            if (it.to.color == -1) {
                val otherColor = if (color == 0) 1 else 0
                if (!isBipartiteDFSRec(graph, it.to, otherColor)) return false
            }
        }

        return true
    }
}

data class Vertice<T>(
    val value: T,
    var color: Int = -1
)

class Graph<T>(val vertices: List<Vertice<T>>) {
    var neighbors: MutableMap<T, MutableList<Edge<T>>> = mutableMapOf()
    var edges: MutableList<Edge<T>> = mutableListOf()

    fun addEdge(edge: Edge<T>) {
        val neighbours = neighbors.getOrDefault(edge.from.value, mutableListOf())
        neighbours.add(edge)
        neighbors[edge.from.value] = neighbours

        edges.add(edge)
    }

    fun neighboursOf(vertice: Vertice<T>): MutableList<Edge<T>> {
        return neighbors[vertice.value] ?: mutableListOf()
    }
}

data class Edge<T>(
    val from: Vertice<T>,
    val to: Vertice<T>
)

val vertice0 = Vertice(0)
val vertice1 = Vertice(1)
val vertice2 = Vertice(2)
val vertice3 = Vertice(3)
val vertice4 = Vertice(4)
val vertice5 = Vertice(5)

val vertices = listOf(
    vertice0, vertice1, vertice2,
    vertice3, vertice4, vertice5
)

val graph = Graph(vertices)

graph.addEdge(Edge(vertice0, vertice1))
graph.addEdge(Edge(vertice1, vertice2))
graph.addEdge(Edge(vertice2, vertice3))
graph.addEdge(Edge(vertice3, vertice4))
graph.addEdge(Edge(vertice4, vertice5))
graph.addEdge(Edge(vertice5, vertice0))

BipartiteGraph<Int>().isBipartiteBFS(graph)

graph.vertices.forEach {
    println("${it.value}: ${it.color}")
    it.color = -1
}

graph.neighbors

BipartiteGraph<Int>().isBipartiteDFS(graph)

graph.vertices.forEach {
    println("${it.value}: ${it.color}")
}

val vertice0_2 = Vertice(0)
val vertice1_2 = Vertice(1)
val vertice2_2 = Vertice(2)
val vertice3_2 = Vertice(3)
val vertice4_2 = Vertice(4)

val vertices2 = listOf(
    vertice0_2, vertice1_2, vertice2_2,
    vertice3_2, vertice4_2
)

val graph2 = Graph(vertices2)

graph2.addEdge(Edge(vertice0_2, vertice1_2))
graph2.addEdge(Edge(vertice1_2, vertice2_2))
graph2.addEdge(Edge(vertice2_2, vertice3_2))
graph2.addEdge(Edge(vertice3_2, vertice4_2))
graph2.addEdge(Edge(vertice4_2, vertice0_2))

BipartiteGraph<Int>().isBipartiteBFS(graph2)

graph2.vertices.forEach {
    println("${it.value}: ${it.color}")
    it.color = -1
}

BipartiteGraph<Int>().isBipartiteDFS(graph2)

graph2.vertices.forEach {
    println("${it.value}: ${it.color}")
}
