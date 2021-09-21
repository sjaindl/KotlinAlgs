package kotlin.kotlinalgs.app

import kt.kotlinalgs.app.SingleLinkedList

class App {
    val greeting: String
        get() {
            val stack = SingleLinkedList<Int>()
            return "Hello world."
        }
}

fun main() {
    println(App().greeting)
}
