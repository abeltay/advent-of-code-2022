fun main() {
    fun part1(input: List<String>): Int {
        val checkpoints = listOf(20, 60, 100, 140, 180, 220)
        var valueX = 1
        var answer = 0
        var cycle = 0
        var lastCheckpoint = 0
        fun incrementCycle() {
            cycle++
            if (lastCheckpoint >= checkpoints.size)
                return
            if (cycle == checkpoints[lastCheckpoint]) {
                val signalStrength = cycle * valueX
                answer += signalStrength
                lastCheckpoint++
            }
        }

        for (it in input) {
            if (it == "noop") {
                incrementCycle()
                continue
            }
            incrementCycle()
            incrementCycle()
            val num = it.substringAfter(' ').toInt()
            valueX += num
        }
        return answer
    }

    fun part2(input: List<String>) {
        val screen = List(6) { MutableList(40) { '.' } }
        var valueX = 1
        var cycle = 0
        fun drawPixel() {
            val row = cycle / 40
            val column = cycle % 40
            if (valueX - 1 <= column && valueX + 1 >= column) {
                screen[row][column] = '#'
            }
            cycle++
        }
        for (it in input) {
            if (it == "noop") {
                drawPixel()
                continue
            }
            drawPixel()
            drawPixel()
            val num = it.substringAfter(' ').toInt()
            valueX += num
        }
        for (row in screen) {
            for (pixel in row) {
                print(pixel)
            }
            println()
        }
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    part2(testInput)

    println("--------------------------------------------------")
    val input = readInput("Day10")
    println(part1(input))
    part2(input)
}
