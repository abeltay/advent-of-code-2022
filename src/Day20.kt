fun main() {
    fun normalise(size: Int, num: Long): Int {
        var number = num
        if (number >= size) {
            number %= size
        }
        while (number < 0) {
            number += size
        }
        return number.toInt()
    }

    fun calculateValues(numbers: List<Pair<Int, Long>>, zeroIdx: Int, position: Int): Long {
        return numbers[(zeroIdx + position) % numbers.size].second
    }

    fun decrypt(numbers: MutableList<Pair<Int, Long>>): Long {
        for (i in numbers.indices) {
            val idx = numbers.indexOfFirst { it.first == i }
            val num = numbers.removeAt(idx)
            val putAt = normalise(numbers.size, idx + num.second)
            if (putAt == 0) {
                numbers.add(num)
            } else {
                numbers.add(putAt, num)
            }
        }
        val zeroIdx = numbers.indexOfFirst { it.second == 0L }
        val a = calculateValues(numbers, zeroIdx, 1000)
        val b = calculateValues(numbers, zeroIdx, 2000)
        val c = calculateValues(numbers, zeroIdx, 3000)
        return a + b + c
    }

    fun calculateValues2(
        original: List<Pair<Int, Long>>,
        mix: Long,
        numbers: List<Pair<Int, Long>>,
        zeroIdx: Int,
        position: Int
    ): Long {
        return original[numbers[(zeroIdx + position) % numbers.size].first].second
    }

    fun decrypt2(original: List<Pair<Int, Long>>, mix: Long): Long {
        val numbers = original.map { it.first to it.second * mix % (original.size - 1) }.toMutableList()
        repeat(10) {
            for (i in numbers.indices) {
                val idx = numbers.indexOfFirst { it.first == i }
                val num = numbers.removeAt(idx)
                val putAt = normalise(numbers.size, idx + num.second)
                if (putAt == 0) {
                    numbers.add(num)
                } else {
                    numbers.add(putAt, num)
                }
            }
        }
        val zeroIdx = numbers.indexOfFirst { it.second == 0L }
        val a = calculateValues2(original, mix, numbers, zeroIdx, 1000)
        val b = calculateValues2(original, mix, numbers, zeroIdx, 2000)
        val c = calculateValues2(original, mix, numbers, zeroIdx, 3000)
        return (a + b + c) * mix
    }

    fun part1(input: List<String>): Long {
        val numbers = input.mapIndexed { index, s -> index to s.toLong() }
        return decrypt(numbers.toMutableList())
    }

    fun part2(input: List<String>): Long {
        val numbers = input.mapIndexed { index, s -> index to s.toLong() }
        return decrypt2(numbers.toMutableList(), 811589153)
    }

    val testInput = readInput("Day20_test")
    check(part1(testInput) == 3L)
    check(part2(testInput) == 1623178306L)

    val input = readInput("Day20")
    println(part1(input))
    println(part2(input))
}
