package aoc11

import java.io.File
import java.time.Month

class Monkey {
    var index: Int = 0
    var items: MutableList<Int> = mutableListOf()
    var testDivisible: Int = 0
    var throwIfTrue = 0
    var throwIfFalse = 0
    var operation: String = ""
    var inspected = 0

    fun runOperation(item: Int): Int {
        val operations = operation.split(" ")
        var result = 0
        var first = 0
        var second = 0
        if (operations[0] == "old") {
            first = item
        } else {
            first = operations[0].toInt()
        }
        if (operations[2] == "old") {
            second = item
        } else {
            second = operations[2].toInt()
        }
        if (operations[1] == "+") {
            result = first + second
        } else {
            result = first * second
        }
        return result
    }
}

fun main() {
    val input = readFile("src/main/kotlin/aoc11/input.txt")
    val monkeys = input.split("\n\n")

    val parsedMonkeys = mutableListOf<Monkey>()
    monkeys.forEach {
        val monkey = Monkey()
        it.split("\n").forEach {
            if (it.startsWith("Monkey ")) {
                monkey.index = it.filter { it.isDigit() }.toInt()
            } else if (it.startsWith("  Starting items: ")) {
                monkey.items = it.substringAfter("Starting items: ").split(",").map { s -> s.trim().toInt() } as MutableList<Int>
            } else if (it.startsWith("  Operation")) {
                monkey.operation = it.substringAfter("Operation: new = ")
            } else if (it.startsWith("  Test: ")) {
                monkey.testDivisible = it.filter { it.isDigit() }.toInt()
            } else if (it.startsWith("    If true:")) {
                monkey.throwIfTrue = it.filter { it.isDigit() }.toInt()
            } else if (it.startsWith("    If false:")) {
                monkey.throwIfFalse = it.filter { it.isDigit() }.toInt()
            }
        }
        parsedMonkeys.add(monkey)
    }

    repeat(20) {
        parsedMonkeys.forEach {
            it.items.forEachIndexed { index, item ->
                run {
                    var newLevel = it.runOperation(item) / 3
                    if (newLevel % it.testDivisible == 0) {
                        parsedMonkeys[it.throwIfTrue].items.add(newLevel)
                    } else {
                        parsedMonkeys[it.throwIfFalse].items.add(newLevel)
                    }
                    it.inspected++
                }
            }
            it.items = mutableListOf()
        }
    }


    val var1 = parsedMonkeys.map { it.inspected }.sortedDescending()[0]
    println(var1)
    val var3 = parsedMonkeys.map { it.inspected }.sortedDescending()[1]
    println(var3)
    println(var1 * var3)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)