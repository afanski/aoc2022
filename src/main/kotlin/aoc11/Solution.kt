package aoc11

import java.io.File
import java.math.BigInteger
import java.time.Month

class Monkey {
    var index: Int = 0
    var items: MutableList<BigInteger> = mutableListOf()
    var testDivisible: BigInteger = BigInteger.ZERO
    var throwIfTrue = BigInteger.ZERO
    var throwIfFalse = BigInteger.ZERO
    var operation: String = ""
    var inspected = BigInteger.ZERO

    fun runOperation(item: BigInteger): BigInteger {
        val operations = operation.split(" ")
        var result = BigInteger.ZERO
        var first = BigInteger.ZERO
        var second = BigInteger.ZERO
        if (operations[0] == "old") {
            first = item
        } else {
            first = operations[0].toBigInteger()
        }
        if (operations[2] == "old") {
            second = item
        } else {
            second = operations[2].toBigInteger()
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
                monkey.items = it.substringAfter("Starting items: ").split(",").map { s -> s.trim().toBigInteger() } as MutableList<BigInteger>
            } else if (it.startsWith("  Operation")) {
                monkey.operation = it.substringAfter("Operation: new = ")
            } else if (it.startsWith("  Test: ")) {
                monkey.testDivisible = it.filter { it.isDigit() }.toBigInteger()
            } else if (it.startsWith("    If true:")) {
                monkey.throwIfTrue = it.filter { it.isDigit() }.toBigInteger()
            } else if (it.startsWith("    If false:")) {
                monkey.throwIfFalse = it.filter { it.isDigit() }.toBigInteger()
            }
        }
        parsedMonkeys.add(monkey)
    }

    val allTests = parsedMonkeys.map { it.testDivisible }.reduce { acc, i -> acc * i }

    var round = 0
    repeat(10000) {
        round++
        parsedMonkeys.forEach {
            it.items.forEachIndexed { index, item ->
                run {
                    var newLevel = it.runOperation(item)
                    if (newLevel % it.testDivisible == BigInteger.ZERO) {
                        val target = parsedMonkeys[it.throwIfTrue.toInt()]
                        target.items.add(newLevel % allTests)
                    } else {
                        val target = parsedMonkeys[it.throwIfFalse.toInt()]
                        target.items.add(newLevel % allTests)
                    }
                    it.inspected++
                }
            }
            it.items = mutableListOf()
        }
        if (round % 1000 == 0) {
            println("Round $round. Levels: ${parsedMonkeys.map { it.inspected }}")
        }
    }


    val var1 = parsedMonkeys.map { it.inspected }.sortedDescending()[0]
    println(var1)
    val var3 = parsedMonkeys.map { it.inspected }.sortedDescending()[1]
    println(var3)
    println(var1 * var3)
}

fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)