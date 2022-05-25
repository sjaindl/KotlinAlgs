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

val graph = DirectedGraphWithAdjMatrix(
    vertices = listOf(
        vertice0, vertice1, vertice2, vertice3, vertice4, vertice5, vertice6
    )
)
graph.addEdge(vertice0, vertice1)
graph.addEdge(vertice1, vertice2)
graph.addEdge(vertice1, vertice3)
graph.addEdge(vertice2, vertice4)
graph.addEdge(vertice2, vertice5)
graph.addEdge(vertice5, vertice6)
graph.addEdge(vertice6, vertice0)
/*
0-> 1 -> 3
      -> 2 -> 4
           -> 5 -> 6 -> 0.. (cycle)

    DFS: 0,1,2,4,5,6,3
    BFS: 0,1,2,3,4,5,6
 */

val search = GraphSearch<Int>()
search.dfs(graph, vertice0)
search.bfs(graph, vertice0)

data class Vertice(
    val value: Int
)

class DirectedGraphWithAdjMatrix(val vertices: List<Vertice> = emptyList()) {
    val adjMatrix: Array<IntArray> =
        Array<IntArray>(vertices.size) { IntArray(vertices.size) { 0 } }

    fun addEdge(from: Vertice, to: Vertice) {
        adjMatrix[from.value][to.value] = 1
    }
}

class GraphSearch<T> {
    fun bfs(graph: DirectedGraphWithAdjMatrix, start: Vertice) {
        val queue = LinkedList<Vertice>()
        val visited = mutableSetOf<Vertice>()

        visited.add(start)
        queue.add(start)

        while (!queue.isEmpty()) {
            val next = queue.poll()

            println("BFS - visiting ${next.value}")

            graph.adjMatrix[next.value].forEachIndexed { index, isNeighbour ->
                val element = graph.vertices[index]
                if (isNeighbour == 1 && !visited.contains(element)) {
                    queue.add(element)
                }
            }
        }
    }

    fun dfs(graph: DirectedGraphWithAdjMatrix, start: Vertice) {
        val visited = mutableSetOf<Vertice>()

        dfsRec(graph, start, visited)
    }

    private fun dfsRec(
        graph: DirectedGraphWithAdjMatrix,
        current: Vertice,
        visited: MutableSet<Vertice>
    ) {
        visited.add(current)
        println("DFS - Visiting ${current.value}")

        graph.adjMatrix[current.value].forEachIndexed { index, isNeighbour ->
            val element = graph.vertices[index]
            if (isNeighbour == 1 && !visited.contains(element)) {
                dfsRec(graph, element, visited)
            }
        }
    }
}
