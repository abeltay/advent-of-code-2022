fun main() {
    fun part2(input: List<String>): Int {
        fun hasOverlap(elf1: Pair<Int, Int>, elf2: Pair<Int, Int>): Boolean {
            if (elf2.first < elf1.first) {
                return hasOverlap(elf2, elf1)
            }
            if (elf2.first <= elf1.second) {
                return true
            }
            return false
        }

        val inputLineRegex = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
        var answer = 0
        for (it in input) {
            val (pair11, pair12, pair21, pair22) = inputLineRegex
                .matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            val elf1 = Pair(pair11.toInt(), pair12.toInt())
            val elf2 = Pair(pair21.toInt(), pair22.toInt())
            if (hasOverlap(elf1, elf2)) {
                answer++
            }
        }
        return answer
    }

    val testInput = readInput("Day04_test")
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part2(input))
}
