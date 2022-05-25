package kt.kotlinalgs.app.graph

println("Test")

Solution().test()

class Solution {
    fun test() {
        //https://www.geeksforgeeks.org/kruskals-minimum-spanning-tree-algorithm-greedy-algo-2/
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

        val kruskal = Kruskal()
        val edges = kruskal.minimumSpanningTree(graph)

        println("Edges:")
        edges.forEach {
            println("${it.from.value} - ${it.to.value}: ${it.weight}")
        }
    }
}

// https://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/
class UnionFind(size: Int) {
    private val parent = IntArray(size) {
        it
    }

    private val rank = IntArray(size) { 1 }

    // [0,1,2,3]
    fun find(component: Int): Int {
        //println("find: $component, ${parent[component]}")
        if (component == parent[component]) return component

        // path compression
        parent[component] = find(parent[component])

        return parent[component]
    }

    fun union(first: Int, second: Int) {
        val firstComponent = find(first)
        val secondComponent = find(second)

        if (firstComponent != secondComponent) {
            // union by rank
            when {
                rank[firstComponent] < rank[secondComponent] -> {
                    parent[firstComponent] = secondComponent
                }
                rank[secondComponent] < rank[firstComponent] -> {
                    parent[secondComponent] = firstComponent
                }
                else -> {
                    parent[secondComponent] = firstComponent
                    rank[firstComponent]++
                }
            }
        }
    }
}

data class Vertice(val value: Int)

data class Edge(
    val from: Vertice,
    val to: Vertice,
    val weight: Int
) : Comparable<Edge> {
    override fun compareTo(other: Edge): Int {
        return weight - other.weight
    }
}

data class WeightedUndirectedGraph(val vertices: List<Vertice>) {
    var edges: MutableList<Edge> = mutableListOf()

    fun addEdge(first: Vertice, second: Vertice, weight: Int) {
        val edge = Edge(first, second, weight)
        edges.add(edge)
    }
}

class Kruskal() {
    fun minimumSpanningTree(graph: WeightedUndirectedGraph): MutableList<Edge> {
        val unionFind = UnionFind(graph.vertices.size)
        val output: MutableList<Edge> = mutableListOf()
        val edges = graph.edges.toMutableList()
        edges.sort() // O(E log E)

        var edgeIndex = 0

        while (output.size < graph.vertices.size - 1) { //O(V)
            val nextEdge = edges[edgeIndex] // O(1)
            edgeIndex++

            // Don't allow cycles
            if (unionFind.find(nextEdge.from.value) != unionFind.find(nextEdge.to.value)) {
                unionFind.union(nextEdge.from.value, nextEdge.to.value)
                output.add(nextEdge)
            } // O(1) with path compression + weighted UF
        } // total loop = O(V * log E)

        // total = O(E log E + V)
        return output
    }
}
