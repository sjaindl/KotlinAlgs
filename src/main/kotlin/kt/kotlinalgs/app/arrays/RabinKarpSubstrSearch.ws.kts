package kt.kotlinalgs.app.arrays

println("test1")
println("${'a'.toInt()}")
println("${'b'.toInt()}")

class RabinKarpSubstrSearch(var base: Int = 128, var prime: Int = 66600049) {
    fun isSubstring(searched: String, full: String): Boolean {
        if (searched.isEmpty()) return true
        if (searched.length > full.length) return false

        val len = searched.length
        val searchedHash = buildHash(searched, len)
        var rollingHash = buildHash(full, len)

        println("searched $searchedHash")

        if (matches(0, searchedHash, rollingHash, searched, full)) return true

        // Rabin-Karp fingerprint
        // https://www.geeksforgeeks.org/rabin-karp-algorithm-for-pattern-searching/
        for (index in 1 until full.length - len + 1) {
            val asciiToRemove = full[index - 1].toInt()
            val asciiToAdd = full[index + len - 1].toInt()
            println(asciiToRemove)
            println(asciiToAdd)

            //Rabin Karp Fingerprint:
            //rollingHash -= asciiToRemove * Math.pow(base.toDouble(), (len - 1).toDouble()).toInt()
            //rollingHash *= base
            //rollingHash += asciiToAdd

            rollingHash = rollingHash - asciiToRemove + asciiToAdd

            println(rollingHash)
            if (matches(index, searchedHash, rollingHash, searched, full)) return true
        }

        return false
    }

    private fun buildHash(str: String, len: Int): Int {
        var rollingHash = 0
        for (index in len - 1 downTo 0) {
            val ascii = str[index].toInt() - 'a'.toInt()
            // Rabin Karp Fingerprint:
            //rollingHash += ascii * Math.pow(base.toDouble(), index.toDouble()).toInt() % prime

            rollingHash += ascii
        }
        return rollingHash
    }

    private fun matches(
        index: Int,
        searchedHash: Int,
        fullHash: Int,
        searched: String,
        full: String
    ): Boolean {
        if (searchedHash != fullHash) return false

        for (fullIndex in index until index + searched.length) {
            if (full[fullIndex] != searched[fullIndex - index]) return false
        }

        return true
    }
}

val rabin = RabinKarpSubstrSearch()
rabin.isSubstring("eyes", "meyes")
rabin.isSubstring("eyes", "ears")

