fun main() {
    fun part1(input: List<String>): Int {
        val inputLineRegex = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()

        var answer = 1
        for (it in input) {
            val (startX, startY, endX, endY) = inputLineRegex
                .matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        var answer = 1
        return answer
    }

    val testInput = readInput("Day{replace}_test")
    check(part1(testInput) == 1)
    // check(part2(testInput) == 1)

    val input = readInput("Day{replace}")
    println(part1(input))
    // println(part2(input))
}
