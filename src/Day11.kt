fun main() {
    val multiply = 0
    val add = 1
    data class Data(
        val items: ArrayDeque<Long>,
        val operation: Int,
        val operand: Long,
        val divisor: Long,
        val passTrue: Int,
        val passFalse: Int
    )

    fun parseInput(input: List<String>): List<Data> {
        var i = 0
        val items = mutableListOf<Data>()
        while (i < input.size) {
            i++
            val line2 = input[i].substringAfter(": ")
            i++
            val line3 = input[i].substringAfter(" = old ")
            val operand = line3.substringAfter(' ')
            i++
            val divisor = input[i].substringAfter("Test: divisible by ").toLong()
            i++
            val passTrue = input[i].substringAfter("If true: throw to monkey ").toInt()
            i++
            val passFalse = input[i].substringAfter("If false: throw to monkey ").toInt()
            i += 2
            items.add(
                Data(
                    items = ArrayDeque(line2.split(", ").map { it.toLong() }),
                    operation = if (line3[0] == '*') multiply else add,
                    operand = if (operand != "old") operand.toLong() else 0,
                    divisor = divisor,
                    passTrue = passTrue,
                    passFalse = passFalse,
                )
            )
        }
        return items
    }

    fun part1(input: List<String>): Int {
        val data = parseInput(input)
        val inspectionCount = MutableList(data.size) { 0 }
        for (round in 0 until 20) {
            for ((index, value) in data.withIndex()) {
                while (value.items.isNotEmpty()) {
                    val first = value.items.removeFirst()
                    val calculated = if (value.operation == multiply) {
                        var operand = value.operand
                        if (operand == 0.toLong()) {
                            operand = first
                        }
                        first * operand
                    } else {
                        first + value.operand
                    } / 3
                    inspectionCount[index]++
                    val pass = if (calculated % value.divisor == 0.toLong()) {
                        value.passTrue
                    } else {
                        value.passFalse
                    }
                    data[pass].items.add(calculated)
                }
            }
        }
        var answer = inspectionCount.max()
        inspectionCount.remove(answer)
        answer *= inspectionCount.max()
        return answer
    }

    fun part2(input: List<String>): Long {
        val data = parseInput(input)
        val inspectionCount = MutableList<Long>(data.size) { 0 }
        var lcm = 1.toLong()
        for (it in data) {
            lcm *= it.divisor
        }
        for (round in 0 until 10000) {
            for ((index, value) in data.withIndex()) {
                while (value.items.isNotEmpty()) {
                    val first = value.items.removeFirst()
                    val calculated = if (value.operation == multiply) {
                        var operand = value.operand
                        if (operand == 0.toLong()) {
                            operand = first
                        }
                        first * operand
                    } else {
                        first + value.operand
                    } % lcm
                    inspectionCount[index]++
                    val pass = if (calculated % value.divisor == 0.toLong()) {
                        value.passTrue
                    } else {
                        value.passFalse
                    }
                    data[pass].items.add(calculated)
                }
            }
        }
        var answer = inspectionCount.max()
        inspectionCount.remove(answer)
        answer *= inspectionCount.max()
        return answer
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
