package aoc1

fun main() {
    val input = readFile(INPUT_FILENAME);
    val result = input.split("\n\n").map { it.split("\n").sumOf { it.toInt() } }.sortedDescending().take(3).sum()
    println(result)
}
