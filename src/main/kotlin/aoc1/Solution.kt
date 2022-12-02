package aoc1

import java.io.File

const val INPUT_FILENAME = "src/main/kotlin/aoc1/input.txt"

fun main() {
    val input = readFile(INPUT_FILENAME);

    val result = input.split("\n\n").maxOfOrNull { it.split("\n").sumOf { it.toInt() } }
    val result2 = input.split("\n\n").map { it.split("\n").sumOf { it.toInt() } }.sortedDescending().take(3).sum()

    println(result)
    println(result2)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)