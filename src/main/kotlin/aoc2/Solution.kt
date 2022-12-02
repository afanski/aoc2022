package aoc2

import java.io.File

val scoreConvertMap = mapOf("X" to "A", "Y" to "B", "Z" to "C")
val scoreMap = mapOf("A" to 1, "B" to 2, "C" to 3)
val outcomeMap = mapOf("X" to 0, "Y" to 3, "Z" to 6)
val losingMoveMap = mapOf("A" to "C", "B" to "A", "C" to "B")
val winningMoveMap = mapOf("A" to "B", "B" to "C", "C" to "A")

fun main() {
    val input = readFile("src/main/kotlin/aoc2/input.txt")

    val moves = input.split("\n").map { it.split(" ") }

    val score = moves.sumOf { calculateScore(it) + scoreMap[scoreConvertMap[it[1]]]!! }
    val score2 = moves.sumOf { calculateScore2(it) + outcomeMap[it[1]]!! }

    println(score)
    println(score2)
}

fun calculateScore2(item: List<String?>): Int {
    val move = item[0]!!
    val outcome = item[1]!!

    return when (outcome) {
        "X" -> scoreMap[losingMoveMap[move]]!!
        "Y" -> scoreMap[move]!!
        else -> scoreMap[winningMoveMap[move]]!!
    }
}

fun calculateScore(item: List<String?>): Int {
    val their = item[0];
    val mine = item[1];

    if (scoreConvertMap[mine] == their) {
        return 3;
    }

    return when (mine) {
        "X" -> if (their == "B") 0 else 6
        "Y" -> if (their == "A") 6 else 0
        else -> if (their == "B") 6 else 0
    }
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)