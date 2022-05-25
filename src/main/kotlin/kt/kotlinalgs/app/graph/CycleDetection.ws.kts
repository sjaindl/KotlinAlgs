package kt.kotlinalgs.app.graph

println("Test")

val vertice1 = Vertice(1)
val vertice2 = Vertice(2)
val vertice3 = Vertice(3)
val vertice4 = Vertice(4)
val vertice5 = Vertice(5)
val vertice6 = Vertice(6)

val directedGraph = DirectedGraphWithAdjList<Int>(
    vertices = listOf(
        vertice1, vertice2, vertice3, vertice4, vertice5, vertice6
    )
)

val search = GraphSearch<Int>()

directedGraph.addEdge(vertice1, vertice2)
directedGraph.addEdge(vertice1, vertice3)
directedGraph.addEdge(vertice2, vertice4)
directedGraph.addEdge(vertice2, vertice5)
directedGraph.addEdge(vertice5, vertice6)

println("DFS Dir: ${search.hasCycleDirectedGraph(directedGraph)}")
println("UF Dir: ${search.hasCycleDirectedGraphUnionFind(directedGraph)}")

directedGraph.addEdge(vertice6, vertice1) // add cycle

println("DFS Dir: ${search.hasCycleDirectedGraph(directedGraph)}")
println("UF Dir: ${search.hasCycleDirectedGraphUnionFind(directedGraph)}")

/*
    1 -> 3
      -> 2 -> 4
           -> 5 -> 6 -> 1.. (cycle)

    DFS: 1,2,4,5,6,3
    BFS: 1,2,3,4,5,6
 */

val undirectedGraph = UndirectedGraphWithAdjList<Int>(
    vertices = listOf(
        vertice1, vertice2, vertice3, vertice4, vertice5, vertice6
    )
)

undirectedGraph.addEdge(vertice1, vertice2)
undirectedGraph.addEdge(vertice1, vertice3)
undirectedGraph.addEdge(vertice2, vertice4)
undirectedGraph.addEdge(vertice2, vertice5)
undirectedGraph.addEdge(vertice5, vertice6)

println("DFS Undir: ${search.hasCycleUndirectedGraph(undirectedGraph)}")

undirectedGraph.addEdge(vertice6, vertice1) // add cycle

println("DFS Undir: ${search.hasCycleUndirectedGraph(undirectedGraph)}")

/*
    1 -> 3
      -> 2 -> 4
           -> 5 -> 6 -> 1.. (cycle)

    DFS: 1,2,4,5,6,3
    BFS: 1,2,3,4,5,6
 */

data class Vertice<T>(
    val value: T
)

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

class UndirectedGraphWithAdjList<T>(val vertices: List<Vertice<T>> = emptyList()) {
    val adjList: MutableMap<Vertice<T>, MutableList<Vertice<T>>> = mutableMapOf()

    fun addEdge(from: Vertice<T>, to: Vertice<T>) {
        val fromNeighbours = adjList[from] ?: mutableListOf()
        fromNeighbours.add(to)
        adjList[from] = fromNeighbours

        val toNeighbours = adjList[to] ?: mutableListOf()
        toNeighbours.add(from)
        adjList[to] = toNeighbours
    }

    fun neighbours(of: Vertice<T>): List<Vertice<T>> {
        return adjList[of] ?: emptyList()
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

class GraphSearch<T> {
    // https://www.geeksforgeeks.org/detect-cycle-in-a-graph/
    // O(V+E)
    fun hasCycleDirectedGraph(graph: DirectedGraphWithAdjList<T>): Boolean {
        val visited: MutableSet<Vertice<T>> = mutableSetOf()
        val inStack: MutableSet<Vertice<T>> = mutableSetOf()

        graph.vertices.forEach {
            if (!visited.contains(it)) {
                if (hasCycleRec(graph, it, inStack, visited)) return true
            }
        }

        return false
    }

    private fun hasCycleRec(
        graph: DirectedGraphWithAdjList<T>,
        vertice: Vertice<T>,
        inStack: MutableSet<Vertice<T>>,
        visited: MutableSet<Vertice<T>>
    ): Boolean {
        if (inStack.contains(vertice)) return true
        if (visited.contains(vertice)) return false

        visited.add(vertice)
        inStack.add(vertice)

        graph.adjList[vertice]?.forEach {
            if (hasCycleRec(graph, it, inStack, visited)) return true
        }

        inStack.remove(vertice)

        return false
    }

    // https://www.geeksforgeeks.org/union-find/
    // O(E + V), if path compression + union by rank
    fun hasCycleDirectedGraphUnionFind(graph: DirectedGraphWithAdjList<T>): Boolean {
        val unionFind = UnionFind(graph.vertices.size)
        val verticeToIntMapping: MutableMap<Vertice<T>, Int> = mutableMapOf()
        graph.vertices.forEachIndexed { index, vertice ->
            verticeToIntMapping[vertice] = index
        }

        graph.vertices.forEach { parent ->
            graph.adjList[parent]?.forEach { neighbour ->
                val first = unionFind.find(verticeToIntMapping[parent]!!)
                val second = unionFind.find(verticeToIntMapping[neighbour]!!)

                if (first == second) return true
                unionFind.union(first, second)
            }
        }

        return false
    }

    // https://www.geeksforgeeks.org/detect-cycle-undirected-graph/
    // O(E+V)
    // ohne inStack, weil bei undirected graphs connected components immer gleich voll traversed werden
    fun hasCycleUndirectedGraph(graph: UndirectedGraphWithAdjList<T>): Boolean {
        val visited: MutableSet<Vertice<T>> = mutableSetOf()

        graph.vertices.forEach {
            if (!visited.contains(it) && hasCycleUndirectedGraphRec(
                    graph,
                    it,
                    null,
                    visited
                )
            ) return true
        }

        return false
    }

    private fun hasCycleUndirectedGraphRec(
        graph: UndirectedGraphWithAdjList<T>,
        vertice: Vertice<T>,
        parent: Vertice<T>?,
        visited: MutableSet<Vertice<T>>
    ): Boolean {
        if (visited.contains(vertice)) return true

        visited.add(vertice)

        graph.adjList[vertice]?.forEach {
            if (it != parent && hasCycleUndirectedGraphRec(
                    graph,
                    it,
                    vertice,
                    visited
                )
            ) return true
        }

        return false
    }
}

// Another alternative using 3 colors (grey = in stack, black = visited, white = not visited):
// https://www.geeksforgeeks.org/detect-cycle-direct-graph-using-colors/
// O(E+V) time, O(V) space
