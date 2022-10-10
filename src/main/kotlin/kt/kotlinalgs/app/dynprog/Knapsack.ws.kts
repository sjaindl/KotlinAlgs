package com.sjaindl.kotlinalgsandroid.dynprog

// https://www.geeksforgeeks.org/0-1-knapsack-problem-dp-10/

class KnapSack {
    // [60, 100, 120]
    fun calculateKnapsack(values: IntArray, weights: IntArray, maxWeight: Int): Int {
        val cache: MutableMap<Memo, Int> = mutableMapOf()
        println("cache.size")
        //val cache: Array<IntArray> = Array(weights.size) { IntArray(weights.size) }

        println(cache.size)

        if (values.isEmpty()) return 0
        return calculateKnapsack(values, weights, maxWeight, 0, 0, 0, cache)
    }

    private fun calculateKnapsack(
        values: IntArray, // [60, 100, 120]
        weights: IntArray, // [10, 20, 30]
        maxWeight: Int, // 50
        index: Int, // 0
        weight: Int, // 0
        value: Int, // 0
        cache: MutableMap<Memo, Int>
        //cache: Array<IntArray>
    ): Int {
        if (index >= values.size) return value

        val memo = Memo(index, weight)
        cache[memo]?.let {
            //println(cache[index][weight])
            //if (cache[index][weight] != 0) {
            //return cache[index][weight]
            return it
        }

        // include index or not
        val newWeight = weight + weights[index] // 50
        val newValue = value + values[index] // 220
        var bestValue = value
        if (newWeight <= maxWeight) {
            bestValue =
                calculateKnapsack(values, weights, maxWeight, index + 1, newWeight, newValue, cache)
        }
        bestValue = Math.max(
            bestValue,
            calculateKnapsack(values, weights, maxWeight, index + 1, weight, value, cache)
        )

        //cache[index][weight] = bestValue
        cache[memo] = bestValue

        return bestValue
    }

    data class Memo(val index: Int, val weight: Int)
}

val knapsack = KnapSack()
val result = knapsack.calculateKnapsack(intArrayOf(60, 100, 120), intArrayOf(10, 20, 30), 50)
println(result)
// 19:25 - 19:55

/*
input: int[] weights, int[] values, maxWeight
output: int (max value)

example:
values: [60, 100, 120]
weights: [10, 20, 30]
maxW: 50
-> 220 (100 + 120 with W 50)

BF:
loop thr values:
    2 branches:
        include, if (<= max weight)
        don't include
        update, if value > curMax
O(2^n), O(N) if recursively

Opt.: 
Dynamic Programming
cache max value after index

 */

