package com.sjaindl.kotlinalgsandroid.graph

/*
    https://www.geeksforgeeks.org/union-find/
 */

class CycleDetection {
    fun hasCycle(graph: Graph): Boolean {
        //val unionFind = Array(graph.vertices.size) { -1 }
        val unionFind: MutableMap<Int, Int> = mutableMapOf()
        val counts: MutableMap<Int, Int> = mutableMapOf()
        graph.vertices.forEach {
            unionFind[it.value] = it.value
            counts[it.value] = 1
        }

        // O((E+V) * log V):
        graph.vertices.forEach {
            graph.neighbours(it).forEach { neighbour ->
                val srcGroup = find(unionFind, it) //O(log V)
                val destGroup = find(unionFind, neighbour) //O(log V)
                if (srcGroup == destGroup) return true
                union(unionFind, counts, it, neighbour) //O(1)
            }
        }

        return false
    }

    private fun find(unionFind: MutableMap<Int, Int>, vertice: Vertice): Int {
        var group = vertice.value
        while (unionFind[group] != group) {
            group = unionFind[group]!!
        }

        return group
    }

    private fun union(
        unionFind: MutableMap<Int, Int>,
        counts: MutableMap<Int, Int>,
        src: Vertice,
        dst: Vertice
    ) {
        // union by rank (size, but could also be depth) -> O(log N)!
        if (counts[src.value]!! < counts[dst.value]!!) {
            unionFind[src.value] = unionFind[dst.value]!!
            counts[dst.value] = counts[dst.value]!!.plus(counts[src.value]!!)
        } else {
            unionFind[dst.value] = unionFind[src.value]!!
            counts[src.value] = counts[dst.value]!!.plus(counts[src.value]!!)
        }
    }
}

class Graph(val vertices: List<Vertice>) {
    private var adjList: MutableMap<Int, MutableList<Vertice>> = mutableMapOf()

    fun addEdge(from: Vertice, to: Vertice) {
        val list = adjList.getOrDefault(from.value, mutableListOf())
        list.add(to)
        adjList[from.value] = list
    }

    fun neighbours(vertice: Vertice): List<Vertice> {
        return adjList.getOrDefault(vertice.value, listOf())
    }
}

data class Vertice(
    val value: Int
)

val vertice0 = Vertice(0)
val vertice1 = Vertice(1)
val vertice2 = Vertice(2)
val vertice3 = Vertice(3)

val vertices = listOf(
    vertice0, vertice1, vertice2, vertice3
)

val graph = Graph(vertices)

graph.addEdge(vertice0, vertice1)
graph.addEdge(vertice0, vertice2)
graph.addEdge(vertice1, vertice2)
graph.addEdge(vertice2, vertice0)
graph.addEdge(vertice3, vertice3)

println(CycleDetection().hasCycle(graph))
