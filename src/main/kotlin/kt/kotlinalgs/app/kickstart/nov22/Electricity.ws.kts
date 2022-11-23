var line = 0
var args: Array<String> = arrayOf()

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

// https://codingcompetitions.withgoogle.com/kickstart/round/00000000008cb1b6/0000000000c47c8e#problem

// O(E + V) time, O(E + V) space (graph)
fun main(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val numJunctions = readLine()?.toIntOrNull() ?: return
        val weights: List<Int> = readLine()?.split(" ")?.mapNotNull { it.toIntOrNull() } ?: listOf()
        val graph = buildGraph(numJunctions, weights) ?: return

        // DFS
        var maxReactivated = 0

        for (junction in 1 until numJunctions + 1) {
            maxReactivated = Math.max(maxReactivated, dfs(junction, graph))
        }

        println("Case #$testCase: $maxReactivated")
    }
}

fun dfs(junction: Int, graph: DirectedWeightedGraph): Int {
    val vertice = graph.getVertice(junction)
    if (vertice.reachable != 0) return vertice.reachable

    vertice.reachable = 1
    graph.neighbours(junction).forEach {
        vertice.reachable += dfs(it.num, graph)
    }

    return vertice.reachable
}

fun buildGraph(numJunctions: Int, weights: List<Int>): DirectedWeightedGraph? {
    val vertices: MutableList<Vertice> = mutableListOf()

    for (index in 1 until numJunctions + 1) {
        val vertice = Vertice(index, weights[index - 1])
        vertices.add(vertice)
    }

    val graph = DirectedWeightedGraph(vertices)

    // n-1 edges
    for (index in 0 until numJunctions - 1) {
        val (from, to) = readLine()?.split(" ")?.map { it.toInt() } ?: return null
        graph.addEdge(vertices[from - 1], vertices[to - 1])
    }

    return graph
}

class Vertice(val num: Int, val weight: Int, var reachable: Int = 0)

class DirectedWeightedGraph(val vertices: List<Vertice>) {
    val adjList: MutableMap<Vertice, MutableList<Vertice>> = mutableMapOf()

    fun addEdge(from: Vertice, to: Vertice) {
        if (from.weight == to.weight) return // no valid edge!
        if (from.weight > to.weight) {
            val list = adjList.getOrDefault(from, mutableListOf())
            list.add(to)
            adjList[from] = list
        } else {
            val list = adjList.getOrDefault(to, mutableListOf())
            list.add(from)
            adjList[to] = list
        }
    }

    fun getVertice(junction: Int): Vertice {
        return vertices[junction - 1]
    }

    fun neighbours(junction: Int): List<Vertice> {
        val vertice = getVertice(junction)
        return adjList.getOrDefault(vertice, mutableListOf())
    }
}

main(
    arrayOf(
        "2",
        "5",
        "1 2 3 4 3",
        "1 3",
        "2 3",
        "4 3",
        "4 5",
        "6",
        "1 2 3 3 1 4",
        "3 1",
        "3 2",
        "3 4",
        "4 5",
        "1 6"
    )
)
