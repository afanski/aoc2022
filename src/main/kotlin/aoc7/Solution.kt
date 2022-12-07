package aoc7

import java.io.File

enum class FileType {
    FILE, DIR
}

class AocFile {
    val name: String
    val type: FileType?
    val path: String
    val size: Int?
    val contents: MutableList<AocFile> = mutableListOf()
    val parent: AocFile?

    constructor(name: String, type: FileType, path: String, parent: AocFile? = null, size: Int? = null) {
        this.name = name
        this.type = type
        this.path = path
        this.size = size
        this.parent = parent
    }

    fun size(): Int {
        if (type == FileType.FILE) {
            return size!!
        } else {
            return contents.map { c -> c.size() }.sum()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AocFile) return false

        if (path != other.path) return false

        return true
    }

    override fun hashCode(): Int {
        return path.hashCode()
    }

    override fun toString(): String {
        return "AocFile(name='$name', type=$type, path='$path', size=$size)"
    }

}

fun main() {
    val input = readFile("src/main/kotlin/aoc7/input.txt")

    val lines = input.split("\n")

    val rootFile = AocFile("/", FileType.DIR, "/")
    var currentFile = rootFile
    var currentPath = "/"
    lines.forEach {
        if (it.startsWith("$")) {
            val cliInput = it.substring(2, it.length).split(" ")
            val command = cliInput.first()
            val param = if (cliInput.size > 1) cliInput.last() else null

            if (command == "cd") {
                if (param == "/") {
                    currentPath = "/"
                    currentFile = rootFile
                } else if (param == "..") {
                    currentPath = currentPath.substring(0, currentPath.indexOfLast { it == '/' })
                    currentFile = currentFile.parent!!
                } else {
                    if (currentPath == "/") {
                        currentPath = "$currentPath/$param"
                    } else {
                        currentPath = "$currentPath/$param"
                    }
                    if (currentFile.contents.none { it.name == param }) {
                        println("cant find $param in $currentFile")
                    }
                    currentFile = currentFile.contents.find { it.name == param }!!
                }
            }
        } else {
            if (it.startsWith("dir")) {
                val fileName = it.substringAfter("dir ")
                val file = AocFile(fileName, FileType.DIR, ("$currentPath/$fileName").replace("//", "/"), currentFile)
                if (!currentFile.contents.contains(file)) {
                    currentFile.contents.add(file)
                }
            } else {
                var size = it.split(" ").first()
                var name = it.split(" ").last()
                val file = AocFile(name, FileType.FILE, ("$currentPath/$name").replace("//", "/"), currentFile, size.toInt())
                if (!currentFile.contents.contains(file)) {
                    currentFile.contents.add(file)
                }
            }
        }
    }

    val resultDirs = mutableListOf<AocFile>()
    findDirs(resultDirs, rootFile, 100000)
    println(resultDirs.sumOf { it.size() })

    val allDirs = mutableListOf<AocFile>()
    findAllDirs(allDirs, rootFile)

    val filter = allDirs.filter { 70000000 - rootFile.size() + it.size() >= 30000000 }
    val result = filter.minOf { it.size() }
    println(result)
}

fun findDirs(result: MutableList<AocFile>, rootFile: AocFile, size: Int) {
    if (rootFile.type == FileType.DIR && rootFile.size() < size && !result.contains(rootFile)) {
        result.add(rootFile)
    }

    rootFile.contents.forEach {
        if (it.type == FileType.DIR && !result.contains(it)) {
            findDirs(result, it, size)
        }
    }
}

fun findAllDirs(result: MutableList<AocFile>, rootFile: AocFile) {
    if (rootFile.type == FileType.DIR && !result.contains(rootFile)) {
        result.add(rootFile)
    }

    rootFile.contents.forEach {
        if (it.type == FileType.DIR && !result.contains(it)) {
            findAllDirs(result, it)
        }
    }
}
fun readFile(fileName: String): String = File(fileName).readText(Charsets.UTF_8)