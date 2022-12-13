package aoc13

import java.io.File
import java.lang.Math.max

fun main() {
    val input = readFile("src/main/kotlin/aoc13/input3.txt")
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
            if (value != null && value) {
                result += i + 1
            }
        }
    }
    println(result)
}

fun compareElements(first: String, second: String): Boolean? {
    if (isNumber(first) && isNumber(second)) {
        if (first.toInt() == second.toInt()) {
            return null
        }
        return first.toInt() < second.toInt()
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
            return true
        } else if (item1.isNotEmpty() && item2.isEmpty()) {
            return false
        }

        while (item1 != "" && item2 != "") {
            if (item1 == item2) {
                item1 = firstItemNew(left)
                item2 = firstItemNew(right)

                if (left.isEmpty()) {
                    return true
                } else if (right.isEmpty()) {
                    return false
                }

                left = remainingItemsNew(left)
                right = remainingItemsNew(right)
                continue
            }

            val comparisonResult = compareElements(item1, item2)
            if (comparisonResult == null) {
                item1 = left.substringBefore(",")
                item2 = right.substringBefore(",")

                left = left.substringAfter(",")
                right = right.substringAfter(",")
                continue
            } else {
                return comparisonResult
            }
        }
        return null
    } else {
        throw RuntimeException("wtf")
    }
}

fun firstItem(list: String): String {
    val removedBraces = removeBraces(list)
    if (removedBraces.startsWith("[")) {
        return removedBraces.substringBefore("]") + "]"
    } else if (!removedBraces.contains(",")) {
        return removedBraces
    }
    return removedBraces.substringBefore(",")
}

fun firstItemNew(list: String): String {
    if (list.all { it.isDigit() }) return list
    val removedBraces = removeBraces(list)
    if (removedBraces.all { it.isDigit() }) return removedBraces
    var braces = 0
    var index = 0
    while (braces > 0 || index < removedBraces.length - 1) {
        if (removedBraces[index] == '[') {
            braces++
        } else if (removedBraces[index] == ']') {
            braces--
        } else if (braces == 0 && removedBraces[index] == ',') {
            return removedBraces.substring(0, index)
        }
        index++
    }
    return removedBraces.substring(0, index)
}

fun remainingItems(list: String): String {
    val removedBraces = removeBraces(list)
    if (removedBraces.startsWith("[")) {
        var result = removedBraces.substringAfter("]")
        if (result.startsWith(",")) {
            result = result.substringAfter(",")
        }
        return result
    } else if (!removedBraces.contains(",")) {
        return ""
    }
    return removedBraces.substringAfter(",")
}
fun remainingItemsNew(list: String): String {
    val removedBraces = removeBraces(list)
    var braces = 0
    var index = 0
    while (braces > 0 || index < removedBraces.length - 1) {
        if (removedBraces[index] == '[') {
            braces++
        } else if (removedBraces[index] == ']') {
            braces--
        } else if (braces == 0 && removedBraces[index] == ',') {
            return removedBraces.substring(index + 1)
        }
        index++
    }
    return list
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