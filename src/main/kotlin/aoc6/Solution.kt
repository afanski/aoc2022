package aoc6

import java.io.File

fun main() {
    val input = readFile("src/main/kotlin/aoc6/input.txt")

    val signal = input.split("").windowed(4, 1).let { it.takeLast(it.size - 1) }. first {
        it.distinct().size == it.size
    }.joinToString("")
    println(signal)
    val result = input.indexOf(signal) + signal.length
    println(result)

    val signal2 = input.split("").windowed(14, 1).let { it.takeLast(it.size - 1) }. first {
        it.distinct().size == it.size
    }.joinToString("")
    println(signal2)
    val result2 = input.indexOf(signal2) + signal2.length
    println(result2)
}


fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)