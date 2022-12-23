package com.sjaindl.kotlinalgsandroid.graph

import java.util.*

/*
    GraphMColoring - Color graph with max M colors possible?
    Backtracking algo: O(m^V) time + O(V) space for recursion. In practice much faster.
    Alternative: BFS in O(E+V)
 */

//https://www.geeksforgeeks.org/m-coloring-problem-backtracking-5/
class GraphMColoringBacktrack {
    fun color(graph: Array<IntArray>, maxColors: Int): IntArray? {
        if (graph.isEmpty()) return IntArray(0)

        val colors = IntArray(graph.size) { -1 }
        return color(graph, 0, colors, maxColors)
    }

    private fun color(
        graph: Array<IntArray>,
        index: Int,
        colors: IntArray,
        maxColors: Int
    ): IntArray? {
        if (index == graph.size) {
            return colors
        }

        for (color in 0 until maxColors) {
            if (isSafe(graph, index, colors, color)) {
                colors[index] = color

                color(graph, index + 1, colors, maxColors)?.let {
                    return it
                }

                colors[index] = -1
            }
        }

        return null
    }

    private fun isSafe(graph: Array<IntArray>, index: Int, colors: IntArray, color: Int): Boolean {
        for (neighbourIndex in graph.indices) {
            //check if there's an edge with already same color
            if (graph[index][neighbourIndex] != 0 && colors[neighbourIndex] == color) return false
        }
        return true
    }
}

class GraphColoringBFS {
    fun color(graph: Array<IntArray>, maxColors: Int): IntArray? {
        if (graph.isEmpty()) return null

        val visited: MutableSet<Int> = mutableSetOf()
        val queue = LinkedList<Int>()
        val colors = IntArray(graph.size) { 0 }
        queue.add(0)

        while (!queue.isEmpty()) {
            val next = queue.removeLast()
            for (neighbour in graph.indices) {
                if (next != neighbour && graph[next][neighbour] != 0) {
                    if (colors[next] == colors[neighbour]) {
                        colors[neighbour]++
                        if (colors[neighbour] >= maxColors) return null
                    }
                    if (!visited.contains(neighbour)) {
                        visited.add(neighbour)
                        queue.add(neighbour)
                    }
                }
            }
        }

        return colors
    }
}

val graph: Array<IntArray> = arrayOf(
    intArrayOf(0, 1, 1, 1),
    intArrayOf(1, 0, 1, 0),
    intArrayOf(1, 1, 0, 1),
    intArrayOf(1, 0, 1, 0)
)

val colors = GraphMColoringBacktrack().color(graph, 3)

colors?.forEach {
    println("$it")
}

val colorsBFS = GraphColoringBFS().color(graph, 3)

colorsBFS?.forEach {
    println("$it")
}
