package com.sjaindl.kotlinalgsandroid.leetcode

// https://docs.google.com/document/d/1Z8ia8-90S7QFfq5S0LkE_TbFoG9fXQekmeTEnzZx9aY/edit
// https://leetcode.com/problems/product-of-array-except-self/

fun productExceptSelf(nums: IntArray): IntArray {  //[1,2,4,3]
    if (nums.isEmpty()) return IntArray(0)

    val left = IntArray(nums.size)  // [1,1,2,8]
    val right = IntArray(nums.size)  //  [0,0,3,1]

    left[0] = 1
    right[nums.size - 1] = 1

    for (index in 1 until nums.size) {
        left[index] = left[index - 1] * nums[index - 1]
        right[nums.size - 1 - index] = right[nums.size - index] * nums[nums.size - index]
    }

    val output = IntArray(nums.size)
    for (index in nums.indices) {
        output[index] = left[index] * right[index]
    }

    return output
}

productExceptSelf(intArrayOf(3, 3)).toList()
productExceptSelf(intArrayOf(2, 2, 0, 2)).toList()
productExceptSelf(intArrayOf(1, 2, 4, 3)).toList()

// Solved: 20:04
