fun main() {
    fun parseInput(input: List<String>): MutableList<Pair<Int, Int>> {
        val elves = mutableListOf<Pair<Int, Int>>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '#') {
                    elves.add(Pair(i, j))
                }
            }
        }
        return elves
    }

    fun isVerticalSidesOccupied(elves: List<Pair<Int, Int>>, location: Pair<Int, Int>): Boolean {
        return elves.contains(Pair(location.first - 1, location.second)) ||
                elves.contains(Pair(location.first, location.second)) ||
                elves.contains(Pair(location.first + 1, location.second))
    }

    fun isHorizontalSidesOccupied(elves: List<Pair<Int, Int>>, location: Pair<Int, Int>): Boolean {
        return elves.contains(Pair(location.first, location.second - 1)) ||
                elves.contains(Pair(location.first, location.second)) ||
                elves.contains(Pair(location.first, location.second + 1))
    }

    val directions = listOf(
        fun(elves: List<Pair<Int, Int>>, elf: Pair<Int, Int>): Pair<Int, Int>? {
            val next = Pair(elf.first - 1, elf.second)
            return if (!isHorizontalSidesOccupied(elves, next)) {
                next
            } else {
                null
            }
        },
        fun(elves: List<Pair<Int, Int>>, elf: Pair<Int, Int>): Pair<Int, Int>? {
            val next = Pair(elf.first + 1, elf.second)
            return if (!isHorizontalSidesOccupied(elves, next)) {
                next
            } else {
                null
            }
        },
        fun(elves: List<Pair<Int, Int>>, elf: Pair<Int, Int>): Pair<Int, Int>? {
            val next = Pair(elf.first, elf.second - 1)
            return if (!isVerticalSidesOccupied(elves, next)) {
                next
            } else {
                null
            }
        },
        fun(elves: List<Pair<Int, Int>>, elf: Pair<Int, Int>): Pair<Int, Int>? {
            val next = Pair(elf.first, elf.second + 1)
            return if (!isVerticalSidesOccupied(elves, next)) {
                next
            } else {
                null
            }
        }
    )

    fun hasMoved(elves: MutableList<Pair<Int, Int>>, direction: Int): Boolean {
        var stationary = 0
        val proposed = mutableListOf<Pair<Int, Int>>()
        for (elf in elves) {
            var found = false
            for (i in elf.first - 1..elf.first + 1) {
                for (j in elf.second - 1..elf.second + 1) {
                    if (i == elf.first && j == elf.second) continue
                    if (elves.contains(Pair(i, j)))
                        found = true
                }
            }
            if (!found) {
                stationary++
                proposed.add(elf)
                continue
            }
            found = false
            for (i in 0..3) {
                val next = directions[(direction + i) % 4](elves, elf)
                if (next != null) {
                    proposed.add(next)
                    found = true
                    break
                }
            }
            if (!found) proposed.add(elf)
        }
        for (i in proposed.indices) {
            val next = if (proposed.count { it == proposed[i] } > 1) {
                elves.first()
            } else {
                proposed[i]
            }
            elves.removeFirst()
            elves.add(next)
        }
        if (stationary == proposed.size) {
            return true
        }
        return false
    }

    fun part1(input: List<String>): Int {
        val elves = parseInput(input)
        for (i in 0..9) {
            hasMoved(elves, i % 4)
        }
        val x = elves.maxOf { it.second } - elves.minOf { it.second } + 1
        val y = elves.maxOf { it.first } - elves.minOf { it.first } + 1
        return (x * y) - elves.size
    }

    fun part2(input: List<String>): Int {
        val elves = parseInput(input)
        var rounds = 0
        while(true) {
            if (hasMoved(elves, rounds % 4)){
                return rounds +1
            }
            rounds++
        }
    }

    val testInput = readInput("Day23_test")
    check(part1(testInput) == 110)
    check(part2(testInput) == 20)

    val input = readInput("Day23")
    println(part1(input))
    println(part2(input))
}
