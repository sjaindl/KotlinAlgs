var line = 0
var args: Array<String> = arrayOf()

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

// Time: O(V) see below
// Space: O(V) .. containers + levelMap + processed
fun main(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    /*
    1:15 h...

    Water container system
    N containers

     .. tree with N-1 bidir. pipes,
     .. 2 connected: adj. levels
     container 1 = root (bottom)
        1 connection below (exc. root)
        0 .. n-1 connections on top
     container cap.: 1 liter max (init. empty 0)

    q queries, int i (container #), each query 1l++ in container

    output: num of completely filled containers
     */

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (numContainers, numQueries) = (readLine()?.split(" ")?.map { it.toInt() }) ?: return

        val containers1: MutableMap<Int, MutableSet<Int>> = mutableMapOf()
        val containers2: MutableMap<Int, MutableSet<Int>> = mutableMapOf()

        var completelyFilled = if (numContainers == 1 && numQueries >= 1) 1 else 0

        for (num in 0 until numContainers - 1) {
            val (container1, container2) = (readLine()?.split(" ")?.map { it.toInt() }) ?: return

            val set1 = containers1.getOrDefault(container1, mutableSetOf())
            set1.add(container2)
            containers1[container1] = set1

            val set2 = containers2.getOrDefault(container2, mutableSetOf())
            set2.add(container1)
            containers2[container2] = set2
        }

        for (num in 0 until numQueries) {
            val query = readLine()?.toInt()
        }

        val levelMap: MutableMap<Int, Int> = mutableMapOf()

        /*
                1. build tree
                start with container 1 (could be c1 or c2)
                .. no we just need to know how many containers are on each level

                .. put in 2 ordered maps (by insertion order) [c1: c2], and [c2: c1]
                start with 1 ..

                map counts [Int: Int] (level to count)
                 .. DFS
             */

        val processed: MutableSet<Int> = mutableSetOf()
        dfs(containers1, containers2, levelMap, 1, 1, processed)

        var waterLeft = numQueries
        var curLevel = 1
        while (waterLeft > 0) {
            val curLevelWater = levelMap.getOrDefault(curLevel, 0)
            if (curLevelWater == 0) break

            if (waterLeft >= curLevelWater) completelyFilled += curLevelWater

            waterLeft -= curLevelWater
            curLevel++
        }

        println("Case #$testCase: $completelyFilled")
    }
}

//O(E+V) = O(2V) = O(V) because E = V-1
fun dfs(
    containers1: MutableMap<Int, MutableSet<Int>>,
    containers2: MutableMap<Int, MutableSet<Int>>,
    levelMap: MutableMap<Int, Int>,
    curContainer: Int,
    curLevel: Int,
    processed: MutableSet<Int>,
) {
    if (!containers1.containsKey(curContainer) && !containers2.containsKey(curContainer)) return

    processed.add(curContainer)

    val curLevelCount = levelMap.getOrDefault(curLevel, 0)
    levelMap[curLevel] = curLevelCount + 1

    val nextOfC1 = containers1.getOrDefault(curContainer, mutableSetOf()).toMutableSet()
    containers1[curContainer] = mutableSetOf()
    nextOfC1.forEach { nextContainer ->
        if (processed.contains(nextContainer)) return@forEach
        dfs(containers1, containers2, levelMap, nextContainer, curLevel + 1, processed)
    }

    val nextOfC2 = containers2.getOrDefault(curContainer, mutableSetOf()).toMutableSet()
    containers2[curContainer] = mutableSetOf()
    nextOfC2.forEach { nextContainer ->
        if (processed.contains(nextContainer)) return@forEach
        dfs(containers1, containers2, levelMap, nextContainer, curLevel + 1, processed)
    }
}

fun mainWithGraphSolution(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val (numContainers, numQueries) = (readLine()?.split(" ")?.map { it.toInt() }) ?: return

        val vertices: MutableList<Vertice<Int>> = mutableListOf()
        for (verticeNum in 1 until numContainers + 1) {
            vertices.add(Vertice(verticeNum))
        }

        val graph = Graph(vertices)

        for (num in 0 until numContainers - 1) {
            val (container1, container2) = (readLine()?.split(" ")?.map { it.toInt() }) ?: return

            graph.addEdge(container1, container2)
        }

        for (num in 0 until numQueries) {
            val query = readLine()?.toInt()
        }

        val levelMap = IntArray(numContainers + 1)

        for (container in 1 until numContainers) {
            levelMap[container] = graph.lookup[container]!!.depth
        }

        var completelyFilled = 0

        var waterLeft = numQueries
        var curLevel = 1
        while (waterLeft > 0) {
            val curLevelWater = levelMap[curLevel]
            if (curLevelWater == 0) break

            if (waterLeft >= curLevelWater) completelyFilled += curLevelWater

            waterLeft -= curLevelWater
            curLevel++
        }

        println("Case #$testCase: $completelyFilled")
    }
}

main(arrayOf(
    "2",
    "1 1",
    "1",
    "3 2",
    "1 2",
    "1 3",
    "1",
    "2",
))

main(arrayOf(
    "2",
    "4 4",
    "1 2",
    "1 3",
    "2 4",
    "3",
    "3",
    "3",
    "3",
    "5 2",
    "1 2",
    "5 3",
    "3 1",
    "2 4",
    "4",
    "5"
))

mainWithGraphSolution(arrayOf(
    "2",
    "1 1",
    "1",
    "3 2",
    "1 2",
    "1 3",
    "1",
    "2",
))

mainWithGraphSolution(arrayOf(
    "2",
    "4 4",
    "1 2",
    "1 3",
    "2 4",
    "3",
    "3",
    "3",
    "3",
    "5 2",
    "1 2",
    "5 3",
    "3 1",
    "2 4",
    "4",
    "5"
))

data class Vertice<T : Comparable<T>>(
    val value: T,
    var depth: Int = 1, // default fo root
)

class Graph<T : Comparable<T>>(val vertices: List<Vertice<T>>) {
    private var adjList: MutableMap<T, MutableList<Vertice<T>>> = mutableMapOf()

    var lookup: MutableMap<T, Vertice<T>> = mutableMapOf()

    fun addEdge(from: Vertice<T>, to: Vertice<T>) {
        val list = adjList.getOrDefault(from.value, mutableListOf())
        list.add(to)
        adjList[from.value] = list

        to.depth = from.depth + 1
    }

    fun addEdge(from: T, to: T) {
        val fromVertice = lookup[from] ?: return
        val toVertice = lookup[to] ?: return

        addEdge(fromVertice, toVertice)
        addEdge(toVertice, fromVertice)
    }

    fun neighbours(vertice: Vertice<T>): List<Vertice<T>> {
        return adjList.getOrDefault(vertice.value, listOf())
    }
}
