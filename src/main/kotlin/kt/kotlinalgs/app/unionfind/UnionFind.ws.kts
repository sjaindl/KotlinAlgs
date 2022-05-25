package kt.kotlinalgs.app.unionfind

println("Test")

// https://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/
class UnionFind(size: Int) {
    private val parent = IntArray(size) {
        it
    }

    private val rank = IntArray(size) { 1 }

    // [0,1,2,3]
    fun find(component: Int): Int {
        //println("find: $component, ${parent[component]}")
        if (component == parent[component]) return component

        // path compression
        parent[component] = find(parent[component])

        return parent[component]
    }

    fun union(first: Int, second: Int) {
        val firstComponent = find(first)
        val secondComponent = find(second)

        if (firstComponent != secondComponent) {
            // union by rank
            when {
                rank[firstComponent] < rank[secondComponent] -> {
                    parent[firstComponent] = secondComponent
                }
                rank[secondComponent] < rank[firstComponent] -> {
                    parent[secondComponent] = firstComponent
                }
                else -> {
                    parent[secondComponent] = firstComponent
                    rank[firstComponent]++
                }
            }
        }
    }
}
