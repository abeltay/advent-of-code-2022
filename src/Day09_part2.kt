fun main() {
    fun part2(input: List<String>): Int {
        fun isEnd(head: Pair<Int, Int>, tail: Pair<Int, Int>): Boolean {
            return head.first - 1 <= tail.first && head.first + 1 >= tail.first && head.second - 1 <= tail.second && head.second + 1 >= tail.second
        }

        fun step(head: Pair<Int, Int>, tail: Pair<Int, Int>): Pair<Int, Int> {
            val newX = if (head.first > tail.first) {
                tail.first + 1
            } else if (head.first < tail.first) {
                tail.first - 1
            } else {
                tail.first
            }
            val newY = if (head.second > tail.second) {
                tail.second + 1
            } else if (head.second < tail.second) {
                tail.second - 1
            } else {
                tail.second
            }
            return Pair(newX, newY)
        }

        val rope = MutableList(10) { Pair(0, 0) }
        val set = hashSetOf(Pair(0, 0))
        for (it in input) {
            val direction = it.substringBefore(' ')
            val steps = it.substringAfter(' ').toInt()
            for (count in 1..steps){
                when (direction) {
                    "R" -> rope[0] = Pair(rope[0].first + 1, rope[0].second)
                    "L" -> rope[0] = Pair(rope[0].first - 1, rope[0].second)
                    "U" -> rope[0] = Pair(rope[0].first, rope[0].second + 1)
                    else -> rope[0] = Pair(rope[0].first, rope[0].second - 1)
                }
                for (i in 1 until rope.size) {
                    while (!isEnd(rope[i - 1], rope[i])) {
                        rope[i] = step(rope[i - 1], rope[i])
                        if (i == rope.size - 1) {
                            set.add(rope[i])
                        }
                    }
                }
            }
        }
        return set.size
    }

    val testInput = readInput("Day09_test")
    check(part2(testInput) == 1)
    val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    println(part2(input))
}
