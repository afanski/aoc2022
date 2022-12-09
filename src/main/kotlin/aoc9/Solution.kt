package aoc9

import java.io.File
import kotlin.math.abs
import kotlin.math.sign

class RopeNode {
    private var x = 0
    private var y = 0
    private var prev: RopeNode? = null
    val visited = mutableSetOf(Pair(0, 0))

    constructor(prev: RopeNode?) {
        this.prev = prev
    }

    private fun moveTail() {
        prev?.moveTail()
        if (shouldMove()) {
            val xDiff = x - prev!!.x
            x += sign(xDiff.toDouble()).toInt() * -1
            val yDiff = y - prev!!.y
            y += sign(yDiff.toDouble()).toInt() * -1
            visited.add(Pair(x, y))
        }
    }

    private fun shouldMove(): Boolean {
        if (prev == null) {
            return false
        }
        return abs(prev!!.x - x) > 1 || abs(prev!!.y - y) > 1
    }

    private fun moveHeadUp() {
        findHead().y++
    }

    private fun moveHeadDown() {
        findHead().y--
    }

    private fun moveHeadLeft() {
        findHead().x--
    }

    private fun moveHeadRight() {
        findHead().x++
    }

    private fun findHead(): RopeNode {
        var result = this
        while (result.prev != null) {
            result = result.prev!!
        }
        return result
    }

    fun moveHead(direction: String) {
        when (direction) {
            "U" -> moveHeadUp()
            "D" -> moveHeadDown()
            "L" -> moveHeadLeft()
            "R" -> moveHeadRight()
        }
        moveTail()
    }
}

fun initRope(size: Int): RopeNode {
    var tail = RopeNode(null)
    var currentSize = 1
    while (currentSize < size) {
        tail = RopeNode(tail)
        currentSize++
    }
    return tail
}

fun main() {
    val input = readFile("src/main/kotlin/aoc9/input.txt")
    val commands = input.split("\n").map { it.split(" ") }

    val rope10 = initRope(10)
    val rope2 = initRope(2)

    commands.forEach {
        val direction = it.first()
        val count = it.last().toInt()

        repeat(count) {
            rope10.moveHead(direction)
            rope2.moveHead(direction)
        }
    }

    println(rope2.visited.size)
    println(rope10.visited.size)

}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)