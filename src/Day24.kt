fun main() {
    val right = 0
    val down = 1
    val left = 2
    val up = 3

    data class Blizzard(
        var x: Int,
        var y: Int,
        val direction: Int
    )

    fun parseInput(input: List<String>): MutableList<Blizzard> {
        val blizzard = mutableListOf<Blizzard>()
        for (y in input.indices) {
            if (y == 0 || y == input.size - 1) continue
            val line = input[y].toCharArray()
            for (x in line.indices) {
                if (x == 0 || x == line.size - 1) continue
                if (line[x] == '.') continue
                val direction = when (line[x]) {
                    '>' -> right
                    '<' -> left
                    'v' -> down
                    else -> up
                }
                blizzard.add(Blizzard(x = x - 1, y = y - 1, direction = direction))
            }
        }

        return blizzard
    }

    fun moveBlizzards(blizzards: List<Blizzard>, x: Int, y: Int) {
        for (it in blizzards) {
            when (it.direction) {

                right -> it.x = (it.x + 1) % x
                left -> {
                    it.x--
                    if (it.x < 0) {
                        it.x += x
                    }
                }

                down -> it.y = (it.y + 1) % y
                else -> {
                    it.y--
                    if (it.y < 0) {
                        it.y += y
                    }
                }
            }
        }
    }

    fun addIfValid(
        blizzards: HashSet<Pair<Int, Int>>,
        location: Pair<Int, Int>,
        x: Int,
        y: Int,
        new: HashSet<Pair<Int, Int>>
    ) {
        if (location.first < 0 || location.first >= x || location.second < 0 || location.second >= y) return
        if (!blizzards.contains(location)) {
            new.add(location)
        }
    }

    fun moveHuman(
        blizzards: HashSet<Pair<Int, Int>>,
        location: Pair<Int, Int>,
        x: Int,
        y: Int,
        newLocations: HashSet<Pair<Int, Int>>
    ) {
        addIfValid(blizzards, Pair(location.first, location.second), x, y, newLocations)
        addIfValid(blizzards, Pair(location.first + 1, location.second), x, y, newLocations)
        addIfValid(blizzards, Pair(location.first - 1, location.second), x, y, newLocations)
        addIfValid(blizzards, Pair(location.first, location.second + 1), x, y, newLocations)
        addIfValid(blizzards, Pair(location.first, location.second - 1), x, y, newLocations)
    }

    fun explore(blizzards: MutableList<Blizzard>, x: Int, y: Int, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        moveBlizzards(blizzards, x, y)
        var possibleLocations = hashSetOf(start)
        var answer = 1
        while (true) {
            answer++
            moveBlizzards(blizzards, x, y)
            val blizzardLocations = blizzards.map { Pair(it.x, it.y) }.toHashSet()
            val newLocations = HashSet<Pair<Int, Int>>()
            for (it in possibleLocations) {
                if (it == end) {
                    return answer
                }
                moveHuman(blizzardLocations, it, x, y, newLocations)
            }
            addIfValid(blizzardLocations, start, x, y, newLocations)
            possibleLocations = newLocations
        }
    }

    fun part1(input: List<String>): Int {
        val blizzards = parseInput(input)
        val x = input[0].length - 2
        val y = input.size - 2
        return explore(blizzards, x, y, Pair(0, 0), Pair(x - 1, y - 1))
    }

    fun part2(input: List<String>): Int {
        val blizzards = parseInput(input)
        val x = input[0].length - 2
        val y = input.size - 2
        val one = explore(blizzards, x, y, Pair(0, 0), Pair(x - 1, y - 1))
        val two = explore(blizzards, x, y, Pair(x - 1, y - 1), Pair(0, 0))
        val three = explore(blizzards, x, y, Pair(0, 0), Pair(x - 1, y - 1))
        return one + two + three
    }

    val testInput = readInput("Day24_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 54)

    val input = readInput("Day24")
    println(part1(input))
    println(part2(input))
}
