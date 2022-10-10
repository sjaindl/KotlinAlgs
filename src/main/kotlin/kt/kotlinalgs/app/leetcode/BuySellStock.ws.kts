package com.sjaindl.kotlinalgsandroid.leetcode

// https://docs.google.com/document/d/1So7wf4yqkQkIsQ6QISXgBnvdyQ9xJJ1xVPTtgNqdjdc/edit
// https://leetcode.com/problems/best-time-to-buy-and-sell-stock/solution/


fun maxProfit(prices: IntArray): Int {
    var maxProfit = 0   //8
    var curMin = Int.MAX_VALUE  // 1

    for (price in prices) { // [5, 1, 7, 9, 2]
        if (curMin != Int.MAX_VALUE && price - curMin > maxProfit) {
            maxProfit = price - curMin
        }
        if (curMin == Int.MAX_VALUE || curMin > price) curMin = price
    }

    return maxProfit
}



maxProfit(intArrayOf(3, 3))
maxProfit(intArrayOf(1, 2, 3))
maxProfit(intArrayOf(5, 1, 7, 9, 2))
