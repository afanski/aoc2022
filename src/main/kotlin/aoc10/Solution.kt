package aoc10

import java.io.File

class CPU {
    private var cycle = 1
    private var x = 1
    var strength = 0;

    fun run(command: String, param: Int?) {
        if (command == "noop") {
            cycle++
            recordStrength()
        } else {
            cycle++
            recordStrength()
            cycle++
            x+=param!!
            recordStrength()
        }
    }

    private fun recordStrength() {
        if (cycle == 20) {
            strength += cycle * x
        } else if ((cycle-20) % 40 == 0) {
            strength += cycle * x
        }
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