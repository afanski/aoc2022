package aoc6

import java.io.File

fun main() {
    val input = readFile("src/main/kotlin/aoc6/input.txt")

    val result = input.split("").windowed(4, 1).let { it.takeLast(it.size - 1) }. first {
        it.distinct().size == it.size
    }.joinToString("").let { input.indexOf(it) + it.length }
    println(result)

    val result2 = input.split("").windowed(14, 1).let { it.takeLast(it.size - 1) }. first {
        it.distinct().size == it.size
    }.joinToString("").let { input.indexOf(it) + it.length }
    println(result2)
}


fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)