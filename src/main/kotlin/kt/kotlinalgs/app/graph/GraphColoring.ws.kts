package kt.kotlinalgs.app.graph

import java.util.*

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

        val graph = UndirectedGraph(
            listOf(
                vertice0, vertice1, vertice2, vertice3, vertice4
            )
        )

        graph.addEdge(vertice0, vertice1)
        graph.addEdge(vertice0, vertice2)
        graph.addEdge(vertice1, vertice2)
        graph.addEdge(vertice1, vertice3)
        graph.addEdge(vertice2, vertice3)
        graph.addEdge(vertice3, vertice4)

        val coloring = GraphColoring()
        println(coloring.colorGraph(graph))

        val vertice0_2 = Vertice(0)
        val vertice1_2 = Vertice(1)
        val vertice2_2 = Vertice(2)
        val vertice3_2 = Vertice(3)
        val vertice4_2 = Vertice(4)

        val graph2 = UndirectedGraph(
            listOf(
                vertice0_2, vertice1_2, vertice2_2, vertice3_2, vertice4_2
            )
        )

        graph2.addEdge(vertice0_2, vertice1_2)
        graph2.addEdge(vertice0_2, vertice2_2)
        graph2.addEdge(vertice1_2, vertice2_2)
        graph2.addEdge(vertice1_2, vertice4_2)
        graph2.addEdge(vertice2_2, vertice4_2)
        graph2.addEdge(vertice3_2, vertice4_2)

        println(coloring.colorGraph(graph2))
    }
}

data class Vertice(val value: Int, var color: Int = -1)

data class UndirectedGraph(val vertices: List<Vertice>) {
    var adjList: MutableMap<Vertice, MutableList<Vertice>> = mutableMapOf()

    fun addEdge(first: Vertice, second: Vertice) {
        val firstList = adjList.getOrDefault(first, mutableListOf())
        firstList.add(second)
        adjList[first] = firstList

        val secondList = adjList.getOrDefault(second, mutableListOf())
        secondList.add(first)
        adjList[second] = secondList
    }
}

// https://www.geeksforgeeks.org/graph-coloring-set-2-greedy-algorithm/
class GraphColoring {
    fun colorGraph(graph: UndirectedGraph): Int {
        graph.vertices.forEach {
            it.color = -1
        }
        /*
            for each vertice:
                find min color used by checking all neighbours
                (putting adj colors > -1 in TreeSet, and find first gap color)
                color vertice with found color
                update numColors with Math.max(numColors, curColor)

            return numColors + 1 (includes color 0)
         */
        if (graph.vertices.isEmpty()) return 0

        var numColors = 0 //1

        // [0, -1, 1, -1, -1]
        graph.vertices.forEach { //O(V)
            val minColor = findMinColor(graph, it) // O(V+E)
            it.color = minColor
            println("Vertice ${it.value} -> ${it.color}")
            numColors = Math.max(numColors, minColor + 1)
        }

        // total: O(V^2 + E)
        return numColors
    }

    private fun findMinColor(graph: UndirectedGraph, vertice: Vertice): Int {
        var usedColors = TreeSet<Int>()

        graph.adjList[vertice]?.forEach { //O(E)
            if (it.color != -1) usedColors.add(it.color)
        }

        println(vertice)
        println(usedColors)

        if (usedColors.isEmpty()) return 0
        var lastColor = -1


        usedColors.forEach { //O(V)
            if (it != lastColor + 1) return lastColor + 1
            lastColor = it
        }

        return usedColors.size
    }
}
