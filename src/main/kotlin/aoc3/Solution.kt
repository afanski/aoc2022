import java.io.File

fun main() {
    val input = readFile("src/main/kotlin/aoc3/input.txt")

    val result1 = input.split("\n").map {it.toCharArray().let { it.take(it.size / 2).intersect(it.takeLast(it.size / 2)) } }.map { it.first().code }.map { if (it >= 97) it - 96 else it - 38 }.sum()

    println(result1)

    val result2 = input.split("\n").chunked(3).map { it[0].toCharArray().intersect(it[1].toCharArray().asIterable()).intersect(it[2].toCharArray().asIterable()) }.map { it.first().code }.map { if (it >= 97) it - 96 else it - 38 }.sum()

    println(result2)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)