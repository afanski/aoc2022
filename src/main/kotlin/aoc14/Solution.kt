package aoc14

import java.io.File
import kotlin.math.sign

fun main() {
    val input = readFile("src/main/kotlin/aoc14/input.txt")
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

    array.forEach {
        println(it.joinToString(""))
    }
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)