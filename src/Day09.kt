fun main() {
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

    fun part1(input: List<String>): Int {
        var head = Pair(0, 0)
        var tail = Pair(0, 0)
        val set = mutableSetOf(tail)
        for (it in input) {
            val direction = it.substringBefore(' ')
            val steps = it.substringAfter(' ').toInt()
            for (count in 1..steps) {
                head = when (direction) {
                    "R" -> Pair(head.first + 1, head.second)
                    "L" -> Pair(head.first - 1, head.second)
                    "U" -> Pair(head.first, head.second + 1)
                    else -> Pair(head.first, head.second - 1)
                }
                while (!isEnd(head, tail)) {
                    tail = step(head, tail)
                    set.add(tail)
                }
            }
        }
        return set.size
    }

    fun part2(input: List<String>): Int {
        val rope = MutableList(10) { Pair(0, 0) }
        val set = hashSetOf(Pair(0, 0))
        for (it in input) {
            val direction = it.substringBefore(' ')
            val steps = it.substringAfter(' ').toInt()
            for (count in 1..steps) {
                rope[0] = when (direction) {
                    "R" -> Pair(rope[0].first + 1, rope[0].second)
                    "L" -> Pair(rope[0].first - 1, rope[0].second)
                    "U" -> Pair(rope[0].first, rope[0].second + 1)
                    else -> Pair(rope[0].first, rope[0].second - 1)
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
    check(part1(testInput) == 13)
    check(part2(testInput) == 1)
    val testInput2 = readInput("Day09_test2")
    check(part2(testInput2) == 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
