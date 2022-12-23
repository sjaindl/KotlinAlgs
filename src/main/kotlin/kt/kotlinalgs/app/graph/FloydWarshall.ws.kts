package com.sjaindl.kotlinalgsandroid.graph

/*
    FloydWarshall
    shortest paths between all nodes
    Using array: O(V^3)
 */

//https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16/
class FloydWarshall<T> {
    fun shortestPath(graph: Array<IntArray>): Array<IntArray> {
        val size = graph.size
        val shortestPaths: Array<IntArray> = Array(size) { IntArray(size) }

        for (front in 0 until size) {
            for (back in 0 until size) {
                shortestPaths[front][back] = graph[front][back]
            }
        }

        for (intermediate in 0 until size) {
            for (front in 0 until size) {
                for (back in 0 until size) {
                    val cur = shortestPaths[front][intermediate] + shortestPaths[intermediate][back]
                    if (cur < shortestPaths[front][back]) {
                        shortestPaths[front][back] = cur
                    }
                }
            }
        }

        printSolution(shortestPaths)

        return shortestPaths
    }

    fun printSolution(dist: Array<IntArray>) {
        println(
            "The following matrix shows the shortest " +
                    "distances between every pair of vertices"
        )
        for (i in 0 until dist.size) {
            for (j in 0 until dist.size) {
                print(dist[i][j].toString() + "   ")
            }
            println()
        }
    }
}

val INF = 999999 // possible int overflow if set to Int.MAX_VALUE!

var graph = arrayOf(
    intArrayOf(0, 5, INF, 10),
    intArrayOf(INF, 0, 3, INF),
    intArrayOf(INF, INF, 0, 1),
    intArrayOf(INF, INF, INF, 0)
)

val result = FloydWarshall<String>().shortestPath(graph)
