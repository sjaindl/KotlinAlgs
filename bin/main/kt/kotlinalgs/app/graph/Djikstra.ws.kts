package kt.kotlinalgs.app.graph

import java.util.*

println("test")

/*
    Djikstra
    shortest path with weighted positive edges between start node and all other nodes.
    Using PQ (array: V * V = V^2, heap: V log V + E log V = remove min + update neighbor nodes prio)
 */
class Djikstra<T> {
    fun shortestPath(graph: Graph<T>, source: Vertice<T>, target: Vertice<T>) {
        var previous: MutableList<Vertice<T>> = mutableListOf()
        var weights: MutableList<Int> = mutableListOf()

        var priorityQueue = PriorityQueue<DirectedWeightedEdge>()

    }
}

data class Vertice<T>(
    val value: T
)

class Graph<T>(var vertices: List<Vertice<T>>) {
    var neighbors: MutableMap<Vertice<T>, MutableList<DirectedWeightedEdge<T>>> = mutableMapOf()

    fun addEdge(edge: DirectedWeightedEdge<T>) {

        val neighboursOf = neighbors.getOrDefault(edge.from, mutableListOf())
        neighboursOf.add(edge)

        neighbors[edge.from] = neighboursOf
    }
}

data class DirectedWeightedEdge<T>(
    val weight: Int,
    val from: Vertice<T>,
    val to: Vertice<T>
)

println("test")