package aoc12

import java.io.File

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


    X = rows.size
    Y = rows.first().length

    rows.forEachIndexed { index, r ->
        run {
            r.forEachIndexed { index2, h ->
                run {
                    if (h == 'S') {
                        array[index][index2] = 1
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

    var starts = mutableSetOf<Pair<Int, Int>>()
    rows.forEachIndexed { index, r ->
        run {
            r.forEachIndexed { index2, h ->
                run {
                    if (h == 'S' || h == 'a') {
                        starts.add(Pair(index, index2))
                    }
                }
            }
        }
    }

    println(array)
    val minDistances = starts.map { processPath(it) }

    println(minDistances.filter { it > 0 }.min())
}

fun processPath(start: Pair<Int, Int>): Int {
    val queue = mutableListOf<Pair<Int, Int>>()
    val seen = mutableSetOf<Pair<Int, Int>>()
    val distances = Array(X) { IntArray(Y) }

    queue.add(Pair(start.first, start.second))

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()

        val currentDistance = distances[current.first][current.second]

        seen.add(current)

        process(current.first + 1, current.second, seen, queue, distances, currentDistance, current)
        process(current.first - 1, current.second, seen, queue, distances, currentDistance, current)
        process(current.first, current.second + 1, seen, queue, distances, currentDistance, current)
        process(current.first, current.second - 1, seen, queue, distances, currentDistance, current)
    }

    return distances[endX][endY]
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
    if (checkIJ(i, j) && array[i][j] - array[current.first][current.second] <= 1) {
        distances[i][j] = distance + 1
        if (!seen.contains(Pair(i, j))) {
            queue.add(Pair(i, j))
        }
        seen.add(Pair(i, j))
    }
}


fun checkIJ(i: Int, j: Int): Boolean {
    return !(i < 0 || j < 0 || i >= X || j >= Y)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)