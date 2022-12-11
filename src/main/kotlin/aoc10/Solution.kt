package aoc10

import java.io.File

class CPU {
    private var cycle = 0
    private var x = 1
    var strength = 0;
    private var row: String = ""

    fun run(command: String, param: Int?) {
        if (command == "noop") {
            printCrt()
            cycle++
            recordStrength()
        } else {
            printCrt()
            cycle++
            recordStrength()
            printCrt()
            cycle++
            x+=param!!
            recordStrength()
        }
    }

    private fun printCrt() {
        val pos = cycle % 40
        if (sprite().contains(pos)) {
            row = row + "#"
        } else {
            row = row + "."
        }
        if (row.length == 40) {
            println(row)
            row = ""
        }
    }

    private fun recordStrength() {
        if (cycle == 20) {
            strength += cycle * x
        } else if ((cycle-20) % 40 == 0) {
            strength += cycle * x
        }
    }

    private fun sprite(): List<Int> {
        if (x == 0) {
            return listOf(0, 1, 2)
        }
        if (x == 39) {
            return listOf(37, 38, 39)
        }
        return listOf(x - 1, x, x + 1)
    }
}

fun main() {
    val cpu = CPU()
    val input = readFile("src/main/kotlin/aoc10/input.txt")
    val lines = input.split("\n")

    lines.forEach {
        val list = it.split(" ")
        if (list.size == 1) {
            cpu.run("noop", null)
        } else {
            cpu.run("addx", list.last().toInt())
        }
    }

    println(cpu.strength)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)