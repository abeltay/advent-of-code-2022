fun main() {
    fun part1(input: List<String>): Int {
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
                'X' -> rock
                'Y' -> paper
                else -> scissors
            }
        }

        var total = 0
        input.forEach {
            val left = convertLeft(it[0])
            val right = convertRight(it[2])
            when {
                left == right -> total += draw
                left == 3 && right == 1 -> total += win
                left + 1 == right -> total += win
            }
            total += right
        }
        return total
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)

    val input = readInput("Day02")
    println(part1(input))
}