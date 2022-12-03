fun main() {
    fun part1(input: List<String>): Int {
        val split = splitListByEmptyLine(input)
        return split.maxOf { calorie ->
            calorie.sumOf { it.toInt() }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
}
