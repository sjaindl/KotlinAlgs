package kt.kotlinalgs.app.graph

import java.util.*

println("Test")

val verticeA = Vertice("A")
val verticeB = Vertice("B")
val verticeC = Vertice("C")
val verticeD = Vertice("D")
val verticeE = Vertice("E")
val verticeF = Vertice("F")

val graph = WeightedDirectedGraph(
    listOf(
        verticeA, verticeB, verticeC, verticeD, verticeE, verticeF
    )
)

// https://www.baeldung.com/java-dijkstra:
graph.addEdge(verticeA, verticeB, 10)
graph.addEdge(verticeA, verticeC, 15)
graph.addEdge(verticeB, verticeD, 12)
graph.addEdge(verticeB, verticeF, 15)
graph.addEdge(verticeC, verticeE, 10)
graph.addEdge(verticeD, verticeE, 2)
graph.addEdge(verticeD, verticeF, 1)
graph.addEdge(verticeF, verticeE, 5)

val djikstra = Djikstra()
djikstra.shortestPaths(verticeA, graph)
djikstra.printMinPath(verticeA, verticeB, graph)
djikstra.printMinPath(verticeA, verticeC, graph)
djikstra.printMinPath(verticeA, verticeD, graph)
djikstra.printMinPath(verticeA, verticeE, graph)
djikstra.printMinPath(verticeA, verticeF, graph)

data class Vertice(
    val value: String,
    var distance: Int = Integer.MAX_VALUE,
    var prev: Vertice? = null
) {
    override fun equals(other: Any?): Boolean {
        val oth = other as? Vertice ?: return false
        return value == oth.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

/*: Comparable<Vertice> {
    override fun compareTo(other: Vertice): Int {
        return distance - other.distance
    }
} */

data class Edge(
    val from: Vertice,
    val to: Vertice,
    val weight: Int
)

data class WeightedDirectedGraph(val vertices: List<Vertice>) {
    var edges: MutableMap<Vertice, MutableList<Edge>> = mutableMapOf()

    fun addEdge(from: Vertice, to: Vertice, weight: Int) {
        val edge = Edge(from, to, weight)
        val list = edges.getOrDefault(from, mutableListOf())
        list.add(edge)
        edges[from] = list
    }
}

class Djikstra() {
    /*
        Shortest path with weighted positive edges between start node and all other nodes.

        1. construct weighted directed graph with edges
        2. set start dist to 0, other to max int/Infinity
        3. init min pq (sort by vertice distance)
        4. add start vertice to pq
        5. while !pq.isEmpty: (E+V)
            poll min
            check neighbours: if from.distance + edge weight < to.distance:
                update to.distance & put in pq (log V)

    runtime:
        O((E+V) log V)
        https://inst.eecs.berkeley.edu/~cs61bl/r//cur/graphs/dijkstra-algorithm-runtime.html?topic=lab24.topic&step=4&course=
    Using PQ (array: V * V = V^2, heap: V log V + E log V = remove min + remove/add neighbor nodes prio)
 */
    fun shortestPaths(from: Vertice, graph: WeightedDirectedGraph) {
        graph.vertices.forEach {
            it.distance = Int.MAX_VALUE
        }
        from.distance = 0

        val cmp: Comparator<Vertice> = compareBy {
            it.distance
        }

        //val pq = PriorityQueue<Vertice>(11, cmp)
        val pq = TreeSet<Vertice>(cmp)
        // Oder treeset für remove/update, IndexedPQ?
        pq.add(from)

        while (!pq.isEmpty()) {
            val min = pq.pollFirst() //pq.poll() // V log V (remove)
            if (min.distance == Int.MAX_VALUE) continue

            graph.edges[min]?.forEach { // E log V (insert)
                val curDist = min.distance + it.weight
                if (curDist < it.to.distance) {
                    it.to.distance = curDist
                    it.to.prev = min

                    if (pq.contains(it.to)) pq.remove(it.to)
                    pq.add(it.to)
                }
            }
        }
    }

    fun printMinPath(from: Vertice, to: Vertice, graph: WeightedDirectedGraph) {
        val distance = to.distance

        val stack = Stack<Vertice>()
        var cur: Vertice? = to
        while (cur != null) {
            stack.push(cur)
            cur = cur.prev
        }

        while (!stack.isEmpty()) {
            println("${stack.pop().value} -> ")
        }

        println("Distance: $distance")
    }
}
