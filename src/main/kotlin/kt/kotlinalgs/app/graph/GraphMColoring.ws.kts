package kt.kotlinalgs.app.graph

import java.util.*

println("Test")

Solution().test()

data class Vertice<T>(
    val value: T,
    var color: Int = -1
) {
    override fun equals(other: Any?): Boolean {
        val oth = other as? Vertice<*> ?: return false
        return oth.value == value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}

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

class BipartiteChecker<T> {
    fun isBipartiteBFS(graph: DirectedGraphWithAdjList<T>): Boolean {
        if (graph.vertices.size <= 2) return true

        graph.vertices.forEach {
            it.color = -1
        }

        graph.vertices.forEach {
            if (it.color != -1) return@forEach

            if (!isBipartiteBFSUtil(graph, it)) return false
        }

        return true
    }

    fun isBipartiteBFSUtil(graph: DirectedGraphWithAdjList<T>, vertice: Vertice<T>): Boolean {
        val queue = LinkedList<Vertice<T>>()
        vertice.color = 0
        queue.add(vertice)

        while (!queue.isEmpty()) {
            val cur = queue.remove()
            graph.neighbours(cur).forEach {
                if (it.color == cur.color) return false
                else if (it.color == -1) {
                    it.color = if (cur.color == 0) 1 else 0
                    queue.add(it)
                }
            }
        }

        return true
    }

    fun isBipartiteDFS(graph: DirectedGraphWithAdjList<T>): Boolean {
        if (graph.vertices.size <= 2) return true

        graph.vertices.forEach {
            it.color = -1
        }

        graph.vertices.forEach {
            if (it.color == -1 && !isBipartiteDFSRec(graph, it, -1)) return false
        }

        return true
    }

    private fun isBipartiteDFSRec(
        graph: DirectedGraphWithAdjList<T>,
        vertice: Vertice<T>,
        prevColor: Int
    ): Boolean {
        vertice.color = when (prevColor) {
            -1 -> 0
            0 -> 1
            else -> 0
        }

        graph.neighbours(vertice).forEach {
            if (it.color == vertice.color) return false
            else if (it.color == -1) {
                if (!isBipartiteDFSRec(graph, it, vertice.color)) return false
            }
        }

        return true
    }
}

class Solution {
    fun test() {
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
        graph.addEdge(vertice2, vertice3)
        graph.addEdge(vertice3, vertice4)
        graph.addEdge(vertice4, vertice5)
        graph.addEdge(vertice5, vertice6)
        graph.addEdge(vertice6, vertice1)

        // 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 1 .. (cycle)

        val bipartiteChecker = BipartiteChecker<Int>()
        println(bipartiteChecker.isBipartiteDFS(graph))
        println(bipartiteChecker.isBipartiteBFS(graph))

        // --------------------------------------------------

        val vertice1_2 = Vertice(1)
        val vertice2_2 = Vertice(2)
        val vertice3_2 = Vertice(3)
        val vertice4_2 = Vertice(4)
        val vertice5_2 = Vertice(5)

        val graph_2 = DirectedGraphWithAdjList<Int>(
            vertices = listOf(
                vertice1_2, vertice2_2, vertice3_2, vertice4_2, vertice5_2
            )
        )

        graph_2.addEdge(vertice1_2, vertice2_2)
        graph_2.addEdge(vertice2_2, vertice3_2)
        graph_2.addEdge(vertice3_2, vertice4_2)
        graph_2.addEdge(vertice4_2, vertice5_2)
        graph_2.addEdge(vertice5_2, vertice1_2)

        // 1 -> 2 -> 3 -> 4 -> 5 -> 1 .. (cycle)

        val bipartiteChecker2 = BipartiteChecker<Int>()
        println(bipartiteChecker2.isBipartiteDFS(graph_2))
        println(bipartiteChecker2.isBipartiteBFS(graph_2))
    }
}
