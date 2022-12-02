package aoc1

import java.io.File

const val INPUT_FILENAME = "/Users/aliakseif/rnd/aoc2022/AoC/src/aoc1.main/kotlin/input_1.txt"

fun main() {
    val input = readFile(INPUT_FILENAME);
    val result = input.split("\n\n").maxOfOrNull { it.split("\n").sumOf { it.toInt() } }
    println(result)
}

fun main2() {
    val input = readFile(INPUT_FILENAME);
    val result = input.split("\n\n").map { it.split("\n").sumOf { it.toInt() } }
    println(result)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)