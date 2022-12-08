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
                for (iter in 0 until i) {
                    if (matrix[iter][j] >= matrix[i][j]) {
                        visibleLeft = false
                        break
                    }
                }
                var visibleRight = true
                for (iter in 0 until j) {
                    if (matrix[i][iter] >= matrix[i][j]) {
                        visibleRight = false
                        break
                    }
                }
                var visibleTop = true
                for (iter in i+1 until lines.size) {
                    if (matrix[iter][j] >= matrix[i][j]) {
                        visibleTop = false
                        break
                    }
                }
                var visibleBottom = true
                for (iter in j+1 until lines.size) {
                    if (matrix[i][iter] >= matrix[i][j]) {
                        visibleBottom = false
                        break
                    }
                }
                visible[i][j] = if (visibleBottom || visibleLeft || visibleRight || visibleTop) 1 else 0
            }
        }
    }

    val result = visible.map { it.sum()  }.sum()
    println(result)

    val distances: Array<IntArray> = Array(lines.size) { IntArray(lines.size) }
    matrix.forEachIndexed { i, e ->
        e.forEachIndexed { j, e2 ->
            var il = kotlin.math.max(i - 1, 0)
            while (il > 0 && matrix[il][j] < matrix[i][j]) {
                il--
            }
            val distanceLeft = i - il

            var ir = kotlin.math.min(i + 1, lines.size - 1)
            while (ir < lines.size - 1 && matrix[ir][j] < matrix[i][j]) {
                ir++
            }
            val distanceRight = ir - i

            var jt = kotlin.math.max(j - 1, 0)
            while (jt > 0 && matrix[i][jt] < matrix[i][j]) {
                jt--
            }
            val distanceTop = j - jt

            var jb = kotlin.math.min(j + 1, lines.size - 1)
            while (jb < lines.size - 1 && matrix[i][jb] < matrix[i][j]) {
                jb++
            }
            val distanceBottom = jb - j

            distances[i][j] = distanceBottom * distanceTop * distanceLeft * distanceRight
        }
    }

    val result2 = distances.maxOfOrNull { it.max() }
    println(result2)
}


fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)