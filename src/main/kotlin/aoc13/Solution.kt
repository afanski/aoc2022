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
            if (value!!) {
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
            val comparisonResult = compareElements(item1, item2)
            if (comparisonResult == null) {
                item1 = firstItemNew(left)
                item2 = firstItemNew(right)

                if (item1.isEmpty() && item2.isNotEmpty()) {
                    return true
                } else if (item1.isNotEmpty() && item2.isEmpty()) {
                    return false
                }

                left = remainingItemsNew(left)
                right = remainingItemsNew(right)
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