package kt.kotlinalgs.app.dynprog

println("Test")

getSubsets(
    listOf(1, 2, 3, 4)
)

/*
 [1,2,3] -> [
 ordering important? no!!
    [], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]
 ]

 curList .. subsets of cur iteration
 resultList .. holding all subsets

1. start with resultList = empty set
2. for each inputval in input list:
    curList = empty
    for each resval in resultlist:
        newVal = resval + inputval
        curList.add(newVal)
    resultList.add(curList)

O(n * 2^n) runtime + sets (either element is in set or not) + n time to fill each complete subset
 */
fun getSubsets(set: List<Int>): List<List<Int>> {
    val setOfSubsets: MutableList<MutableList<Int>> = mutableListOf()

    setOfSubsets.add(mutableListOf()) // empty set

    for (value in set) {
        val curLevelLists: MutableList<MutableList<Int>> = mutableListOf()
        setOfSubsets.forEach {
            val newList = it.toMutableList()
            newList.add(value)
            curLevelLists.add(newList)
        }
        setOfSubsets.addAll(curLevelLists)
    }

    return setOfSubsets
}
