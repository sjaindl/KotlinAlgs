package com.sjaindl.kotlinalgsandroid.graph

/*
    MinSpanningTreePrim - MST weighted undirected graph
    O(V^2) runtime (adj. matrix)
 */

//https://www.geeksforgeeks.org/prims-minimum-spanning-tree-mst-greedy-algo-5/
class MinSpanningTreePrim {
    fun minSpanningTree(graph: Array<IntArray>): IntArray {
        val mstSet: MutableSet<Int> = mutableSetOf() //vertices already included in mst
        // min edges adjacent to current mst
        val min = IntArray(graph.size) { Int.MAX_VALUE }
        min[0] = 0 //start vertice to pick first

        //parent edge (from vertice) of (to) vertice
        val parents = IntArray(graph.size) { -1 }

        while (mstSet.size < graph.size) {
            val nextMin = findMin(min, mstSet)
            mstSet.add(nextMin)

            for (vertice in graph.indices) {
                if (!mstSet.contains(vertice) && graph[nextMin][vertice] != 0 && graph[nextMin][vertice] < min[vertice]) {
                    min[vertice] = graph[nextMin][vertice]
                    parents[vertice] = nextMin
                }
            }
        }

        return parents
    }

    private fun findMin(min: IntArray, mstSet: MutableSet<Int>): Int {
        var minVal = Int.MAX_VALUE
        var minIndex = -1

        min.forEachIndexed { index, value ->
            if (value < minVal && !mstSet.contains(index)) {
                minVal = value
                minIndex = index
            }
        }

        return minIndex
    }

}

val graph = arrayOf(
    intArrayOf(0, 4, 0, 0, 0, 0, 0, 8, 0),
    intArrayOf(4, 0, 8, 0, 0, 0, 0, 11, 0),
    intArrayOf(0, 8, 0, 7, 0, 0, 0, 0, 2),
    intArrayOf(0, 0, 7, 0, 9, 0, 0, 0, 0),
    intArrayOf(0, 0, 0, 9, 0, 10, 0, 0, 0),
    intArrayOf(0, 0, 4, 14, 10, 0, 2, 0, 0),
    intArrayOf(0, 0, 0, 0, 0, 2, 0, 1, 6),
    intArrayOf(8, 11, 0, 0, 0, 0, 1, 0, 7),
    intArrayOf(0, 0, 2, 0, 0, 0, 6, 7, 0)
)

val mst = MinSpanningTreePrim().minSpanningTree(graph)

mst.forEachIndexed { from, to ->
    println("$from-$to")
}
