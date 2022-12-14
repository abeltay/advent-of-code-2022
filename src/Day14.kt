fun main() {
    fun addVerticalLine(lines: HashMap<Int, MutableList<Pair<Int, Int>>>, column: Int, from: Int, to: Int) {
        if (from > to) {
            addVerticalLine(lines, column, to, from)
            return
        }
        val existing = lines[column] ?: mutableListOf()
        existing.add(Pair(from, to))
        existing.sortBy { it.first }
        lines[column] = existing
    }

    fun addHorizontalLine(lines: HashMap<Int, MutableList<Pair<Int, Int>>>, row: Int, from: Int, to: Int) {
        if (from > to) {
            addHorizontalLine(lines, row, to, from)
            return
        }
        for (i in from..to) {
            addVerticalLine(lines, i, row, row)
        }
    }

    fun addLines(input: String, lines: HashMap<Int, MutableList<Pair<Int, Int>>>) {
        val coordinates = input.split(" -> ")
        var last: Pair<Int, Int>? = null
        for (it in coordinates) {
            val coordinate = it.split(",")
            val location = Pair(coordinate[0].toInt(), coordinate[1].toInt())
            if (last != null) {
                if (location.first == last.first) {
                    addVerticalLine(lines, location.first, last.second, location.second)
                } else {
                    addHorizontalLine(lines, location.second, last.first, location.first)
                }
            }
            last = location
        }
    }

    fun isBlocked(lines: List<Pair<Int, Int>>, position: Int): Boolean {
        return lines.filter { it.first <= position }.any { it.second >= position }
    }

    fun isSandCollected(lines: HashMap<Int, MutableList<Pair<Int, Int>>>, from: Pair<Int, Int>): Boolean {
        val line = lines[from.first] ?: return false
        val stop = line.find { it.first > from.second } ?: return false

        val leftDiagonal = Pair(from.first - 1, stop.first)
        val leftLine = lines[leftDiagonal.first] ?: return false
        if (!isBlocked(leftLine, leftDiagonal.second)) {
            return isSandCollected(lines, leftDiagonal)
        }
        val rightDiagonal = Pair(from.first + 1, stop.first)
        val rightLine = lines[rightDiagonal.first] ?: return false
        if (!isBlocked(rightLine, rightDiagonal.second)) {
            return isSandCollected(lines, rightDiagonal)
        }
        line[line.indexOf(stop)] = Pair(stop.first - 1, stop.second)
        return true
    }

    fun part1(input: List<String>): Int {
        val lines = hashMapOf<Int, MutableList<Pair<Int, Int>>>()
        for (it in input) {
            addLines(it, lines)
        }
        var answer = 0
        while (isSandCollected(lines, Pair(500, 0))) {
            answer++
        }
        return answer
    }

    fun addInfiniteFloor(
        lines: HashMap<Int, MutableList<Pair<Int, Int>>>,
        max: Int,
        column: Int
    ): MutableList<Pair<Int, Int>> {
        lines[column] = mutableListOf(Pair(max, max))
        return lines[column]!!
    }

    fun isSourceBlocked(lines: HashMap<Int, MutableList<Pair<Int, Int>>>, max: Int, from: Pair<Int, Int>): Boolean {
        var line = lines[from.first]
        if (line == null) {
            line = addInfiniteFloor(lines, max, from.first)
        }
        if (isBlocked(line, from.second)) {
            return true
        }
        var stop = line.find { it.first >= from.second }
        if (stop == null) {
            stop = Pair(max, max)
            line.add(stop)
        }

        val leftDiagonal = Pair(from.first - 1, stop.first)
        if (!isSourceBlocked(lines, max, leftDiagonal)) {
            return false
        }

        val rightDiagonal = Pair(from.first + 1, stop.first)
        if (!isSourceBlocked(lines, max, rightDiagonal)) {
            return false
        }
        line[line.indexOf(stop)] = Pair(stop.first - 1, stop.second)
        return false
    }

    fun part2(input: List<String>): Int {
        val lines = hashMapOf<Int, MutableList<Pair<Int, Int>>>()
        for (it in input) {
            addLines(it, lines)
        }
        var max = 0
        for (line in lines) {
            val cur = line.value.maxOf { it.second }
            if (cur > max) max = cur
        }
        max += 2
        var answer = 0
        while (!isSourceBlocked(lines, max, Pair(500, 0))) {
            answer++
        }
        return answer
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput) == 24)
    check(part2(testInput) == 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
