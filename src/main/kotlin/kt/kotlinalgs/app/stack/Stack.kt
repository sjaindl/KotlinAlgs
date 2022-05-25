package kt.kotlinalgs.app

import kt.kotlinalgs.app.SingleLinkedList

class Stack<T> {
    val list = SingleLinkedList<T>()

    fun push(value: T) {
        list.addFront(SingleNode<T>(value))
    }

    fun pop(): T {
        return list.removeFirst().value
    }

    fun peak(): T {
        val node = list.root ?: throw java.lang.IllegalStateException("No elements on stack")
        return node.value
    }
}

fun main() {
    val stack = Stack<Int>()
    stack.push(1)
    stack.push(2)
    println(stack.peak())
    println(stack.pop())
    println(stack.pop())
    try {
        print(stack.pop())
    } catch(e: RuntimeException) {
        println("exception catched!")
    }
    
}
