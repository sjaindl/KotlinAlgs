package kt.kotlinalgs.app.graph

import java.util.*

println("Test")

val vertice1 = Vertice(1)
val vertice2 = Vertice(2)
val vertice3 = Vertice(3)
val vertice4 = Vertice(4)
val vertice5 = Vertice(5)
val vertice6 = Vertice(6)

val graph = UndirectedGraphWithAdjList<Int>(
    vertices = listOf(
        vertice1, vertice2, vertice3, vertice4, vertice5, vertice6
    )
)

graph.addEdge(vertice1, vertice2)
graph.addEdge(vertice1, vertice3)
graph.addEdge(vertice2, vertice4)
graph.addEdge(vertice2, vertice5)
graph.addEdge(vertice5, vertice6)
/*
    1 -> 3
      -> 2 -> 4
           -> 5 -> 6

    BFS 1: 1,2,3 .. 2 -> 5 found!
    BFS 2: 6,5,4
 */

val search = GraphSearch<Int>()
search.bidirectionalBfs(graph, vertice1, vertice6)

data class Vertice<T>(
    val value: T
)

class UndirectedGraphWithAdjList<T>(val vertices: List<Vertice<T>> = emptyList()) {
    val adjList: MutableMap<Vertice<T>, MutableList<Vertice<T>>> = mutableMapOf()

    fun addEdge(from: Vertice<T>, to: Vertice<T>) {
        val fromNeighbours = adjList[from] ?: mutableListOf()
        fromNeighbours.add(to)
        adjList[from] = fromNeighbours

        val toNeighbours = adjList[to] ?: mutableListOf()
        toNeighbours.add(from)
        adjList[to] = toNeighbours
    }

    fun neighbours(of: Vertice<T>): List<Vertice<T>> {
        return adjList[of] ?: emptyList()
    }
}

class GraphSearch<T> {
    data class BidirectionalSearch<T>(
        var queue: Queue<Vertice<T>> = LinkedList<Vertice<T>>(),
        val visited: MutableSet<Vertice<T>> = mutableSetOf()
    )

    fun bidirectionalBfs(graph: UndirectedGraphWithAdjList<T>, start: Vertice<T>, end: Vertice<T>) {
        val first = BidirectionalSearch<T>()
        val second = BidirectionalSearch<T>()

        if (start == end) {
            println("start == end")
            return
        }

        first.queue.add(start)
        second.queue.add(end)

        var found = false
        while (!found) {
            found = searchOneRound(graph, first, second)
            if (found) return
            found = searchOneRound(graph, second, first)
        }
    }

    private fun searchOneRound(
        graph: UndirectedGraphWithAdjList<T>,
        from: BidirectionalSearch<T>,
        to: BidirectionalSearch<T>
    ): Boolean {
        val newQueue = LinkedList<Vertice<T>>()
        while (!from.queue.isEmpty()) {
            val next = from.queue.poll()
            println("Bidirectional BFS - Visiting ${next.value}")
            from.visited.add(next)

            if (to.visited.contains(next)) {
                println("Bidirectional BFS - Met at ${next.value}")
                return true
            }

            graph.neighbours(next).forEach {
                if (!from.visited.contains(it)) {
                    newQueue.add(it)
                }
            }
        }

        from.queue = newQueue

        return false
    }
}
