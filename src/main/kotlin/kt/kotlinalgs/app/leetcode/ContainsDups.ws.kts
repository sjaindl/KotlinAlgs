package com.sjaindl.kotlinalgsandroid.leetcode

// https://docs.google.com/document/d/1PQsc83WmslmYW4NQXKp3M7IBXTmyMYRYNiuAvVOlVDU/edit
// https://leetcode.com/problems/contains-duplicate/solution/

fun containsDuplicate(nums: IntArray): Boolean {
    val contained: MutableSet<Int> = mutableSetOf()
    for (num in nums) {
        if (contained.contains(num)) return true
        contained.add(num)
    }

    return false
}

containsDuplicate(intArrayOf(3, 3))
containsDuplicate(intArrayOf(1, 2, 3))
containsDuplicate(intArrayOf(5, 1, 7, 9, 2))

// Solved: 9:20
