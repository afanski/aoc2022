package aoc8

import java.io.File

fun main() {
    val input = readFile("src/main/kotlin/aoc8/input.txt")

    val lines = input.split("\n")

    val matrix: Array<IntArray> = Array(lines.size) { IntArray(lines.size) }
    lines.forEachIndexed { i, e ->
        e.forEachIndexed { j, e2 ->
            matrix[i][j] = e2.digitToInt()
        }
    }

    val visible: Array<IntArray> = Array(lines.size) { IntArray(lines.size) }

    matrix.forEachIndexed { i, e ->
        e.forEachIndexed { j, e2 ->
            visible[i][j] = 0
            if (i == 0 || i == lines.size - 1 || j == 0 || j == lines.size - 1) {
                visible[i][j] = 1
            } else {
                var visibleLeft = true
                for (iter in 0..i-1) {
                    if (matrix[iter][j] >= matrix[i][j]) {
                        visibleLeft = false
                        break
                    }
                }
                var visibleRight = true
                for (iter in 0..j-1) {
                    if (matrix[i][iter] >= matrix[i][j]) {
                        visibleRight = false
                        break
                    }
                }
                var visibleTop = true
                for (iter in i+1..lines.size - 1) {
                    if (matrix[iter][j] >= matrix[i][j]) {
                        visibleTop = false
                        break
                    }
                }
                var visibleBottom = true
                for (iter in j+1..lines.size - 1) {
                    if (matrix[i][iter] >= matrix[i][j]) {
                        visibleBottom = false
                        break
                    }
                }
                visible[i][j] = if (visibleBottom || visibleLeft || visibleRight || visibleTop) 1 else 0
            }
        }
    }


    println(visible)
    val result = visible.map { it.sum()  }.sum()
    println(result)
}


fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)