package aoc4

import java.io.File

fun main() {
    val input = readFile("src/main/kotlin/aoc4/input.txt")
    val result = input.split("\n").map { it.split(",").map { it.split("-") }.map { it[0].toInt()..it[1].toInt() } }.count { it[0].toSet().containsAll(it[1].toSet()) || it[1].toSet().containsAll(it[0].toSet()) }
    println(result)

    val result2 = input.split("\n").map { it.split(",").map { it.split("-") }.map { it[0].toInt()..it[1].toInt() } }.count { it[0].toSet().intersect(it[1].toSet()).isNotEmpty() }
    println(result2)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)