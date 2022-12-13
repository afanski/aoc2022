package aoc13

import java.io.File

fun main() {
    val input = readFile("src/main/kotlin/aoc13/input.txt")
    val packets = input.split("\n\n").map { it.split("\n") }
    println(packets)

    val orders = packets.map {
        val (left, right) = it
        compareElements(left, right)
    }

    println(orders)

    var result = 0
    orders.forEachIndexed { i, value ->
        run {
            if (value > 0) {
                result += i + 1
            }
        }
    }
    println(result)

    var packets2 = readFile("src/main/kotlin/aoc13/input.txt")
        .split("\n")
        .filter { it.isNotEmpty() }.toMutableList()
    packets2.add("[[2]]")
    packets2.add("[[6]]")

    packets2 = packets2.sortedWith { o1, o2 -> -compareElements(o1, o2) }.toMutableList()

    println((packets2.indexOf("[[2]]") + 1) * (packets2.indexOf("[[6]]") + 1))
}

fun compareElements(first: String, second: String): Int {
    if (isNumber(first) && isNumber(second)) {
        if (first.toInt() == second.toInt()) {
            return 0
        }
        return second.toInt() - first.toInt()
    } else if (isList(first) && isNumber(second)) {
        return compareElements(first, "[$second]")
    } else if (isNumber(first) && isList(second)) {
        return compareElements("[$first]", second)
    } else if (isList(first) && isList(second)) {
        var item1 = firstItemNew(first)
        var item2 = firstItemNew(second)

        var left = remainingItemsNew(first)
        var right = remainingItemsNew(second)

        if (item1.isEmpty() && item2.isNotEmpty()) {
            return 1
        } else if (item1.isNotEmpty() && item2.isEmpty()) {
            return -1
        }

        while (item1 != "" && item2 != "") {
            val comparisonResult = compareElements(item1, item2)
            if (comparisonResult == 0) {
                item1 = firstItemNew(left)
                item2 = firstItemNew(right)

                if (item1.isEmpty() && item2.isNotEmpty()) {
                    return 1
                } else if (item1.isNotEmpty() && item2.isEmpty()) {
                    return -1
                }

                left = remainingItemsNew(left)
                right = remainingItemsNew(right)
                continue
            } else {
                return comparisonResult
            }
        }
        return 0
    } else {
        throw RuntimeException("wtf")
    }
}

fun firstItemNew(list: String): String {
    if (removeBraces(list).all { it.isDigit() }) return removeBraces(list)
    var braces = 0
    var index = 1
    while (braces > 0 || index < list.length - 1) {
        if (list[index] == '[') {
            braces++
        } else if (list[index] == ']') {
            braces--
        } else if (braces == 0 && list[index] == ',') {
            return list.substring(1, index)
        }
        index++
    }
    return removeBraces(list)
}

fun remainingItemsNew(list: String): String {
    var braces = 0
    var index = 1
    while (braces > 0 || index < list.length - 1) {
        if (list[index] == '[') {
            braces++
        } else if (list[index] == ']') {
            braces--
        }
        if (braces == 0 && list[index] == ',') {
            return "[${list.substring(index + 1, list.length - 1)}]"
        }
        index++
    }
    return ""
}

fun isNumber(input: String): Boolean {
    return input.all { it.isDigit() }
}

fun isList(input: String): Boolean {
    return input.startsWith("[") && input.endsWith("]")
}

fun removeBraces(input: String): String {
    var result = input
    if (result.startsWith("[") && result.endsWith("]")) {
        result = result.substringAfter("[")
        result = result.substringBeforeLast("]")
    }
    return result
}


fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)