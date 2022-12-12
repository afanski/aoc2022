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
var min = Int.MAX_VALUE

fun main() {
    val input = readFile("src/main/kotlin/aoc12/input.txt")
    val rows = input.split("\n")
    array = Array(rows.size) { IntArray(rows.first().length) }
    val distances = Array(rows.size) { IntArray(rows.first().length) }
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
                array[i][j] = 'z'.code - 'a'.code + 2
            }
        }
    }

    val queue = mutableListOf<Pair<Int, Int>>()
    val seen = mutableSetOf<Pair<Int, Int>>()

    queue.add(Pair(startX, startY))

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()

        val currentDistance = distances[current.first][current.second]

        seen.add(current)

        process(current.first + 1, current.second, seen, queue, distances, currentDistance, current)
        process(current.first - 1, current.second, seen, queue, distances, currentDistance, current)
        process(current.first, current.second + 1, seen, queue, distances, currentDistance, current)
        process(current.first, current.second - 1, seen, queue, distances, currentDistance, current)

    }

    println(array)
    println(('S'.code - 'a'.code + 1))
    println(('E'.code - 'a'.code + 1))
    println(('E'.code - 'a'.code + 1))
    println(distances[endX][endY])
//    println(findPath(startX, startY, 0, mutableSetOf(), mutableListOf()))
}

private fun process(
    i: Int,
    j: Int,
    seen: MutableSet<Pair<Int, Int>>,
    queue: MutableList<Pair<Int, Int>>,
    distances: Array<IntArray>,
    distance: Int,
    current: Pair<Int, Int>
) {
    println("process $i and $j")
    if (checkIJ(i, j) && array[i][j] - array[current.first][current.second] <= 1) {
        distances[i][j] = distance + 1
        if (!seen.contains(Pair(i, j))) {
            queue.add(Pair(i, j))
        }
        seen.add(Pair(i, j))
    }
}

fun findPath(i: Int, j: Int, count: Int, seenOld: MutableSet<Pair<Int, Int>>, pathOld: MutableList<String>): Int? {
    if (pathOld.size > min) {
        return null
    }
    if (i == endX && j == endY) {
        println("Path : $pathOld, count: ${pathOld.size}" )
        if (pathOld.size < min) {
            min = pathOld.size
        }
        return pathOld.size
    }

    val newCount = count + 1
    var results = mutableSetOf<Int>()

    seenOld.add(Pair(i, j))
    var seen = seenOld.toMutableSet()

    if (checkIJ(i - 1, j) && !seenOld.contains(Pair(i - 1, j)) && array[i-1][j] - array[i][j] <= 1) {
        var path = pathOld.toMutableList()
        path.add("^" + array[i-1][j] + " - " + newCount)

        seen.add(Pair(i - 1, j))
        findPath(i - 1, j, newCount, seen, path)?.let {
            results.add(it)
        }
    }

    if (checkIJ(i + 1, j) && !seenOld.contains(Pair(i + 1, j)) && array[i+1][j] - array[i][j] <= 1) {
        var path = pathOld.toMutableList()
        path.add("V" + array[i+1][j] + " - " + newCount)
        seen.add(Pair(i + 1, j))
        findPath(i + 1, j, newCount, seen, path)?.let {
            results.add(it)
        }
    }
    if (checkIJ(i, j - 1) && !seenOld.contains(Pair(i, j - 1)) && array[i][j-1] - array[i][j] <= 1) {
        var path = pathOld.toMutableList()
        path.add("<" + array[i][j-1] + " - " + newCount)
        seen.add(Pair(i, j - 1))
        findPath(i, j - 1, newCount, seen, path)?.let {
            results.add(it)
        }
    }
    if (checkIJ(i, j + 1) && !seenOld.contains(Pair(i, j + 1))&& array[i][j+1] - array[i][j] <= 1) {
        var path = pathOld.toMutableList()
        path.add(">"  + array[i][j+1] + " - " + newCount)
        seen.add(Pair(i, j + 1))
        findPath(i, j + 1, newCount, seen, path)?.let {
            results.add(it)
        }
    }

    return results.minOrNull()
}

fun checkIJ(i: Int, j: Int): Boolean {
    return !(i < 0 || j < 0 || i >= X || j >= Y)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)