import kotlin.math.abs

fun main() {
    fun manhattanDistance(a: Pair<Int, Int>, b: Pair<Int, Int>): Int {
        return abs(a.first - b.first) + abs(a.second - b.second)
    }

    fun isTouching(a: Pair<Int, Int>, b: Pair<Int, Int>): Boolean {
        if (a.first > b.first) {
            return isTouching(b, a)
        }
        return a.second + 1 >= b.first
    }

    fun addLine(existing: MutableList<Pair<Int, Int>>, line: Pair<Int, Int>) {
        for (it in existing) {
            if (isTouching(it, line)) {
                existing.remove(it)
                val from = if (it.first < line.first) it.first else line.first
                val to = if (it.second > line.second) it.second else line.second
                addLine(existing, Pair(from, to))
                return
            }
        }
        existing.add(line)
    }

    val inputLineRegex = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()

    fun part1(input: List<String>, row: Int): Int {
        val line = mutableListOf<Pair<Int, Int>>()
        val beacons = hashSetOf<Int>()
        for (it in input) {
            val (startX, startY, endX, endY) = inputLineRegex
                .matchEntire(it)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            val sensor = Pair(startX.toInt(), startY.toInt())
            val beacon = Pair(endX.toInt(), endY.toInt())
            val dist = manhattanDistance(sensor, beacon)
            // Check intersect
            if (sensor.second + dist < row || sensor.second - dist > row) {
                continue
            }
            val firstDist = dist - abs(sensor.second - row)
            val from = sensor.first - firstDist
            val to = sensor.first + firstDist
            if (beacon.second == row) {
                beacons.add(beacon.first)
            }
            addLine(line, Pair(from, to))
        }
        var answer = 0
        for (it in line) {
            if (it.first < 0 && it.second >= 0) {
                answer++
            }
            answer += it.second - it.first
            answer -= beacons.count { beacon -> (beacon >= it.first && beacon <= it.second) }
        }
        return answer
    }

    fun part2(input: List<String>, limit: Int): Long {
        for (row in 0 until limit) {
            val line = mutableListOf<Pair<Int, Int>>()
            for (it in input) {
                val (startX, startY, endX, endY) = inputLineRegex
                    .matchEntire(it)
                    ?.destructured
                    ?: throw IllegalArgumentException("Incorrect input line $it")
                val sensor = Pair(startX.toInt(), startY.toInt())
                val beacon = Pair(endX.toInt(), endY.toInt())
                val dist = manhattanDistance(sensor, beacon)
                // Check intersect
                if (sensor.second + dist < row || sensor.second - dist > row) {
                    continue
                }
                val firstDist = dist - abs(sensor.second - row)
                val from = sensor.first - firstDist
                val to = sensor.first + firstDist
                addLine(line, Pair(from, to))
            }
            for (it in line) {
                val x = it.second + 1
                if (x in 0 until limit) {
                    return x.toLong() * 4000000 + row.toLong()
                }
            }
        }
        return 0
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput, 10) == 26)
    check(part2(testInput, 20) == 56000011.toLong())

    val input = readInput("Day15")
    println(part1(input, 2000000))
    println(part2(input, 4000000))
}
