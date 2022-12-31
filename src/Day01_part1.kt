fun main() {
    fun part1(input: List<String>): Int {
        var max = 0
        var count = 0
        input.forEach {
            if (it.isNotBlank()) {
                count += it.toInt()
            } else {
                if (count > max) {
                    max = count
                }
                count = 0
            }
        }
        if (count > max) {
            max = count
        }
        return max
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
}
