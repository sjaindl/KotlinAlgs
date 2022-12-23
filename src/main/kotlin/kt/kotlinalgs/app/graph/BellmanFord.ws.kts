package com.sjaindl.kotlinalgsandroid.graph

/*
    BellmannFord
    shortest path with weighted negative edges between start node and all other nodes.
    Using array: O(V*E)
 */

//https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23/
class BellmannFord<T> {
    fun shortestPath(graph: Graph<T>, source: Vertice<T>, target: Vertice<T>): Int {
        val shortestPaths: MutableMap<T, Int> = mutableMapOf() //vertice_val to shortest_path

        graph.vertices.forEach {
            shortestPaths[it.value] = Int.MAX_VALUE
        }
        shortestPaths[source.value] = 0

        // Relax edges
        for (iteration in 1 until graph.vertices.size) {
            graph.edges.forEach {
                val curDist = shortestPaths[it.from.value]!! + it.weight
                if (shortestPaths[it.from.value]!! != Int.MAX_VALUE && curDist < shortestPaths[it.to.value]!!) {
                    shortestPaths[it.to.value] = curDist
                }
            }
        }

        //check for negative cycles!
        graph.edges.forEach {
            val curDist = shortestPaths[it.from.value]!! + it.weight
            if (curDist != Int.MAX_VALUE && curDist < shortestPaths[it.to.value]!!) {
                println("negative cycle found!!")
                //throw IllegalStateException("negative cycle found!!")
                return Int.MIN_VALUE
            }
        }


        return shortestPaths[target.value]!!
    }
}

data class Vertice<T>(
    val value: T,
)

class Graph<T>(val vertices: List<Vertice<T>>) {
    var neighbors: MutableMap<T, MutableList<DirectedWeightedEdge<T>>> = mutableMapOf()
    var edges: MutableList<DirectedWeightedEdge<T>> = mutableListOf()

    fun addEdge(edge: DirectedWeightedEdge<T>) {
        val neighboursOf = neighbors.getOrDefault(edge.from.value, mutableListOf())
        neighboursOf.add(edge)
        neighbors[edge.from.value] = neighboursOf

        edges.add(edge)
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

val vertices = listOf(
    verticeA, verticeB, verticeC,
    verticeD, verticeE
)

val graph = Graph(vertices)

graph.addEdge(DirectedWeightedEdge(-1, verticeA, verticeB))
graph.addEdge(DirectedWeightedEdge(4, verticeA, verticeC))
graph.addEdge(DirectedWeightedEdge(2, verticeB, verticeE))
graph.addEdge(DirectedWeightedEdge(2, verticeB, verticeD))
graph.addEdge(DirectedWeightedEdge(3, verticeB, verticeC))
graph.addEdge(DirectedWeightedEdge(1, verticeD, verticeB))
graph.addEdge(DirectedWeightedEdge(5, verticeD, verticeC))
graph.addEdge(DirectedWeightedEdge(-3, verticeE, verticeD))

BellmannFord<String>().shortestPath(graph, verticeA, verticeE)
BellmannFord<String>().shortestPath(graph, verticeA, verticeB)
BellmannFord<String>().shortestPath(graph, verticeA, verticeC)
BellmannFord<String>().shortestPath(graph, verticeE, verticeC)
