import java.util.*

fun main() {
    fun part2(input: List<String>): Int {
        val heap = PriorityQueue(listOf(0, 0, 0))
        var count = 0
        input.forEach {
            if (it.isNotBlank()) {
                count += it.toInt()
            } else {
                val element = heap.peek()
                if (count > element) {
                    heap.remove(element)
                    heap.add(count)
                }
                count = 0
            }
        }
        if (count > heap.peek()) {
            heap.remove(heap.peek())
            heap.add(count)
        }
        var total = 0
        heap.forEach {
            total += it
        }
        return total
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part2(input))
}
