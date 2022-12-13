package aoc13

import java.io.File
import java.lang.Math.max

fun main() {
    val input = readFile("src/main/kotlin/aoc13/input2.txt")
    val packets = input.split("\n\n").map { it.split("\n") }
    println(packets)

    val result = packets.map {
        val (left, right) = it
        compareElements(left, right)
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
        var item1 = firstItem(first)
        var item2 = firstItem(second)

        var left = remainingItems(first)
        var right = remainingItems(second)

        while (item1 != "" && item2 != "") {
            if (item1 == item2) {
                item1 = if (left.startsWith("[")) left.substringBefore("]") + "]" else left.substringBefore(",")
                item2 = if (right.startsWith("[")) right.substringBefore("]") + "]" else right.substringBefore(",")

                if (left.isEmpty()) {
                    return true
                } else if (right.isEmpty()) {
                    return false
                }

                left = if (left.startsWith("[")) left.substringAfter("]") else left.substringAfter(",", "")
                right = if (right.startsWith("[")) right.substringAfter("]") else right.substringAfter(",", "")
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

fun isNumber(input: String): Boolean {
    return input.all { it.isDigit() }
}

fun isList(input: String): Boolean {
    return input.startsWith("[") && input.endsWith("]")
}

fun removeBraces(input: String): String {
    var result = input
    if (result.startsWith("[")) {
        result = result.substringAfter("[")
    }
    if (result.endsWith("]")) {
        result = result.substringBeforeLast("]")
    }
    return result
}


fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)