package com.sjaindl.kotlinalgsandroid.graph

import java.util.*

/*
    StronglyConnectedComponent
    1. DFS
    2. reverse graph edges
    3. DFS on reversed graph
    O(E+V) time
 */

//https://www.geeksforgeeks.org/strongly-connected-components/
class StronglyConnectedComponent<T> {

    // returns list of SCC's (one SCC contains all vertices)
    fun stronglyConnectedComponents(graph: Graph<T>): List<List<Vertice<T>>> {
        // 1. DFS to find build stack -> =(E+V)
        val stack = buildStack(graph)

        // 2. reverse graph (edges) -> =(E)
        val reversedGraph = reverseGraph(graph)

        // 3. DFS on reversed graph -> =(E+V)
        return dfsReversed(reversedGraph, stack)
    }

    private fun buildStack(graph: Graph<T>): Stack<Vertice<T>> {
        val stack = Stack<Vertice<T>>()
        val visited: MutableSet<T> = mutableSetOf()

        graph.vertices.forEach {
            buildStackRec(graph, it, stack, visited)
        }

        return stack
    }

    private fun buildStackRec(
        graph: Graph<T>,
        vertice: Vertice<T>,
        stack: Stack<Vertice<T>>,
        visited: MutableSet<T>
    ) {
        if (visited.contains(vertice.value)) return

        visited.add(vertice.value)

        graph.neighbors[vertice.value]?.forEach {
            buildStackRec(graph, it.to, stack, visited)
        }

        stack.push(vertice)
    }

    private fun reverseGraph(graph: Graph<T>): Graph<T> {
        val newGraph = Graph(graph.vertices)

        graph.edges.forEach {
            val newEdge = DirectedEdge(it.to, it.from)
            newGraph.addEdge(newEdge)
        }

        return newGraph
    }

    private fun dfsReversed(
        reversedGraph: Graph<T>,
        stack: Stack<Vertice<T>>
    ): List<List<Vertice<T>>> {
        val stronglyConnectedComponents: MutableList<List<Vertice<T>>> = mutableListOf()
        val visited: MutableSet<T> = mutableSetOf()

        while (!stack.isEmpty()) {
            val vertice = stack.pop()
            if (!visited.contains(vertice.value)) {
                val list: MutableList<Vertice<T>> = mutableListOf()
                dfsReversedRec(reversedGraph, vertice, visited, list)
                stronglyConnectedComponents.add(list)
            }
        }

        return stronglyConnectedComponents
    }

    private fun dfsReversedRec(
        graph: Graph<T>,
        vertice: Vertice<T>,
        visited: MutableSet<T>,
        list: MutableList<Vertice<T>>
    ) {
        if (visited.contains(vertice.value)) return

        visited.add(vertice.value)
        list.add(vertice)

        graph.neighbors[vertice.value]?.forEach {
            dfsReversedRec(graph, it.to, visited, list)
        }
    }
}

data class Vertice<T>(
    val value: T,
)

class Graph<T>(val vertices: List<Vertice<T>>) {
    var neighbors: MutableMap<T, MutableList<DirectedEdge<T>>> = mutableMapOf()
    var edges: MutableList<DirectedEdge<T>> = mutableListOf()

    fun addEdge(edge: DirectedEdge<T>) {
        val neighboursOf = neighbors.getOrDefault(edge.from.value, mutableListOf())
        neighboursOf.add(edge)
        neighbors[edge.from.value] = neighboursOf

        edges.add(edge)
    }
}

data class DirectedEdge<T>(
    val from: Vertice<T>,
    val to: Vertice<T>
)

val vertice0 = Vertice(0)
val vertice1 = Vertice(1)
val vertice2 = Vertice(2)
val vertice3 = Vertice(3)
val vertice4 = Vertice(4)

val vertices = listOf(
    vertice0, vertice1, vertice2,
    vertice3, vertice4
)

val graph = Graph(vertices)

graph.addEdge(DirectedEdge(vertice0, vertice2))
graph.addEdge(DirectedEdge(vertice0, vertice3))
graph.addEdge(DirectedEdge(vertice1, vertice0))
graph.addEdge(DirectedEdge(vertice2, vertice1))
graph.addEdge(DirectedEdge(vertice3, vertice4))

StronglyConnectedComponent<Int>().stronglyConnectedComponents(graph)
