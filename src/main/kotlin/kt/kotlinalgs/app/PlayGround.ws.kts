package kotlin.kotlinalgs.app

import java.util.*
import kotlin.math.*

println("Playground")

// comparable / comparator / equals / hashcode / copy/clone / sorting

val students = arrayOf(
    Student("Steff", "Cheff", 33),
    Student("Pop", "Hop", 13),
    Student("Pippie", "Langstrumpf", 25),
    Student("Anna", "Langstrumpf", 31),
    Student("Jack", "Cheff", 33),
    Student("Steff", "Cheff", 50),
    Student("Graf", "Dracula", 1500),
    Student("Early", "Bird", 1)
)

data class Student(
    val firstName: String,
    val lastName: String,
    var age: Int
) : Comparable<Student> {
    override fun compareTo(other: Student): Int {
        // default: compare by last name, then by first name
        return when {
            lastName == other.lastName -> firstName.compareTo(other.firstName)
            else -> lastName.compareTo(other.lastName)
        }
    }

    override fun equals(other: Any?): Boolean {
        val oth = other as? Student ?: return false
        return firstName == other.firstName && lastName == other.lastName // ignore age!
    }

    override fun hashCode(): Int {
        return Objects.hash(firstName, lastName)
        // same as: return firstName * 31 + lastName
    }
}

students.sort()
println("Students sort function - name: ${students.map { it.lastName + " " + it.firstName }}")

val desc = students.sortedDescending()
println("Students sort function desc - name: ${desc.map { it.lastName + " " + it.firstName }}")

class StudentAgeComparator : Comparator<Student> {
    override fun compare(first: Student, second: Student): Int {
        return first.age.compareTo(second.age)
    }
}

val ageSorted = students.sortedWith(StudentAgeComparator())
println("Students sort age cmp: ${ageSorted.map { it.age }}")

val ageCmpReversedFunction = compareBy<Student> {
    it.age
}.reversed()

students.sortWith(compareBy<Student> { it.lastName }.thenBy { it.firstName })
println("Students double sort cmp: ${students.map { it.lastName + " " + it.firstName }}")

val ageRevSorted = students.sortedWith(ageCmpReversedFunction)

println("Students sort age cmp func rev: ${ageRevSorted.map { it.age }}")

val clone = students.clone() // not sorted?? Do not use!!
val copy = students.copyOf() // also sorting discarded?

println(students === copy) // structural equality
println(clone.toList() == copy.toList()) // same ref equ.

println("Ori student: ${students.map { "${it.firstName} ${it.lastName} ${it.age}" }}")
println("Cloned student: ${clone.map { "${it.firstName} ${it.lastName} ${it.age}" }}")
println("Copied student: ${copy.map { "${it.firstName} ${it.lastName} ${it.age}" }}")

// conversions
val int = 1
int.toFloat().toDouble()
val str = "string"
str.toIntOrNull()
"-20".toIntOrNull()
int.toByte().toShort().toInt().toLong() // 1 byte -> 2 byte -> 4 byte -> 8 byte
str.toCharArray().map { it }

// math
maxOf(2, 3, 5)
minOf(1.0, 2.0, 3.0)

2.2.roundToInt()

2.0.pow(2)
sqrt(9.0)
ceil(2.3)

log2(16.0)
log(16.0, 2.0)

// collections
val immutableList = listOf(1, 2, 3)
immutableList.last()
immutableList.subList(1, immutableList.size)

val list = mutableListOf(1, 2, 3, 5, 7)
list[1] = 10
list[1]

list.add(8)
list[list.lastIndex]

list.removeAt(list.lastIndex)
list[list.lastIndex]
list.remove(7)
list.size
list.indexOf(3)
list.toIntArray().toList()

val immSet = setOf(1, 2, 3, 1, 2)
immSet.size
immSet.contains(1)
immSet.contains(4)

val mutSet = mutableSetOf(5, 6, 7, 10, 10)
mutSet.add(2)
mutSet.remove(10)

mutSet.forEach {
    println(it)
}

val map = mapOf("a" to 1, "b" to 2, "c" to 3)

map.keys
map.values
map.entries

map.entries.forEach {
    println("${it.key}: ${it.value}")
}

val treeSet = TreeSet<Int>()
treeSet.addAll(listOf(1, 2, 3, 6, 5, 4))
treeSet.forEach {
    println(it) // sorted by natural order!
}
treeSet.pollFirst()
treeSet.pollLast()
treeSet.first()
treeSet.last()

