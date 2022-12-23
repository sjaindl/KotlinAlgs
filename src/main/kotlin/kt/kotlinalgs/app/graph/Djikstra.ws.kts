package com.sjaindl.kotlinalgsandroid.graph

import java.util.*

/*
    Djikstra
    shortest path with weighted positive edges between start node and all other nodes.
    Using PQ (array: V * V = V^2, heap: V log V + E log V = O(E+V) log V remove min + update neighbor nodes prio)
 */

// https://www.geeksforgeeks.org/dijkstras-algorithm-for-adjacency-list-representation-greedy-algo-8/
class Djikstra<T> {
    fun shortestPath(graph: Graph<T>, source: Vertice<T>, target: Vertice<T>): List<T> {
        // link to best prev vertice in path:
        val previous: MutableMap<T, Vertice<T>> = mutableMapOf()

        //remaining vertices to check, sorted by total weight
        val remaining = PriorityQueue<Vertice<T>>()

        graph.vertices.forEach {
            it.pathCost = Int.MAX_VALUE
        }
        source.pathCost = 0 // all other costs init. to int max value

        remaining.add(source)

        while (!remaining.isEmpty()) {
            val current = remaining.poll() ?: continue
            //println(current.value)

            graph.neighbors[current.value]?.forEach { edge ->
                val otherBest = edge.to.pathCost
                val curPathCost = current.pathCost + edge.weight
                
                if (curPathCost < otherBest) {
                    previous[edge.to.value] = current
                    edge.to.pathCost = curPathCost
                    remaining.add(edge.to)
                    // edge ${edge.from.value}-${edge.to.value}: ${edge.to.pathCost}")
                }
            }
        }

        return backtrack(source, target, previous)
    }

    private fun backtrack(
        source: Vertice<T>,
        target: Vertice<T>,
        previous: Map<T, Vertice<T>>
    ): List<T> {
        //backtrack best path
        val bestPath: MutableList<T> = mutableListOf()
        var curNode: Vertice<T>? = target
        println("cost: ${target.pathCost}")
        while (curNode != null && curNode != source) {
            bestPath.add(curNode.value)
            curNode = previous[curNode.value]
        }

        if (curNode == source) {
            bestPath.add(curNode.value)
            return bestPath
        }

        return emptyList()
    }
}

data class Vertice<T>(
    val value: T,
    var pathCost: Int = Int.MAX_VALUE
) : Comparable<Vertice<T>> {
    override fun compareTo(other: Vertice<T>): Int {
        if (pathCost == other.pathCost) return 0
        else if (pathCost < other.pathCost) return -1
        return 1
    }
}

class Graph<T>(val vertices: List<Vertice<T>>) {
    var neighbors: MutableMap<T, MutableList<DirectedWeightedEdge<T>>> = mutableMapOf()

    fun addEdge(edge: DirectedWeightedEdge<T>) {
        val neighboursOf = neighbors.getOrDefault(edge.from.value, mutableListOf())
        neighboursOf.add(edge)
        neighbors[edge.from.value] = neighboursOf
    }
}

data class DirectedWeightedEdge<T>(
    val weight: Int,
    val from: Vertice<T>,
    val to: Vertice<T>
)

val verticeA = Vertice("a")
val verticeB = Vertice("b")
val verticeC = Vertice("c")
val verticeD = Vertice("d")
val verticeE = Vertice("e")
val verticeF = Vertice("f")
val verticeG = Vertice("g")
val verticeH = Vertice("h")
val verticeI = Vertice("i")

val vertices = listOf(
    verticeA, verticeB, verticeC,
    verticeD, verticeE, verticeF,
    verticeG, verticeH, verticeI
)

val graph = Graph(vertices)

graph.addEdge(DirectedWeightedEdge(5, verticeA, verticeB))
graph.addEdge(DirectedWeightedEdge(3, verticeA, verticeC))
graph.addEdge(DirectedWeightedEdge(2, verticeA, verticeE))
graph.addEdge(DirectedWeightedEdge(2, verticeB, verticeD))
graph.addEdge(DirectedWeightedEdge(1, verticeC, verticeB))
graph.addEdge(DirectedWeightedEdge(1, verticeC, verticeD))
graph.addEdge(DirectedWeightedEdge(1, verticeD, verticeA))
graph.addEdge(DirectedWeightedEdge(2, verticeD, verticeG))
graph.addEdge(DirectedWeightedEdge(1, verticeD, verticeH))
graph.addEdge(DirectedWeightedEdge(1, verticeE, verticeA))
graph.addEdge(DirectedWeightedEdge(4, verticeE, verticeH))
graph.addEdge(DirectedWeightedEdge(7, verticeE, verticeI))
graph.addEdge(DirectedWeightedEdge(3, verticeF, verticeB))
graph.addEdge(DirectedWeightedEdge(1, verticeF, verticeG))
graph.addEdge(DirectedWeightedEdge(3, verticeG, verticeC))
graph.addEdge(DirectedWeightedEdge(2, verticeG, verticeI))
graph.addEdge(DirectedWeightedEdge(2, verticeH, verticeC))
graph.addEdge(DirectedWeightedEdge(2, verticeH, verticeF))
graph.addEdge(DirectedWeightedEdge(2, verticeH, verticeG))

Djikstra<String>().shortestPath(graph, verticeA, verticeI)
