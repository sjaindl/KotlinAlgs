package kt.kotlinalgs.app.arrays

import kotlin.math.pow

println("test1")
println("${'a'.toInt()}")
println("${'b'.toInt()}")

val rabin = RabinKarpSubstrSearch()
rabin.isSubstring("eyes", "meyes")
rabin.isSubstring("eyes", "meyesss")
rabin.isSubstring("eyes", "ears")
rabin.isSubstring("eyes", "eye")
rabin.isSubstring("eyes", "eyyeseyes")
rabin.isSubstring("xxx", "ears xx  ssd xxx wed")

// with Rabin Karp fingerprint O(b + s) time - worst case: O(b * s) time
// O(s) space (2 hashes of smaller string len)

class RabinKarpSubstrSearch(var base: Int = 128, var prime: Int = 66600049) {
    fun isSubstring(searched: String, full: String): Boolean {
        if (searched.isEmpty()) return true
        if (searched.length > full.length) return false

        val len = searched.length
        val searchedHash = buildHash(searched, len)
        var rollingHash = buildHash(full, len)

        // println("searched $searchedHash")

        if (matches(0, searchedHash, rollingHash, searched, full)) return true

        // Rabin-Karp fingerprint
        // https://www.geeksforgeeks.org/rabin-karp-algorithm-for-pattern-searching/
        for (index in 1 until full.length - len + 1) {
            val asciiToRemove = full[index - 1] - 'a'
            val asciiToAdd = full[index + len - 1] - 'a'
            // println(asciiToRemove)
            // println(asciiToAdd)

            //Rabin Karp Fingerprint:
            // println("remove: ${full[index - 1]}")
            rollingHash -= asciiToRemove * base.toDouble().pow((len - 1)).toInt()
            rollingHash *= base
            rollingHash += asciiToAdd

            //rollingHash = rollingHash - asciiToRemove + asciiToAdd

            // println(rollingHash)
            if (matches(index, searchedHash, rollingHash, searched, full)) return true
        }

        return false
    }

    private fun buildHash(str: String, len: Int): Int {
        var rollingHash = 0
        //println("__")
        for (index in 0 until len) {
            val ascii = str[index] - 'a'

            // Rabin Karp Fingerprint:
            val exponent = len - index - 1
            rollingHash += ascii * base.toDouble().pow(exponent.toDouble()).toInt() % prime

            // rollingHash += ascii
            //   print(str[index])
        }

        // println("")
        // println("$str: $rollingHash")
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

// Alternatives:
// https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/
// https://www.baeldung.com/java-pattern-matching-suffix-tree
