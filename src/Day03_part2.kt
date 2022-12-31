fun main() {
    fun part2(input: List<String>): Int {
        fun calcPriority(input: Char): Int {
            return if (input.code <= 'Z'.code) {
                input.code - 'A'.code + 27
            } else {
                input.code - 'a'.code + 1
            }
        }

        var priority = 0
        var i = 0
        while (i < input.size) {
            val hash = input[i].toHashSet()
            val hash2 = input[i + 1].toHashSet().filter { hash.contains(it) }
            val character = input[i + 2].find { hash2.contains(it) }
            priority += calcPriority(character!!)
            i += 3
        }
        return priority
    }

    val testInput = readInput("Day03_test")
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part2(input))
}
