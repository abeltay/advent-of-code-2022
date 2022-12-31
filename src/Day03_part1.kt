fun main() {
    fun part1(input: List<String>): Int {
        fun calcPriority(input: Char): Int {
            return if (input.code <= 'Z'.code) {
                input.code - 'A'.code + 27
            } else {
                input.code - 'a'.code + 1
            }
        }

        var priority = 0
        for (it in input) {
            val half = it.length / 2
            val hash = HashSet<Char>()
            var i = 0
            while (i < half) {
                hash.add(it[i])
                i++
            }
            while (i < it.length) {
                if (hash.contains(it[i])) {
                    priority += calcPriority(it[i])
                    break
                }
                i++
            }
        }
        return priority
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)

    val input = readInput("Day03")
    println(part1(input))
}