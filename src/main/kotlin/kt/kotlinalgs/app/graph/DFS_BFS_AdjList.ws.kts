package kt.kotlinalgs.app.graph

import java.util.*

println("Test")

val vertice1 = Vertice(1)
val vertice2 = Vertice(2)
val vertice3 = Vertice(3)
val vertice4 = Vertice(4)
val vertice5 = Vertice(5)
val vertice6 = Vertice(6)

val graph = DirectedGraphWithAdjList<Int>(
    vertices = listOf(
        vertice1, vertice2, vertice3, vertice4, vertice5, vertice6
    )
)

graph.addEdge(vertice1, vertice2)
graph.addEdge(vertice1, vertice3)
graph.addEdge(vertice2, vertice4)
graph.addEdge(vertice2, vertice5)
graph.addEdge(vertice5, vertice6)
graph.addEdge(vertice6, vertice1)
/*
    1 -> 3
      -> 2 -> 4
           -> 5 -> 6 -> 1.. (cycle)

    DFS: 1,2,4,5,6,3
    BFS: 1,2,3,4,5,6
 */

val search = GraphSearch<Int>()
search.dfs(graph, vertice1)
search.dfsIterative(graph, vertice1)
search.bfs(graph, vertice1)

data class Vertice<T>(
    val value: T
)

class DirectedGraphWithAdjList<T>(val vertices: List<Vertice<T>> = emptyList()) {
    val adjList: MutableMap<Vertice<T>, MutableList<Vertice<T>>> = mutableMapOf()

    fun addEdge(from: Vertice<T>, to: Vertice<T>) {
        val neighbours = adjList[from] ?: mutableListOf()
        neighbours.add(to)
        adjList[from] = neighbours
    }

    fun neighbours(of: Vertice<T>): List<Vertice<T>> {
        return adjList[of] ?: emptyList()
    }
}

class GraphSearch<T> {
    fun bfs(graph: DirectedGraphWithAdjList<T>, start: Vertice<T>) {
        val visited = mutableSetOf<Vertice<T>>()
        val queue = LinkedList<Vertice<T>>()

        queue.add(start)

        while (!queue.isEmpty()) {
            val next = queue.poll()
            visited.add(next)

            println("BFS - visiting ${next.value}")
            graph.neighbours(next).forEach {
                if (!visited.contains(it)) {
                    queue.add(it)
                }
            }
        }
    }

    fun dfs(graph: DirectedGraphWithAdjList<T>, start: Vertice<T>) {
        val visited = mutableSetOf<Vertice<T>>()

        dfsRec(graph, start, visited)
    }

    private fun dfsRec(
        graph: DirectedGraphWithAdjList<T>,
        current: Vertice<T>,
        visited: MutableSet<Vertice<T>>
    ) {
        visited.add(current)
        println("DFS - visiting ${current.value}")

        graph.neighbours(current).forEach {
            if (!visited.contains(it)) {
                dfsRec(graph, it, visited)
            }
        }
    }

    // https://www.geeksforgeeks.org/iterative-depth-first-traversal/
    // O(E+V) time, O(V) space for stack
    fun dfsIterative(graph: DirectedGraphWithAdjList<T>, start: Vertice<T>) {
        val stack = Stack<Vertice<T>>()
        val visited: MutableSet<Vertice<T>> = mutableSetOf()

        stack.push(start)

        while (!stack.isEmpty()) {
            val current = stack.pop()
            visited.add(current)
            println("DFS iterative - visiting ${current.value}")

            graph.neighbours(current).forEach {
                if (!visited.contains(it)) {
                    stack.push(it)
                }
            }
        }
    }
}
