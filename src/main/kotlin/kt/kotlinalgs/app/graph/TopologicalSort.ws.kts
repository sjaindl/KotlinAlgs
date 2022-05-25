package kt.kotlinalgs.app.graph

import java.util.*

println("Test")

data class Vertice<T>(
    val value: T,
    var incomingEdgeCount: Int = 0 // exclude from hash!!
) {
    override fun equals(other: Any?): Boolean {
        return value == (other as? Vertice<T>)?.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

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
//graph.addEdge(vertice6, vertice1)
/*
    1 -> 3
      -> 2 -> 4
           -> 5 -> 6

1. construct directed graph
2. count incoming edges  (property at vertice class)
3. put nodes with no incoming edges into queue
4. while (!queue is empty):
    pop
    put on processed queue
    reduce inc edge count for all neighbours, if 0 -> put on queue
5. check processed queue count == nr. of vertices. If not -> cycle
 */

val sort = GraphSort<Int>()
sort.topologicalSort(graph)

class GraphSort<T> {
    // 1. construct directed graph
    fun topologicalSort(graph: DirectedGraphWithAdjList<T>): Queue<Vertice<T>> {
        // 2. count incoming edges  (property at vertice class)
        graph.vertices.forEach {
            println("check $it")
            graph.neighbours(it).forEach { neighbour ->
                neighbour.incomingEdgeCount++
                println("nb $neighbour")
            }
        }

        // 3. put nodes with no incoming edges into queue
        var processingQueue: Queue<Vertice<T>> = LinkedList<Vertice<T>>()
        graph.vertices.forEach {
            println("v $it count ${it.incomingEdgeCount}")
            if (it.incomingEdgeCount == 0) processingQueue.add(it)
        }

        /*
        4. while (!queue is empty):
            pop
            put on processed queue
            reduce inc edge count for all neighbours, if 0 -> put on queue
         */

        var processedQueue: Queue<Vertice<T>> = LinkedList<Vertice<T>>()

        println("processingQueue -> ${processingQueue.size}")

        while (!processingQueue.isEmpty()) {
            val next = processingQueue.poll()
            processedQueue.add(next)

            println("Processed: ${next.value}")

            graph.vertices.forEach {
                graph.neighbours(it).forEach {
                    it.incomingEdgeCount--
                    if (it.incomingEdgeCount == 0) processingQueue.add(it)
                }
            }
        }

        // 5. check processed queue count == nr. of vertices. If not -> cycle
        if (processedQueue.size != graph.vertices.size) {
            println("Cycle!")
        }

        return processedQueue
    }
}
