package aoc5

import java.io.File

fun initStacks(): Map<Int, ArrayDeque<String>> {
    return mapOf(
        1 to ArrayDeque(listOf("J", "H", "P", "M", "S", "F", "N", "V")),
        2 to ArrayDeque(listOf("S", "R", "L", "M", "J", "D", "Q")),
        3 to ArrayDeque(listOf("N", "Q", "D", "H", "C", "S", "W", "B")),
        4 to ArrayDeque(listOf("R", "S", "C", "L")),
        5 to ArrayDeque(listOf("M", "V", "T", "P", "F", "B")),
        6 to ArrayDeque(listOf("T", "R", "Q", "N", "C")),
        7 to ArrayDeque(listOf("G", "V", "R")),
        8 to ArrayDeque(listOf("C", "Z", "S", "P", "D", "L", "R")),
        9 to ArrayDeque(listOf("D", "S", "J", "V", "G", "P", "B", "F")),
    )
}

fun main() {

    val input = readFile("src/main/kotlin/aoc5/input.txt")
    val stack = initStacks()

    input.split("\n").map { it.split(" ").filter { it.matches(Regex("\\d+")) } }.map { it.map { it.toInt() } }
        .map { c ->
            repeat(c[0]) {
                moveStack(stack, c[1], c[2])
            }
        }

    val result = String(stack.values.map { it.last() }.map { it.toCharArray().first() }.toCharArray())

    println(result)
}

fun moveStack(stack: Map<Int, ArrayDeque<String>>, from: Int, to: Int) {
    val item = stack[from]!!.removeLast()
    stack[to]!!.addLast(item)
}

fun moveNStacks(stack: Map<Int, ArrayDeque<String>>, count: Int, from: Int, to: Int) {
    val item = stack[from]!!.removeLast()
    stack[to]!!.addLast(item)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)