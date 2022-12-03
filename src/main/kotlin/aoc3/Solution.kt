import java.io.File

fun main() {
    val input = readFile("src/main/kotlin/aoc3/input.txt")

    val lines = input.split("\n").map {
        it.split("").subList(1, it.length + 1).let {
            it.take(it.size / 2).intersect(it.takeLast(it.size / 2))
        }
    }.map { it.first() }.map { it.toCharArray().first().code }.map { if (it >= 97) it - 96 else it - 38 }.sum()

    println(lines)
    println('A'.code)
    println('a'.code)
    println('G'.code)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)