val linkedSet = LinkedHashSet<Int>()
linkedSet.addAll(listOf(1, 2, 3, 6, 5, 4))
linkedSet.forEach {
    println(it) // sorted by insertion order!
}
linkedSet.remove(4)
linkedSet.first()
linkedSet.last()
linkedSet.toList()
linkedSet.toArray()

val hashSet = HashSet<Int>()
hashSet.addAll(listOf(1, 2, 3, 4, 5, 6))
hashSet.forEach {
    println(it) // no guaranteed order!
}
hashSet.remove(4)
hashSet.toArray().map { it }
hashSet.contains(1)
hashSet.toList()
hashSet.toArray()

val treeMap = TreeMap<Int, Int>()
treeMap[1] = 1
treeMap[3] = 3
treeMap[2] = 2
treeMap[4] = 4
treeMap[1] = 5
treeMap.forEach {
    println(it) // sorted by natural key order!
}

treeMap.pollFirstEntry()
treeMap.pollLastEntry().key
treeMap.firstEntry().key
treeMap.firstEntry().value
treeMap.remove(treeMap.lastKey())
treeMap.toList()

val linkedMap = LinkedHashMap<Int, Int>()
linkedMap[1] = 1
linkedMap[2] = 2
linkedMap[3] = 3
linkedMap[6] = 6
linkedMap[5] = 5
linkedMap[4] = 4

linkedMap.forEach {
    println(it) // sorted by insertion order!
}
linkedMap.remove(4)
linkedMap.put(7, 7)
linkedMap.putIfAbsent(7, 8)
linkedMap.get(7)

val hashMap = HashMap<Int, Int>()
hashMap[1] = 1
hashMap[2] = 2
hashMap[3] = 3
hashMap[6] = 6
hashMap[5] = 5
hashMap[4] = 4

hashMap.forEach {
    println(it) // no guaranteed order!
}

hashMap.remove(4)
hashMap.toList().map { it }
hashMap.contains(1)

val stack = Stack<Int>()
stack.push(1)
stack.push(2)
stack.push(3)
stack.peek()
stack.pop()

val queue = LinkedList<Int>()
queue.add(1) // = linkLast
queue.add(2)
queue.add(3)
queue.add(4)

queue.peek() // first
queue.poll() // first
queue.remove() // first

queue.last
queue.first

val pq = PriorityQueue<Int>(11, Comparator.reverseOrder())
pq.addAll(listOf(1, 2, 3, 4, 5))
pq.peek()
pq.poll()

// pair
var pair: Pair<Int, String> = Pair(1,"")

// enum
enum class Test {
    ONE, TWO, THREE
}
val test = Test.ONE
when (test) {
    Test.ONE -> println("1")
}

// strings
// https://zetcode.com/kotlin/strings/

var testString = "Test String"
testString.first()
testString.last()
testString[0]
testString[testString.lastIndex]

"Eagle".compareTo("eagle")
"Eagle".compareTo("eagle", ignoreCase = true)
"eagle" == "eagle"
"eagle" === "eagle" // java/kotlin optimization!

println("Line 1:\nLine 2:\nLine 3:")
testString.toLowerCase() // testString.lowercase()
testString.toUpperCase() // testString.uppercase()
"lower case".capitalize()
testString.decapitalize()
" ".isBlank()
" ".isEmpty()
" ".isNullOrBlank()
"  xx ".trim()
"  xx ".trimStart()
"  xx ".trimEnd()

println("")
for (chr in testString) {
    print(chr)
}

println("")
testString.forEachIndexed { index, chr ->
    print("$index: $chr, ")
}

testString.filter {
    it < 't'
}

val words = listOf("cat", "catty", "bat", "catamaran")
words.filter {
    it.startsWith("c")
}
words.filter {
    it.endsWith("n")
}

"Today is a rainy day".replace("rainy", "sunny")

"This is some sentence".split(" ")

"This is some  sentence".split(" ").joinToString("#")

val sb = StringBuilder()
sb.append(100)
sb.append("200")
sb.length
sb.deleteCharAt(sb.lastIndex)
sb.toString()

// bit manipulation

val bit = 0b10

bit
bit shl 1
bit shr 1
-1 shr 1
-1 ushr 1

bit and 0b11
bit or 0b11
bit xor 0b11

bit.inv()
