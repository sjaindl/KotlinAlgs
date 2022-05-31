package kt.kotlinalgs.app.dynprog

println("Test")

val mp = Permutations()

println(mp.permutationsWithoutDups("1234"))

class Permutations {

    /*
     123 ->
     [""] -> ["1"] -> ["12","21"] -> ["312", "132", "123", "321", "231", "213"]
     */
    fun permutationsWithoutDups(full: String): List<String> {
        if (full.isEmpty()) return emptyList()

        var perms: MutableList<String> = mutableListOf() // ["1"]

        perms.add("") // empty string

        for (char in full) { // 2
            val newPerms: MutableList<String> = mutableListOf() // []
            perms.forEach { oldString -> // ["1"]
                //println("oldString: $oldString")

                for (index in 0 until oldString.length + 1) {
                    val before = oldString.substring(0, index)
                    val after = oldString.substring(index)
                    val newString = "$before$char$after"

                    //println("before: $before, after: $after, newString: $newString")

                    newPerms.add(newString)
                }
            }
            perms = newPerms
        }

        return perms
    }
}