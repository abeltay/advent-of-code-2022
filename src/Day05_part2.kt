fun main() {
    fun part2(input: List<String>): String {
        fun convertInput(input: List<String>): MutableList<MutableList<Char>> {
            val containers = mutableListOf<MutableList<Char>>()
            for (i in 0 until input.last().length / 4 + 1) {
                containers.add(mutableListOf())
            }
            for (i in input.indices.reversed()) {
                if (i == input.size - 1) continue
                for (j in containers.indices) {
                    val container = 1 + j * 4
                    if (container >= input[i].length) break
                    val name = input[i][container]
                    if (name != ' ') {
                        containers[j].add(name)
                    }
                }
            }
            return containers
        }

        val emptyLine = input.indexOf("")
        val containers = convertInput(input.subList(0, emptyLine))
        val inputLineRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()

        for (it in input.subList(emptyLine + 1, input.size)) {
            val (s1, s2, s3) = inputLineRegex
                .matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            val number = s1.toInt()
            val from = s2.toInt() - 1
            val to = s3.toInt() - 1
            val fromContainer = containers[from].takeLast(number)
            containers[from] = containers[from].dropLast(number).toMutableList()
            containers[to].addAll(fromContainer)
        }
        var answer = ""
        for (it in containers) {
            answer += it.last()
        }
        return answer
    }

    val testInput = readInput("Day05_test")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part2(input))
}
