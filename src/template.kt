fun main() {
    fun part1(input: List<String>): Int {
        val inputLineRegex = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()

        for (it in input) {
            val (startX, startY, endX, endY) = inputLineRegex
                .matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
        }
        var answer = 0
        return answer
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 0)
    // check(part2(testInput) == 0)

    val input = readInput("Day04")
    println(part1(input))
    // println(part2(input))
}
