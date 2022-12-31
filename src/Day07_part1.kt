fun main() {
    fun part1(input: List<String>): Int {
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

        val limit = 100000
        fun totalLimit(directory: Directory): Int {
            var total = 0
            for (d in directory.subDirectories) {
                total += totalLimit(d)
            }
            if (directory.size < limit) {
                total += directory.size
            }
            return total
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
        return totalLimit(history.first())
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)

    val input = readInput("Day07")
    println(part1(input))
}
