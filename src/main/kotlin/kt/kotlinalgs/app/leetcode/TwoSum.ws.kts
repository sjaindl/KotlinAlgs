package com.sjaindl.kotlinalgsandroid.leetcode

// https://leetcode.com/problems/two-sum/

data class Result(val firstIndex: Int, val secondIndex: Int)

fun twoSum(data: IntArray, target: Int): Result? {
    if (data.isEmpty()) return null

    val valueToIndexMap: MutableMap<Int, Int> = mutableMapOf()
    data.forEachIndexed { index, number ->
        val complement = target - number
        valueToIndexMap[complement]?.let {
            return Result(it, index)
        }
        valueToIndexMap[number] = index
    }

    return null
}

twoSum(intArrayOf(3, 3), 6) // [3, 3], 6 -> Result(0, 1)
twoSum(intArrayOf(1, 2, 3), 4) // [1, 2, 3], 4 -> Result(0, 2)
twoSum(intArrayOf(1, 2, 3), 2)
