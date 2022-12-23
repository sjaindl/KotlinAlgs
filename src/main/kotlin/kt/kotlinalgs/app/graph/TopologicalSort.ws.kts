package com.sjaindl.kotlinalgsandroid.graph

import java.util.*

public class TopologicalSort {
    /*
    we have 2 queues. determines a possible order to e.g. produce dependent parts
     */

    fun test() {
        val vertice0 = Vertice(0)
        val vertice1 = Vertice(1)
        val vertice2 = Vertice(2)
        val vertice3 = Vertice(3)
        val vertice4 = Vertice(4)
        val vertice5 = Vertice(5)
        val vertices = listOf(
            vertice0, vertice1, vertice2, vertice3, vertice4, vertice5
        )

// example from: https://www.geeksforgeeks.org/topological-sorting/
        val graph = Graph(vertices)
        graph.addEdge(vertice2, vertice3)
        graph.addEdge(vertice3, vertice1)
        graph.addEdge(vertice4, vertice1)
        graph.addEdge(vertice4, vertice0)
        graph.addEdge(vertice5, vertice0)
        graph.addEdge(vertice5, vertice2)

        graph.neighbours(vertice0)
        graph.neighbours(vertice2)

        graph.adjList.forEach {
            println("${it.key}: ${it.value}")
        }

        println("graph1: " + graph.adjList.size)
        val order = sort(graph)
        println(order)
    }

    fun sort(graph: Graph): List<Int> {
        println("count: ${graph.vertices.size}")
        println("graph2: " + graph.adjList.size)
        // 1. count inbounds
        countInbounds(graph)

        graph.adjList.forEach {
            println("${it.key}: ${it.value}")
        }

        // 2. init queues
        val processing = LinkedList<Vertice>()
        val output = LinkedList<Int>()

        graph.vertices.forEach {
            if (it.inboundCount == 0) {
                processing.addFirst(it)
            }
        }

        println(processing.size)

        // 3. process..
        while (!processing.isEmpty()) {
            val next = processing.removeLast()
            output.addLast(next.value) // = add

            // update neighbors, put in processing new nodes with inbound 0
            graph.neighbours(next).forEach {
                it.inboundCount--
                println(it.inboundCount)
                if (it.inboundCount == 0) {
                    processing.add(it)
                }
            }
        }

        // 4. output
        if (output.size != graph.vertices.size) {
            // There's a cycle, no topological sort possible
            return emptyList()
        }

        return output
    }

    private fun countInbounds(graph: Graph) {
        graph.vertices.forEach {
            println(graph.adjList.size)

            graph.neighbours(it).forEach { neighbour ->
                neighbour.inboundCount++
            }
        }
    }
}

public class Graph(val vertices: List<Vertice>) {
    var adjList: MutableMap<Int, MutableList<Vertice>> = mutableMapOf()

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
    val value: Int,
    var inboundCount: Int = 0
)

TopologicalSort().test()
