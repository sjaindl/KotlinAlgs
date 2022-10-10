package com.sjaindl.kotlinalgsandroid.graph

import java.util.*

/*
    MinSpanningTreeKruskal - MST weighted undirected graph
    pick smallest edge (PQ)
    check for cycle (DFS or UnionFind)
 */

//https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
class MinSpanningTreeKruskal<T> {
    fun minSpanningTree(graph: Graph<T>): List<WeightedEdge<T>> {
        val mst: MutableList<WeightedEdge<T>> = mutableListOf()
        val unionFind = UnionFind(graph) // O(V)
        val sortedEdges = PriorityQueue(graph.edges) // O(E log E)

        // O (V * (log V + log E)) = O(V log E)
        // loop is max E because we may consider all edges!!
        while (mst.size < graph.vertices.size - 1) { //V-1 vertices form a MST .. O(V)
            val possibleEdge = sortedEdges.poll() // O(log E)
            val component1 = unionFind.find(possibleEdge.from.value) // O(log V)
            val component2 = unionFind.find(possibleEdge.to.value) // O(log V)

            if (component1 == component2) continue //would form a cycle
            mst.add(possibleEdge)
            unionFind.union(component1, component2) // O(1)
        }

        return mst
    }

}

class UnionFind<T>//initially every vertice has its own component
    (graph: Graph<T>) {
    private val components: MutableMap<T, T> = mutableMapOf()
    private val sizes: MutableMap<T, Int> = mutableMapOf()

    init {
        graph.vertices.forEach {
            //initially every vertice has its own component
            components[it.value] = it.value
            sizes[it.value] = 1
        }
    }

    fun find(component: T): T {
        var curComponent = component
        while (components[curComponent] != curComponent) {
            curComponent = components[curComponent]!!
        }

        return curComponent
    }

    fun union(first: T, second: T) {
        //union by rank! -> O(log V)
        if (sizes[first]!! < sizes[second]!!) {
            components[first] = components[second]!!
            sizes[second] = sizes[first]!! + sizes[second]!!
        } else {
            components[second] = components[first]!!
            sizes[first] = sizes[first]!! + sizes[second]!!
        }
    }
}

data class Vertice<T>(
    val value: T,
)

class Graph<T>(val vertices: List<Vertice<T>>) {
    var neighbors: MutableMap<T, MutableList<WeightedEdge<T>>> = mutableMapOf()
    var edges: MutableList<WeightedEdge<T>> = mutableListOf()

    fun addEdge(edge: WeightedEdge<T>) {
        val neighboursFrom = neighbors.getOrDefault(edge.from.value, mutableListOf())
        neighboursFrom.add(edge)
        neighbors[edge.from.value] = neighboursFrom

        val neighboursTo = neighbors.getOrDefault(edge.to.value, mutableListOf())
        neighboursTo.add(edge)
        neighbors[edge.to.value] = neighboursTo

        edges.add(edge)
    }
}

data class WeightedEdge<T>(
    val from: Vertice<T>,
    val to: Vertice<T>,
    val weight: Int
) : Comparable<WeightedEdge<T>> {
    override fun compareTo(other: WeightedEdge<T>): Int {
        return when {
            this.weight < other.weight -> -1
            this.weight == other.weight -> 0
            else -> 1
        }
    }
}

val vertice0 = Vertice(0)
val vertice1 = Vertice(1)
val vertice2 = Vertice(2)
val vertice3 = Vertice(3)
val vertice4 = Vertice(4)
val vertice5 = Vertice(5)
val vertice6 = Vertice(6)
val vertice7 = Vertice(7)
val vertice8 = Vertice(8)

val vertices = listOf(
    vertice0, vertice1, vertice2,
    vertice3, vertice4, vertice5,
    vertice6, vertice7, vertice8
)

val graph = Graph(vertices)

graph.addEdge(WeightedEdge(vertice0, vertice1, 4))
graph.addEdge(WeightedEdge(vertice0, vertice7, 8))
graph.addEdge(WeightedEdge(vertice1, vertice2, 8))
graph.addEdge(WeightedEdge(vertice1, vertice7, 11))
graph.addEdge(WeightedEdge(vertice2, vertice3, 7))
graph.addEdge(WeightedEdge(vertice2, vertice5, 4))
graph.addEdge(WeightedEdge(vertice2, vertice8, 2))
graph.addEdge(WeightedEdge(vertice3, vertice4, 9))
graph.addEdge(WeightedEdge(vertice3, vertice5, 14))
graph.addEdge(WeightedEdge(vertice4, vertice5, 10))
graph.addEdge(WeightedEdge(vertice5, vertice6, 2))
graph.addEdge(WeightedEdge(vertice6, vertice7, 1))
graph.addEdge(WeightedEdge(vertice6, vertice8, 6))
graph.addEdge(WeightedEdge(vertice7, vertice8, 7))

MinSpanningTreeKruskal<Int>().minSpanningTree(graph)
