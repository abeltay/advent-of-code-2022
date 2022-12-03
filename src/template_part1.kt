fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("Day{replace}_test")
    check(part1(testInput) == 1)

    val input = readInput("Day{replace}")
    println(part1(input))
}
