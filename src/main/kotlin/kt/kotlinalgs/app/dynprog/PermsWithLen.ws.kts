package kt.kotlinalgs.app.dynprog

println("Test")

val array = intArrayOf(1, 2, 3, 4, 5)
val perms = Solution().permsWithLen(array = array, desiredLen = 4)

println(perms)

class Solution {
    val output: MutableList<MutableList<Int>> = mutableListOf()

    fun permsWithLen(array: IntArray, desiredLen: Int): List<List<Int>> {
        if (array.size < desiredLen) return emptyList()

        val current: MutableList<Int> = mutableListOf()

        permsWithLenRecursive(array, desiredLen, 0, current)

        return output
    }

    private fun permsWithLenRecursive(
        array: IntArray,
        desiredLen: Int,
        curIndex: Int,
        current: MutableList<Int>
    ) {
        if (current.size == desiredLen) {
            output.add(current.toMutableList()) // copy!
            return
        }

        if (curIndex >= array.size || current.size >= desiredLen) return

        // TODO: Optimization early exit if there are too few indices left to fill the list

        // 2 choices: Include element at index, or not
        // don't include:
        permsWithLenRecursive(array, desiredLen, curIndex + 1, current)

        // include:
        current.add(array[curIndex])
        permsWithLenRecursive(array, desiredLen, curIndex + 1, current)
        current.removeAt(current.lastIndex)
    }
}
