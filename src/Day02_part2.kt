fun main() {
    fun part2(input: List<String>): Int {
        val rock = 1
        val paper = 2
        val scissors = 3
        val lose = 0
        val draw = 3
        val win = 6
        fun convertLeft(input: Char): Int {
            return when (input) {
                'A' -> rock
                'B' -> paper
                else -> scissors
            }
        }

        fun convertRight(input: Char): Int {
            return when (input) {
                'X' -> lose
                'Y' -> draw
                else -> win
            }
        }

        fun normalise(result: Int): Int {
            if (result < 1) {
                return result + 3
            }
            if (result > 3) {
                return result - 3
            }
            return result
        }

        var total = 0
        for (it in input) {
            val left = convertLeft(it[0])
            val right = convertRight(it[2])
            total += when (right) {
                lose -> normalise(left - 1)
                win -> normalise(left + 1)
                else -> left
            }
            total += right
        }
        return total
    }

    val testInput = readInput("Day02_test")
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part2(input))
}
