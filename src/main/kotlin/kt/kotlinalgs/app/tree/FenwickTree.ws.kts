package kt.kotlinalgs.app.tree

println("test")

val array = intArrayOf(
    5, 2, 9, -3, 5,
    20, 10, -7, 2, 3,
    -4, 0, -2, 15, 5
)
val tree = FenwickTree(array)

println(tree.sum(0)) // 0
println(tree.sum(1)) // 5
println(tree.sum(5)) // 18
println(tree.sum(2, 5)) // 13
println(tree.sum(10)) //46
println(tree.sum(6, 10)) // 28
println(tree.sum(15)) // 60
println(tree.sum(11, 15)) // 14

// https://www.youtube.com/watch?v=uSFzHCZ4E-8
class FenwickTree(val array: IntArray) { // or Binary Index Tree
    // fenwick tree starting at index 1! makes bit logic simpler
    // O(N) space
    var binaryIndexArray = IntArray(array.size + 1)

    init {
        construct()
    }

    // O(N) runtime
    private fun construct() {
        array.copyInto(binaryIndexArray, 1)

        // update immediate neighbours in bottom-up fashion
        for (index in 1 until binaryIndexArray.size) {
            val parent = index + (index and -index)
            if (parent < binaryIndexArray.size) {
                binaryIndexArray[parent] += binaryIndexArray[index]
            }
        }
    }

    // O(log N) runtime
    fun update(index: Int, newValue: Int) {
        var curIndex = index
        var diff = newValue - array[index]

        while (curIndex < binaryIndexArray.size) {
            binaryIndexArray[curIndex] += diff
            curIndex += curIndex and -curIndex
        }
    }

    // O(log N) runtime
    fun sum(toIndex: Int): Int {
        var curIndex = toIndex
        var sum = 0

        // e.g. for 7:
        // 7  =     0111
        // -7 =     1001
        // 7 & -7 = 0001
        // 7 - (7 & -7) = 0111 - 0001 = 0110 = 6

        // 6  =     0110
        // -6 =     1010
        // 6 & -6 = 0010
        // 6 - (6 & -6) = 0110 - 0010 = 0100 = 4

        // 4  =     0100
        // -4 =     1100
        // 4 & -4 = 0100
        // 4 - (4 & -4) = 0100 - 0100 = 0000 = 0
        while (curIndex > 0) {
            sum += binaryIndexArray[curIndex]
            curIndex -= curIndex and -curIndex
        }

        return sum
    }

    fun sum(fromIndex: Int, toIndex: Int): Int {
        return sum(toIndex) - sum(fromIndex - 1)
    }
}
