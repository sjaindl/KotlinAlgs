package kt.kotlinalgs.app.graph

import java.util.*

println("Test")

val vertice0 = Vertice(0)
val vertice1 = Vertice(1)
val vertice2 = Vertice(2)
val vertice3 = Vertice(3)
val vertice4 = Vertice(4)
val vertice5 = Vertice(5)
val vertice6 = Vertice(6)

val graph = DirectedGraphWithAdjList(
    vertices = listOf(
        vertice1, vertice2, vertice3, vertice4, vertice5, vertice6
    )
)

graph.addEdge(vertice1, vertice2)
graph.addEdge(vertice1, vertice3)
graph.addEdge(vertice2, vertice4)

graph.addEdge(vertice5, vertice6)
graph.addEdge(vertice4, vertice1)

/*
    1 -> 3
      -> 2 -> 4
    5 -> 6
 */

val search = StronglyConnectedComponentSearcher<Int>()
println(search.stronglyConnectedComponentsCountKosaraju(graph))

val graph2 = DirectedGraphWithAdjList(
    vertices = listOf(
        vertice0, vertice1, vertice2, vertice3, vertice4
    )
)

graph2.addEdge(vertice0, vertice2)
graph2.addEdge(vertice3, vertice3)
graph2.addEdge(vertice1, vertice0)
graph2.addEdge(vertice2, vertice1)
graph2.addEdge(vertice3, vertice4)

/*
    1 -> 0 -> 3 -> 4
    ..
    0 -> 2 -> 1

    3 SCC:
    [4, 2, 0-1-2]
 */

val search2 = StronglyConnectedComponentSearcher<Int>()
println(search2.stronglyConnectedComponentsCountKosaraju(graph2))

data class Vertice<T>(
    val value: T
)

class DirectedGraphWithAdjList<T>(val vertices: List<Vertice<T>> = emptyList()) {
    private val adjList: MutableMap<Vertice<T>, MutableList<Vertice<T>>> = mutableMapOf()

    fun addEdge(from: Vertice<T>, to: Vertice<T>) {
        val neighbours = adjList[from] ?: mutableListOf()
        neighbours.add(to)
        adjList[from] = neighbours
    }

    fun neighbours(of: Vertice<T>): List<Vertice<T>> {
        return adjList[of] ?: emptyList()
    }
}

class StronglyConnectedComponentSearcher<T> {
    // https://www.geeksforgeeks.org/strongly-connected-components/
    // O(E+V) time
    fun stronglyConnectedComponentsCountKosaraju(graph: DirectedGraphWithAdjList<T>): Int {
        /*
            1. Perform DFS on each vertice
                put vertices on stack post-order (starting vertice last)
            2. Created reversed graph (reverse edges)
            3. Perform DFS for each vertice on stack, count nr. of traversals
         */

        val stack = Stack<Vertice<T>>()
        val visited: MutableSet<Vertice<T>> = mutableSetOf()
        graph.vertices.forEach {
            if (!visited.contains(it)) dfs(graph, it, visited, stack)
        }

        val reversedGraph = reverse(graph)

        var stronglyConnectedComponentsCount = 0
        visited.clear()

        while (!stack.isEmpty()) {
            val vertice = stack.pop()
            if (!visited.contains(vertice)) {
                dfs(reversedGraph, vertice, visited)
                stronglyConnectedComponentsCount++
            }
        }

        return stronglyConnectedComponentsCount
    }

    private fun dfs(
        graph: DirectedGraphWithAdjList<T>,
        vertice: Vertice<T>,
        visited: MutableSet<Vertice<T>>,
        stack: Stack<Vertice<T>>? = null
    ) {
        visited.add(vertice)

        graph.neighbours(vertice).forEach {
            if (!visited.contains(it)) {
                dfs(graph, it, visited, stack)
            }
        }

        stack?.push(vertice)
    }

    private fun reverse(graph: DirectedGraphWithAdjList<T>): DirectedGraphWithAdjList<T> {
        val newGraph = DirectedGraphWithAdjList(graph.vertices)

        graph.vertices.forEach { dest ->
            graph.neighbours(dest).forEach { src ->
                newGraph.addEdge(src, dest)
            }
        }

        return newGraph
    }
}
