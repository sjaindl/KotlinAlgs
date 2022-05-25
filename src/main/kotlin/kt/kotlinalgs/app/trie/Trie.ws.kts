println("Test")

val trie = Trie()

trie.addWord("Test")

trie.containsPrefix("Tes")
trie.containsPrefix("Tse")

trie.isWord("Tes")
trie.isWord("Test")

data class TrieNode(
    val value: Char,
    val children: MutableMap<Char, TrieNode> = mutableMapOf(),
    var terminates: Boolean = false
)

class Trie {
    val root = TrieNode('*')

    fun addWord(word: String) {
        var curNode = root

        for (index in 0 until word.length) {
            val char = word[index]
            val child = curNode.children.get(char)
            if (child != null) {
                curNode = child
            } else {
                val newNode = TrieNode(char)
                curNode.children[char] = newNode
                curNode = newNode
            }

            if (index == word.length - 1) curNode.terminates = true
        }
    }

    fun containsPrefix(prefix: String): Boolean {
        if (prefix.isEmpty()) return true

        var cur: TrieNode? = root
        var index = 0

        while (cur != null && index < prefix.length) {
            cur = cur?.children[prefix[index]]
            index++
        }

        return index >= prefix.length
    }

    fun isWord(word: String): Boolean {
        if (word.isEmpty()) return false

        var cur: TrieNode? = root
        var index = 0

        while (cur != null && index < word.length) {
            cur = cur?.children?.get(word[index])
            index++
        }

        return index == word.length && cur?.terminates == true
    }
}