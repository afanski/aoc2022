package aoc12

import java.io.File
import kotlin.math.min

var startX = 0
var startY = 0
var endX = 0
var endY = 0
var X = 0
var Y = 0
var array = Array(1) { IntArray(1) }

fun main() {
    val input = readFile("src/main/kotlin/aoc12/input.txt")
    val rows = input.split("\n")
    array = Array(rows.size) { IntArray(rows.first().length) }
    X = rows.size
    Y = rows.first().length

    rows.forEachIndexed { index, r ->
        run {
            r.forEachIndexed { index2, h ->
                run {
                    if (h == 'S') {
                        array[index][index2] = 0
                    } else if (h == 'E') {
                        array[index][index2] = 'z'.code - 'a'.code + 2
                    }
                    array[index][index2] = h.code - 'a'.code + 1
                }
            }
        }
    }

    for (i in 0..rows.size - 1) {
        for (j in 0..rows.first().length - 1) {
            if (array[i][j] == -13) {
                startX = i
                startY = j
                array[i][j] = 0
            }
            if (array[i][j] == -27) {
                endX = i
                endY = j
                array[i][j] = 0
            }
        }
    }

    val queue = mutableListOf<Pair<Int, Int>>()

    queue.add(Pair(startX, startY))

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
    }

    println(array)
    println(('S'.code - 'a'.code + 1))
    println(('E'.code - 'a'.code + 1))
    println(('E'.code - 'a'.code + 1))
    println(findPath(startX, startY, -1, mutableSetOf()))
}

fun findPath(i: Int, j: Int, count: Int, seen: MutableSet<Pair<Int, Int>>): Int? {
    if (i == endX && j == endY) {
        return count
    }

    seen.add(Pair(i, j))

    val newCount = count + 1
    var results = mutableSetOf<Int>()

    if (checkIJ(i - 1, j) && !seen.contains(Pair(i - 1, j)) && array[i-1][j] - array[i][j] <= 1) {
        findPath(i - 1, j, newCount, seen)?.let {
            results.add(it)
        }
    }
    if (checkIJ(i + 1, j) && !seen.contains(Pair(i + 1, j)) && array[i+1][j] - array[i][j] <= 1) {
        findPath(i + 1, j, newCount, seen)?.let {
            results.add(it)
        }
    }
    if (checkIJ(i, j - 1) && !seen.contains(Pair(i, j - 1)) && array[i][j-1] - array[i][j] <= 1) {
        findPath(i, j - 1, newCount, seen)?.let {
            results.add(it)
        }
    }
    if (checkIJ(i, j + 1) && !seen.contains(Pair(i, j + 1))&& array[i][j+1] - array[i][j] <= 1) {
        findPath(i, j + 1, newCount, seen)?.let {
            results.add(it)
        }
    }
    if (checkIJ(i - 1, j + 1) && !seen.contains(Pair(i - 1, j + 1)) && array[i-1][j+1] - array[i][j] <= 1) {
        findPath(i - 1, j + 1, newCount, seen)?.let {
            results.add(it)
        }
    }
    if (checkIJ(i + 1, j - 1) && !seen.contains(Pair(i + 1, j - 1)) && array[i+1][j-1] - array[i][j] <= 1) {
        findPath(i + 1, j - 1, newCount, seen)?.let {
            results.add(it)
        }
    }
    if (checkIJ(i - 1, j - 1) && !seen.contains(Pair(i - 1, j - 1)) && array[i-1][j-1] - array[i][j] <= 1) {
        findPath(i - 1, j - 1, newCount, seen)?.let {
            results.add(it)
        }
    }
    if (checkIJ(i + 1, j + 1) && !seen.contains(Pair(i + 1, j + 1)) && array[i+1][j+1] - array[i][j] <= 1) {
        findPath(i + 1, j + 1, newCount, seen)?.let {
            results.add(it)
        }
    }

    return results.minOrNull()
}

fun checkIJ(i: Int, j: Int): Boolean {
    return !(i < 0 || j < 0 || i >= X || j >= Y)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)