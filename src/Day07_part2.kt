fun main() {
    fun part2(input: List<String>): Int {
        data class Directory(
            val name: String,
            var size: Int = 0,
            val subDirectories: MutableList<Directory> = mutableListOf()
        )

        fun changeDirectory(directory: Directory, name: String): Directory {
            return directory.subDirectories.find { it.name == name } ?: Directory(name = name)
        }

        fun updateSize(directory: Directory): Int {
            if (directory.subDirectories.isEmpty()) {
                return directory.size
            }
            for (d in directory.subDirectories) {
                directory.size += updateSize(d)
            }
            return directory.size
        }

        fun directorySizes(directory: Directory): List<Int> {
            val sizes = mutableListOf(directory.size)
            for (d in directory.subDirectories) {
                sizes.addAll(directorySizes(d))
            }
            return sizes
        }

        val history = mutableListOf(Directory(name = "/"))

        for (i in input.indices) {
            val split = input[i].split(' ')
            if (split[0] == "$") {
                if (split[1] == "cd") {
                    when (split[2]) {
                        ".." -> history.removeLast()
                        "/" -> while (history.size > 1) history.removeLast()
                        else -> history.add(changeDirectory(history.last(), split[2]))
                    }
                }
            } else {
                val split = input[i].split(' ')
                if (split[0] == "dir") {
                    history.last().subDirectories.add(Directory(name = split[1]))
                } else {
                    history.last().size += split[0].toInt()
                }
            }
        }
        updateSize(history.first())
        val directorySizes = directorySizes(history.first()).sorted()
        val total = 70000000
        val free = 30000000
        val current = total - history.first().size
        val toFree = free - current
        return directorySizes.find { it > toFree }!!
    }

    val testInput = readInput("Day07_test")
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part2(input))
}
