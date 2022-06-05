package kt.kotlinalgs.app.sorting

val strings = listOf(
    "lampe", "tor", "hendl", "palme", "ronaldo", "ampel", "rot", "abc", "z"
)

val sorted = sortAnagrams(strings)
println(sorted)

val fast = strings.toMutableList()
sortAnagramsFast(fast)
println(fast)

class AnagramComparator : Comparator<String> {
    // O(S) runtime + space
    override fun compare(str1: String, str2: String): Int {
        if (str1.length != str2.length) return str1.compareTo(str2)

        val counts: MutableMap<Char, Int> = mutableMapOf()

        for (char1 in str1) {
            val count = counts.getOrDefault(char1, 0)
            counts[char1] = count + 1
        }

        var diff = 0
        for (char2 in str2) {
            val count = counts[char2]
            if (count == null || count <= 0) return str1.compareTo(str2)
            counts[char2] = count - 1
        }

        return 0
    }
}

class AnagramSortingComparator : Comparator<String> {
    // sorting in O(N log N) time + O(1) if quicksort used
    override fun compare(str1: String, str2: String): Int {
        val s1 = str1.toCharArray().sorted().toString()
        val s2 = str2.toCharArray().sorted().toString()
        return s1.compareTo(s2)
    }
}

// Sort so that anagrams are grouped together
fun sortAnagrams(strings: List<String>): List<String> {
    return strings.sortedWith(AnagramComparator())
}

// time complexity: O(N * S log S), where N = # of strings & S = len of longest string
// space complexity: O(N*S)
fun sortAnagramsFast(strings: MutableList<String>) {
    // modification of bucket sort
    val anagramMap: MutableMap<String, MutableList<String>> = mutableMapOf()

    strings.forEach {
        val sorted = it.toCharArray().sorted().toString()
        val list = anagramMap.getOrDefault(sorted, mutableListOf())
        list.add(it)
        anagramMap[sorted] = list
    }

    var index = 0

    for (anagrams in anagramMap.values) {
        anagrams.forEach {
            strings[index] = it
            index++
        }
    }
}

// optimization with hashmap + string representation: O(N*S) time + space
// --> https://leetcode.com/problems/group-anagrams/solution/
