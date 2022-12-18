fun main() {
    fun parseInput(input: String): List<Boolean> {
        val rightWind = mutableListOf<Boolean>()
        for (it in input.toCharArray()) {
            if (it == '<') {
                rightWind.add(false)
            } else {
                rightWind.add(true)
            }
        }
        return rightWind
    }

    fun createShapes(): List<List<List<Int>>> {
        val minus = listOf(
            listOf(0, 0, 1, 1, 1, 1, 0)
        )
        val plus = listOf(
            listOf(0, 0, 0, 1, 0, 0, 0),
            listOf(0, 0, 1, 1, 1, 0, 0),
            listOf(0, 0, 0, 1, 0, 0, 0)
        )
        val letterL = listOf(
            listOf(0, 0, 1, 1, 1, 0, 0),
            listOf(0, 0, 0, 0, 1, 0, 0),
            listOf(0, 0, 0, 0, 1, 0, 0)
        )
        val l = listOf(
            listOf(0, 0, 1, 0, 0, 0, 0),
            listOf(0, 0, 1, 0, 0, 0, 0),
            listOf(0, 0, 1, 0, 0, 0, 0),
            listOf(0, 0, 1, 0, 0, 0, 0)
        )
        val window = listOf(
            listOf(0, 0, 1, 1, 0, 0, 0),
            listOf(0, 0, 1, 1, 0, 0, 0)
        )
        return listOf(minus, plus, letterL, l, window)
    }

    fun hasCollision(chamber: MutableList<MutableList<Int>>, itemBase: Int, shape: List<List<Int>>): Boolean {
        for (i in shape.indices) {
            if (itemBase + i >= chamber.size) {
                break
            }
            for (j in shape[i].indices) {
                if (chamber[itemBase + i][j] == 1 && shape[i][j] == 1) {
                    return true
                }
            }
        }
        return false
    }

    fun moveShape(
        chamber: MutableList<MutableList<Int>>,
        itemBase: Int,
        shape: List<List<Int>>,
        right: Boolean
    ): List<List<Int>> {
        if (right) {
            for (i in shape.indices) {
                if (shape[i][shape[i].size - 1] == 1) {
                    return shape
                }
            }
            val shifted = mutableListOf<List<Int>>()
            for (i in shape.indices) {
                val newLine = shape[i].toMutableList()
                newLine.removeLast()
                newLine.add(0, 0)
                shifted.add(newLine)
            }
            if (hasCollision(chamber, itemBase, shifted)) {
                return shape
            }
            return shifted
        } else {
            for (i in shape.indices) {
                if (shape[i][0] == 1) {
                    return shape
                }
            }
            val shifted = mutableListOf<List<Int>>()
            for (i in shape.indices) {
                val newLine = shape[i].toMutableList()
                newLine.removeFirst()
                newLine.add(0)
                shifted.add(newLine)
            }
            if (hasCollision(chamber, itemBase, shifted)) {
                return shape
            }
            return shifted
        }
    }

    fun addShape(chamber: MutableList<MutableList<Int>>, shape: List<List<Int>>, itemBase: Int) {
        for (i in shape.indices) {
            if (chamber.size <= i + itemBase) {
                chamber.add(shape[i].toMutableList())
                continue
            }
            for (j in shape[i].indices) {
                if (shape[i][j] == 1) {
                    chamber[i + itemBase][j] = shape[i][j]
                }
            }
        }
    }

    fun rockLanding(chamber: MutableList<MutableList<Int>>, shape: List<List<Int>>, itemBase: Int): Boolean {
        if (itemBase >= chamber.size) {
            return false
        }
        if (itemBase < 0) {
            addShape(chamber, shape, 0)
            return true
        }
        if (hasCollision(chamber, itemBase, shape)) {
            addShape(chamber, shape, itemBase + 1)
            return true
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val rightWind = parseInput(input.first())
        var windPointer = 0
        val chamber = mutableListOf<MutableList<Int>>()
        val shapes = createShapes()
        var shapePointer = 0
        for (i in 1..2022) {
            var itemBase = chamber.size + 3
            var shape = shapes[shapePointer]
            shapePointer = (shapePointer + 1) % shapes.size
            do {
                // Wind push
                shape = moveShape(chamber, itemBase, shape, rightWind[windPointer])
                windPointer = (windPointer + 1) % rightWind.size
                // Fall once
                itemBase--
            } while (!rockLanding(chamber, shape, itemBase))
        }
        return chamber.size
    }

    fun part2(input: List<String>): Long {
        data class CycleStruct(
            val rockCount: Int,
            val height: Int,
            val shapePointer: Int,
            val windPointer: Int
        )

        val rightWind = parseInput(input.first())
        var windPointer = 0
        val chamber = mutableListOf<MutableList<Int>>()
        val shapes = createShapes()
        var shapePointer = 0
        val cycles = mutableListOf<CycleStruct>()
        val heights = mutableListOf<Int>()
        for (i in 1..7000) {
            var itemBase = chamber.size + 3
            var shape = shapes[shapePointer]
            shapePointer = (shapePointer + 1) % shapes.size
            do {
                // Wind push
                shape = moveShape(chamber, itemBase, shape, rightWind[windPointer])
                windPointer = (windPointer + 1) % rightWind.size
                // Fall once
                itemBase--
            } while (!rockLanding(chamber, shape, itemBase))
            heights.add(chamber.size)
            // if (shapePointer == 0) {
            if (shapePointer == 2) {
                cycles.add(
                    CycleStruct(
                        rockCount = i,
                        height = chamber.size,
                        shapePointer = shapePointer,
                        windPointer = windPointer
                    )
                )
            }
        }
        val totalCycles = 1000000000000
        for (it in cycles) {
            val repeated = cycles.filter { cycle -> cycle.windPointer == it.windPointer }
            if (repeated.size > 1) {
                val cycleLength = repeated[2].rockCount - repeated[1].rockCount
                val cycleRepeats = (totalCycles - repeated[0].rockCount) / cycleLength
                val leftover = (totalCycles - repeated[0].rockCount) - cycleLength * cycleRepeats
                val leftoverHeight =
                    heights[leftover.toInt() + repeated[1].rockCount - 1] - heights[repeated[1].rockCount - 1]
                // println("$leftoverHeight height $leftover leftovers, started with ${repeated[0].rockCount}")
                return (repeated[2].height - repeated[1].height) * cycleRepeats + repeated[0].height + leftoverHeight// height per cycle * cycle + initial + leftover
            }
        }
        return chamber.size.toLong()
    }

    val testInput = readInput("Day17_test")
    check(part1(testInput) == 3068)
    check(part2(testInput) == 1514285714288)

    val input = readInput("Day17")
    println(part1(input))
    println(part2(input))
}
