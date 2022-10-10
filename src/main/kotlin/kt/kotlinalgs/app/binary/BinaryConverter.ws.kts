package com.sjaindl.kotlinalgsandroid.binary

import java.util.*

// https://www.youtube.com/watch?v=uSFzHCZ4E-8

class BinaryConverter {
    fun binToDec(number: String): Int {
        if (number.isEmpty()) return 0

        val remaining = number.toCharArray()
        var decimal = 0
        var multiplier = 1
        var remIndex = remaining.size - 1

        while (remIndex >= 0) {
            if (remaining[remIndex] != '0' && remaining[remIndex] != '1') return 0 //invalid
            val cur = if (remaining[remIndex] == '0') 0 else 1
            decimal += cur * multiplier

            remIndex--
            multiplier *= 2
        }

        return decimal
    }

    fun decToBin(number: Int): String {
        val binary = LinkedList<Char>()
        var currentDec = number

        while (currentDec != 0) {
            val digit = if ((currentDec and 1) == 1) '1' else '0'
            binary.addFirst(digit)
            currentDec /= 2
        }

        val chars = binary.toCharArray()
        return String(chars)
    }
}

val converter = BinaryConverter()
converter.binToDec("11111111111111111111111111111111")
converter.binToDec("1001")
converter.decToBin(9)
converter.decToBin(100)

converter.decToBin(20)
converter.decToBin(30)
converter.decToBin(50)
