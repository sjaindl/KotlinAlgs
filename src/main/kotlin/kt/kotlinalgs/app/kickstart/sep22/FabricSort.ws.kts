var line = 0
var args: Array<String> = arrayOf()

fun readLine(): String? {
    val result = args[line]
    line++
    return result
}

fun main(mainArgs: Array<String>) {
    args = mainArgs
    line = 0

    // C = color (string a-z)
    // D = durability (int)
    // U = unique id (int)
    /*
    N fabrics, sort:
    ADA C asc, U if eq. asc.
    Charles D asc., U if eq. asc.

    Output nr. of same fabric sort

    idea:
    sort A, sort B, iterate and cpm if u. ID same
    =(2 * N log N + N) = O(N)

    15 mins
     */

    val testCases = readLine()?.toIntOrNull() ?: 0

    for (testCase in 1 until testCases + 1) {
        val numFabrics = readLine()?.toIntOrNull() ?: return
        val fabrics: MutableList<Fabric> = mutableListOf()

        for (num in 0 until numFabrics) {
            val (color, d, u) = (readLine()?.split(" ")?.map { it }) ?: return
            val durability = d.toIntOrNull() ?: return
            val id = u.toIntOrNull() ?: return

            fabrics.add(Fabric(color, durability, id))
        }

        val adaCmp = compareBy<Fabric> { it.color }.thenBy { it.id }
        val charlesCmp = compareBy<Fabric> { it.durability }.thenBy { it.id }

        val adaSorted = fabrics.sortedWith(adaCmp)
        val charlesSorted = fabrics.sortedWith(charlesCmp)

        var sameCount = 0

        for (index in 0 until numFabrics) {
            if (adaSorted[index] == charlesSorted[index]) sameCount++
        }

        println("Case #$testCase: $sameCount")
    }
}

data class Fabric(val color: String, val durability: Int, val id: Int)

main(arrayOf(
    "3",
    "2",
    "blue 2 1",
    "yellow 1 2",
    "2",
    "blue 2 1",
    "brown 2 2",
    "1",
    "red 1 1"
))




