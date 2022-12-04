fun main() {
    fun part1(input: List<String>): Int {
        val inputLineRegex = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()

        var answer = 0
        for (it in input) {
            val (startX, startY, endX, endY) = inputLineRegex
                .matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
        }
        return answer
    }

    val testInput = readInput("Day{replace}_test")
    check(part1(testInput) == 1)

    val input = readInput("Day{replace}")
    println(part1(input))
}
