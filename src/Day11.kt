fun main() {
    data class Data(
        val items: List<List<Long>>,
        val operations: List<(Long) -> Long>,
        val passTo: List<(Long) -> Int>,
        val divisor: Long
    )

    fun part1(input: Data): Int {
        val inspectionCount = MutableList(input.items.size) { 0 }
        val items = mutableListOf<MutableList<Long>>()
        for (it in input.items) {
            items.add(it.toMutableList())
        }
        for (round in 0 until 20) {
            for (i in items.indices) {
                while (items[i].isNotEmpty()) {
                    val first = items[i].removeFirst()
                    val operated = input.operations[i](first) / 3
                    inspectionCount[i]++
                    val pass = input.passTo[i](operated)
                    items[pass].add(operated)
                }
            }
        }
        var answer = inspectionCount.max()
        inspectionCount.remove(answer)
        answer *= inspectionCount.max()
        return answer
    }

    fun part2(input: Data): Long {
        val inspectionCount = MutableList<Long>(input.items.size) { 0 }
        val items = mutableListOf<MutableList<Long>>()
        for (it in input.items) {
            items.add(it.toMutableList())
        }
        for (round in 0 until 10000) {
            for (i in input.items.indices) {
                while (items[i].isNotEmpty()) {
                    val first = items[i].removeFirst()
                    val operated = input.operations[i](first) % input.divisor
                    inspectionCount[i]++
                    val pass = input.passTo[i](operated)
                    items[pass].add(operated)
                }
            }
        }
        var answer = inspectionCount.max()
        inspectionCount.remove(answer)
        answer *= inspectionCount.max()
        return answer
    }

    val testData = Data(
        items = listOf(
            listOf(79, 98),
            listOf(54, 65, 75, 74),
            listOf(79, 60, 97),
            listOf(74)
        ),
        operations = listOf(
            { old -> old * 19 },
            { old -> old + 6 },
            { old -> old * old },
            { old -> old + 3 }
        ),
        passTo = listOf(
            { num -> if (num % 23 == 0.toLong()) 2 else 3 },
            { num -> if (num % 19 == 0.toLong()) 2 else 0 },
            { num -> if (num % 13 == 0.toLong()) 1 else 3 },
            { num -> if (num % 17 == 0.toLong()) 0 else 1 }
        ),
        divisor = 23 * 19 * 13 * 17
    )
    check(part1(testData) == 10605)
    check(part2(testData) == 2713310158)

    val actualData = Data(
        items = listOf(
            listOf(54, 61, 97, 63, 74),
            listOf(61, 70, 97, 64, 99, 83, 52, 87),
            listOf(60, 67, 80, 65),
            listOf(61, 70, 76, 69, 82, 56),
            listOf(79, 98),
            listOf(72, 79, 55),
            listOf(63),
            listOf(72, 51, 93, 63, 80, 86, 81)
        ),
        operations = listOf(
            { old -> old * 7 },
            { old -> old + 8 },
            { old -> old * 13 },
            { old -> old + 7 },
            { old -> old + 2 },
            { old -> old + 1 },
            { old -> old + 4 },
            { old -> old * old }
        ),
        passTo = listOf(
            { num -> if (num % 17 == 0.toLong()) 5 else 3 },
            { num -> if (num % 2 == 0.toLong()) 7 else 6 },
            { num -> if (num % 5 == 0.toLong()) 1 else 6 },
            { num -> if (num % 3 == 0.toLong()) 5 else 2 },
            { num -> if (num % 7 == 0.toLong()) 0 else 3 },
            { num -> if (num % 13 == 0.toLong()) 2 else 1 },
            { num -> if (num % 19 == 0.toLong()) 7 else 4 },
            { num -> if (num % 11 == 0.toLong()) 0 else 4 }
        ),
        divisor = 17 * 2 * 5 * 3 * 7 * 13 * 19 * 11
    )
    println(part1(actualData))
    println(part2(actualData))
}
