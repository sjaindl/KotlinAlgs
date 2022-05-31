package kt.kotlinalgs.app.dynprog

println("Test")

val mp = Permutations()

println(mp.permutationsWithDups("1123"))

class Permutations {

    /*
     1123 ->
     counts: [1:2, 2:1, 3:1]
     [""] ->
        [1123, 1132, 1231, 1213, 1312, 1321, 2113, 2131, 2311, 3112, 3121, 3211]
     */
    fun permutationsWithDups(full: String): List<String> {
        if (full.isEmpty()) return emptyList()

        var perms: MutableList<String> = mutableListOf() // ["1"]

        val counts = buildCounts(full)

        println(counts)

        permutationsWithDupsRec(StringBuilder(), counts, perms)

        return perms
    }

    private fun permutationsWithDupsRec(
        prefix: StringBuilder,
        counts: MutableMap<Char, Int>,
        result: MutableList<String>
    ) {
        if (counts.isEmpty()) { // BC
            result.add(prefix.toString())
            return
        }

        for (char in counts.keys.toMutableSet()) {
            prefix.append(char)
            removeOrDecrement(counts, char)

            permutationsWithDupsRec(prefix, counts, result)

            addOrIncrement(counts, char)
            prefix.deleteCharAt(prefix.length - 1)
        }
    }

    private fun buildCounts(full: String): MutableMap<Char, Int> {
        val counts: MutableMap<Char, Int> = mutableMapOf()
        full.forEach {
            addOrIncrement(counts, it)
        }

        return counts
    }

    private fun addOrIncrement(counts: MutableMap<Char, Int>, char: Char) {
        val count = counts.getOrDefault(char, 0)
        counts[char] = count + 1
    }

    private fun removeOrDecrement(counts: MutableMap<Char, Int>, char: Char) {
        val count = counts.getOrDefault(char, 0)
        if (count <= 1) counts.remove(char)
        else counts[char] = count - 1
    }
}