package kt.kotlinalgs.app.graph

println("Test")

data class Vertice(val value: String)

data class Edge(
    val from: Vertice,
    val to: Vertice,
    val weight: Int
)

data class WeightedDirectedGraph(val vertices: List<Vertice>) {
    var edges: MutableList<Edge> = mutableListOf()

    fun addEdge(from: Vertice, to: Vertice, weight: Int) {
        val edge = Edge(from, to, weight)
        edges.add(edge)
    }
}

/*
for each vertice in 0 until V - 1 (min path for each vertice is max V-1 edges long):

runtime: O(V*E)
 */
class BellmanFord() {
    fun allShortestPaths(graph: WeightedDirectedGraph): MutableMap<Vertice, Int> {
        val dist: MutableMap<Vertice, Int> = mutableMapOf()
        graph.vertices.forEach {
            dist.put(it, Int.MAX_VALUE)
        }

        dist.put(graph.vertices[0], 0)

        //IntArray(graph.vertices.size) { Int.MAX_VALUE }
        //dist[0] = 0

        for (v in 0 until graph.vertices.size - 1) {
            graph.edges.forEach {
                val distFrom = dist[it.from] ?: return@forEach
                val distTo = dist[it.to] ?: return@forEach
                if (distFrom == Int.MAX_VALUE) return@forEach

                val curValue = distFrom + it.weight
                if (curValue < distTo) {
                    dist[it.to] = curValue
                }
            }
        }

        // check for negative cycle
        graph.edges.forEach {
            val distFrom = dist[it.from] ?: return@forEach
            val distTo = dist[it.to] ?: return@forEach
            if (distFrom == Int.MAX_VALUE) return@forEach

            val curValue = distFrom + it.weight
            if (curValue < distTo) {
                println("Negative cycle found!")
                return mutableMapOf()
            }
        }

        return dist
    }
}

//https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23/
val verticeA = Vertice("A")
val verticeB = Vertice("B")
val verticeC = Vertice("C")
val verticeD = Vertice("D")
val verticeE = Vertice("E")

val graph = WeightedDirectedGraph(
    listOf(
        verticeA, verticeB, verticeC, verticeD, verticeE
    )
)

graph.addEdge(verticeA, verticeB, -1)
graph.addEdge(verticeA, verticeC, 4)
graph.addEdge(verticeB, verticeC, 3)
graph.addEdge(verticeB, verticeD, 2)
graph.addEdge(verticeB, verticeE, 2)
graph.addEdge(verticeD, verticeB, 2)
graph.addEdge(verticeD, verticeC, 5)
graph.addEdge(verticeE, verticeD, -3)

val bellmanFord = BellmanFord()
bellmanFord.allShortestPaths(graph)

