package kt.kotlinalgs.app.graph

println("Test")

Solution().test()

data class Vertice<T>(
    val value: T
)

class UndirectedGraphWithAdjList<T>(val vertices: List<Vertice<T>> = emptyList()) {
    private val adjList: MutableMap<Vertice<T>, MutableList<Vertice<T>>> = mutableMapOf()

    fun addEdge(from: Vertice<T>, to: Vertice<T>) {
        val neighbours = adjList[from] ?: mutableListOf()
        neighbours.add(to)
        adjList[from] = neighbours

        val neighbours2 = adjList[to] ?: mutableListOf()
        neighbours2.add(from)
        adjList[to] = neighbours2
    }

    fun neighbours(of: Vertice<T>): List<Vertice<T>> {
        return adjList[of] ?: emptyList()
    }
}

class ConnectedComponentChecker<T> {
    fun numOfConnectedComponents(graph: UndirectedGraphWithAdjList<T>): Int {
        val visited: MutableSet<Vertice<T>> = mutableSetOf()
        var componentsCount = 0

        graph.vertices.forEach {
            if (!visited.contains(it)) {
                componentsCount++
                dfs(graph, it, visited)
            }
        }

        return componentsCount
    }

    private fun dfs(
        graph: UndirectedGraphWithAdjList<T>,
        vertice: Vertice<T>,
        visited: MutableSet<Vertice<T>>
    ) {
        visited.add(vertice)

        graph.neighbours(vertice).forEach {
            if (!visited.contains(it)) {
                dfs(graph, it, visited)
            }
        }
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

        val graph = UndirectedGraphWithAdjList<Int>(
            vertices = listOf(
                vertice1, vertice2, vertice3, vertice4, vertice5, vertice6
            )
        )

        graph.addEdge(vertice1, vertice2)
        graph.addEdge(vertice3, vertice4)
        graph.addEdge(vertice5, vertice6)
        graph.addEdge(vertice6, vertice1)

        // 1 -> 2 .. 3 -> 4 .. 5 -> 6 -> 1

        val connectedComponentChecker = ConnectedComponentChecker<Int>()
        println(connectedComponentChecker.numOfConnectedComponents(graph))
    }
}
