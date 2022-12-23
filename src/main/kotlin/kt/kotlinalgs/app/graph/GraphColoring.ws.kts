package com.sjaindl.kotlinalgsandroid.graph

/*
    GraphColoring - Color graph with min d(egree max)+1 colors.
    Worst Case runtime: O(V^2 + E) - if fully connected graph?
 */

//https://www.geeksforgeeks.org/graph-coloring-set-2-greedy-algorithm/
class GraphColoring<T> {
    fun color(graph: Graph<T>) {
        if (graph.vertices.isEmpty()) return
        //assign first color to vertex 0
        graph.vertices[0].color = 0

        //color vertices with lowest color of adjacent ones
        for (verticeIndex in 1 until graph.vertices.size) {
            val vertice = graph.vertices[verticeIndex]
            val colorSet: MutableSet<Int> = mutableSetOf()
            for (neighbour in graph.neighbours(vertice)) {
                if (neighbour.color > -1) colorSet.add(neighbour.color)
            }
            
            var minColor = -1
            val sorted = colorSet.sorted()
            println("${vertice.id}: $sorted")

            for (color in sorted) {
                if (color > minColor + 1) {
                    break
                } else {
                    minColor++
                }
            }

            vertice.color = minColor + 1
        }
    }
}

data class Vertice<T>(
    val id: T,
    var color: Int = -1
)

data class Edge<T>(
    val from: Vertice<T>,
    val to: Vertice<T>
)

class Graph<T>(val vertices: List<Vertice<T>>) {
    var edges: MutableList<Edge<T>> = mutableListOf()
    private var adjList: MutableMap<T, MutableList<Vertice<T>>> = mutableMapOf()

    fun addEdge(from: Vertice<T>, to: Vertice<T>) {
        val list = adjList.getOrDefault(from.id, mutableListOf())
        list.add(to)
        adjList[from.id] = list

        val list2 = adjList.getOrDefault(to.id, mutableListOf())
        list2.add(from)
        adjList[to.id] = list2
    }

    fun neighbours(vertice: Vertice<T>): List<Vertice<T>> {
        return adjList[vertice.id] ?: emptyList()
    }
}

val vertices = listOf(
    Vertice(0),
    Vertice(1),
    Vertice(2),
    Vertice(3),
    Vertice(4)
)

val graph = Graph(vertices)

graph.addEdge(vertices[0], vertices[1])
graph.addEdge(vertices[0], vertices[2])
graph.addEdge(vertices[1], vertices[0])
graph.addEdge(vertices[1], vertices[2])
graph.addEdge(vertices[1], vertices[3])
graph.addEdge(vertices[2], vertices[0])
graph.addEdge(vertices[2], vertices[1])
graph.addEdge(vertices[2], vertices[3])
graph.addEdge(vertices[3], vertices[1])
graph.addEdge(vertices[3], vertices[2])
graph.addEdge(vertices[3], vertices[4])
graph.addEdge(vertices[4], vertices[3])

GraphColoring<Int>().color(graph)

graph.vertices.forEach {
    println("${it.id}: ${it.color}")
}
