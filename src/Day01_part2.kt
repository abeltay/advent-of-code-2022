import java.util.*

fun main() {
    fun part2(input: List<String>): Int {
        val split = splitListByEmptyLine(input)
        val heap = PriorityQueue(listOf(0, 0, 0))
        for (s in split) {
            val count = s.sumOf { it.toInt() }
            val element = heap.peek()
            if (count > element) {
                heap.remove(element)
                heap.add(count)
            }
        }
        return heap.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part2(input))
}
