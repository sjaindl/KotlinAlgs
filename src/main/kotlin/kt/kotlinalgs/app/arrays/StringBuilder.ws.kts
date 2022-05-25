package kt.kotlinalgs.app.arrays

println("test1")

class MyStringBuilder(var capacity: Int = 8) {
    private var chars = mutableListOf<Char>()

    fun append(newString: String) {
        newString.toCharArray().forEach {
            chars.add(it)
        }
    }

    fun toMyString(): String {
        return chars.toString()
    }
}

val sb = MyStringBuilder()
sb.append("a")
sb.append("b")
sb.append("c")
sb.toMyString()


class MyStringBuilder2(var capacity: Int = 8) {
    private var chars = Array<Char>(capacity) { ' ' }
    private var numElements = 0

    fun append(newString: String) {
        if (newString.isEmpty()) return

        val neededSize = numElements + newString.length
        ensureCapacity(neededSize)

        newString.forEach {
            chars[numElements] = it
            numElements++
        }

    }

    fun toMyString(): String {
        return chars.toCharArray().joinToString("")//.substring(0, numElements)
    }

    private fun ensureCapacity(required: Int) {
        if (capacity >= required) return

        val newCapacity = if (capacity * 2 < required) required else capacity * 2
        var newChars = Array<Char>(newCapacity) { ' ' }
        chars.forEachIndexed { index, char ->
            newChars[index] = char
        }

        chars = newChars
    }
}


val sb2 = MyStringBuilder2()
sb2.append("a")
sb2.append("b")
sb2.append("c")

for (i in 0 until 100) {
    sb2.append("$i")
}

sb2.toMyString()
