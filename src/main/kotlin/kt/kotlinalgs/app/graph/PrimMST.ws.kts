package kt.kotlinalgs.app.graph

import java.util.*

println("Test")

Solution().test()

class Solution {
    fun test() {
        //https://www.geeksforgeeks.org/prims-mst-for-adjacency-list-representation-greedy-algo-6/
        val vertice0 = Vertice(0)
        val vertice1 = Vertice(1)
        val vertice2 = Vertice(2)
        val vertice3 = Vertice(3)
        val vertice4 = Vertice(4)
        val vertice5 = Vertice(5)
        val vertice6 = Vertice(6)
        val vertice7 = Vertice(7)
        val vertice8 = Vertice(8)

        val graph = WeightedUndirectedGraph(
            listOf(
                vertice0, vertice1, vertice2, vertice3, vertice4,
                vertice5, vertice6, vertice7, vertice8
            )
        )

        graph.addEdge(vertice0, vertice1, 4)
        graph.addEdge(vertice0, vertice7, 8)
        graph.addEdge(vertice1, vertice2, 8)
        graph.addEdge(vertice1, vertice7, 11)
        graph.addEdge(vertice2, vertice3, 7)
        graph.addEdge(vertice2, vertice5, 4)
        graph.addEdge(vertice2, vertice8, 2)
        graph.addEdge(vertice3, vertice4, 9)
        graph.addEdge(vertice3, vertice5, 14)
        graph.addEdge(vertice4, vertice5, 10)
        graph.addEdge(vertice5, vertice6, 2)
        graph.addEdge(vertice6, vertice7, 1)
        graph.addEdge(vertice6, vertice8, 6)
        graph.addEdge(vertice7, vertice8, 7)

        val prim = Prim()
        val vertices = prim.minimumSpanningTree(graph)
        prim.printMst(vertices)
    }
}

data class Vertice(val value: Int, var distance: Int = Int.MAX_VALUE, var prev: Vertice? = null) :
    Comparable<Vertice> {
    override fun compareTo(other: Vertice): Int {
        return distance - other.distance
    }

    override fun equals(other: Any?): Boolean {
        val oth = other as? Vertice ?: return false
        return value == oth.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

data class Edge(
    val from: Vertice,
    val to: Vertice,
    val weight: Int
)

data class WeightedUndirectedGraph(val vertices: List<Vertice>) {
    var edges: MutableMap<Vertice, MutableList<Edge>> = mutableMapOf()

    fun addEdge(first: Vertice, second: Vertice, weight: Int) {
        val firstList = edges.getOrDefault(first, mutableListOf())
        firstList.add(Edge(first, second, weight))
        edges[first] = firstList

        val secondList = edges.getOrDefault(second, mutableListOf())
        secondList.add(Edge(second, first, weight))
        edges[second] = secondList
    }
}

// O(E log V)
class Prim {
    fun minimumSpanningTree(graph: WeightedUndirectedGraph): MutableList<Vertice> {
        val output: MutableList<Vertice> = mutableListOf()
        val pq: TreeSet<Vertice> = TreeSet() // PriorityQueue()
        val inMst: MutableSet<Vertice> = mutableSetOf()

        graph.vertices.forEach {
            it.distance = Int.MAX_VALUE
        }

        graph.vertices[0].distance = 0

        pq.add(graph.vertices[0])

        while (!pq.isEmpty()) { // E
            val cur = pq.pollFirst() // // O(log V)

            output.add(cur)
            inMst.add(cur)

            graph.edges[cur]?.forEach {
                if (!inMst.contains(it.to) && it.weight < it.to.distance) { // O(log V)
                    if (pq.contains(it.to)) {
                        pq.remove(it.to) // O(log V)
                        // important!! oder IndexedPQ / because PQ remove = O(N)..
                        // treeSet add, remove, contains = O(log N)
                    }

                    it.to.distance = it.weight
                    it.to.prev = cur

                    pq.add(it.to) // O(log V)
                }
            }
        }

        // total = O(E log V)

        return output
    }

    fun printMst(mst: MutableList<Vertice>) {
        println("MST:")
        mst.forEach {
            println("${it.prev?.value} -> ${it.value}")
        }
    }
}
