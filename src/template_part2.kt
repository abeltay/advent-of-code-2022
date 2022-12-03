fun main() {
    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day{replace}_test")
    check(part2(testInput) == 1)

    val input = readInput("Day{replace}")
    println(part2(input))
}
