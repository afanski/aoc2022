package aoc14

import java.io.File
import kotlin.math.sign

fun main() {
    val input = readFile("src/main/kotlin/aoc14/input2.txt")
    val result = input.split("\n").map { it.split(" -> ").map { it.split(",").map { it.toInt() } } }
    val xMin = result.map { it.map { it.first() } }.map { it.min() }.min()
    val xMax = result.map { it.map { it.first() } }.map { it.max() }.max()
    val yMax = result.map { it.map { it.last() } }.map { it.max() }.max()
    println(xMin)
    println(xMax)
    println(yMax)

    println(result)
    val normalized = result.map { it.map { listOf(it.first() - xMin, it.last()) } }
    println(normalized)
    var array = Array(yMax + 1) { IntArray(xMax - xMin + 1) }
    normalized.forEach {
        var current: Pair<Int, Int>? = null
        it.forEach {
            if (current == null) {
                current = Pair(it.first(), it.last())
                array[current!!.second][current!!.first] = 1
            } else {
                val diffX = sign((it.first() - current!!.first).toDouble()).toInt()
                val diffY = sign((it.last() - current!!.second).toDouble()).toInt()

                while (current!!.first != it.first() || current!!.second != it.last()) {
                    current = Pair(current!!.first + diffX, current!!.second + diffY)
                    array[current!!.second][current!!.first] = 1
                }
            }
        }
    }


    var output = runOnce(xMin, array)
    var newOutput = runOnce(xMin, array)
    var runs = 2
    while (newOutput != output) {
        runs++
        output = newOutput
        newOutput = runOnce(xMin, array)

        array.forEach {
            println(it.joinToString(""))
        }
        println(runs)
    }
}

private fun runOnce(xMin: Int, array: Array<IntArray>): String {
    var startX = 500 - xMin
    var startY = 0
    array[startY][startX] = 2

    while (nextPosition(startX, startY, array) != null) {
        val next = nextPosition(startX, startY, array)
        array[startY][startX] = 0
        if (next!!.first == -1 && next.second == -1) {
            array[startY][startX] = 3
            break
        } else {
            array[next!!.second][next!!.first] = 2
            startX = next.first
            startY = next.second
        }
    }


    val result =     array.map {
        it.joinToString("")
    }.joinToString("")
    return result
}

fun nextPosition(startX: Int, startY: Int, array: Array<IntArray>): Pair<Int, Int>? {
    if (startY + 1 >= array.size) {
        return null
    } else if (array[startY + 1][startX] == 0) {
        return Pair(startX, startY + 1)
    }

    if (startX - 1 < 0) {
        return null
    } else if (array[startY + 1][startX - 1] == 0) {
        return Pair(startX - 1, startY + 1)
    }

    if (startX + 1 >= array.first().size) {
        return null
    } else if (array[startY + 1][startX + 1] == 0) {
        return Pair(startX + 1, startY + 1)
    }

    return Pair(-1, -1)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)