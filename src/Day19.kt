fun main() {
    val ore = 0
    val clay = 1
    val obsidian = 2
    val geode = 3

    var best = 0

    fun parseInput(input: String): List<List<Int>> {
        val robots = input.substringAfter(": ")
        val inputLineRegex =
            """Each ore robot costs (\d+) ore. Each clay robot costs (\d+) ore. Each obsidian robot costs (\d+) ore and (\d+) clay. Each geode robot costs (\d+) ore and (\d+) obsidian.""".toRegex()
        val (ore, clay, obsidian1, obsidian2, geode1, geode2) = inputLineRegex
            .matchEntire(robots)
            ?.destructured
            ?: throw IllegalArgumentException("Incorrect input line $robots")
        return listOf(
            listOf(ore.toInt(), 0, 0, 0),
            listOf(clay.toInt(), 0, 0, 0),
            listOf(obsidian1.toInt(), obsidian2.toInt(), 0, 0),
            listOf(geode1.toInt(), 0, geode2.toInt(), 0)
        )
    }

    fun canBuild(inventory: List<Int>, item: List<Int>): Boolean {
        for (i in inventory.indices) {
            if (inventory[i] < item[i])
                return false
        }
        return true
    }

    fun collect(robots: List<Int>, inventory: MutableList<Int>) {
        for (i in robots.indices) {
            inventory[i] += robots[i]
        }
    }

    fun fastForwardToBuild(
        blueprint: List<List<Int>>,
        robots: MutableList<Int>,
        inventory: MutableList<Int>,
        time: Int,
        build: Int
    ): Int {
        var countdown = time
        while (countdown > 0) {
            countdown--
            if (canBuild(inventory, blueprint[build])) {
                for (i in inventory.indices) {
                    inventory[i] -= blueprint[build][i]
                }
                collect(robots, inventory)
                robots[build]++
                break
            } else {
                collect(robots, inventory)
            }
        }
        return countdown
    }

    fun isBetterThanBest(robots: List<Int>, inventory: List<Int>, time: Int): Boolean {
        val potentialProduction = (0 until time).sumOf { it + robots[geode] }
        return inventory[geode] + potentialProduction > best
    }

    fun mineGeode(blueprint: List<List<Int>>, maxOre: Int, robots: List<Int>, inventory: List<Int>, time: Int): Int {
        if (time == 0) {
            best = listOf(best, inventory.last()).max()
            return inventory.last()
        }
        var geodeCollected = 0
        for (robotToBuild in 3 downTo 0) {
            if (
                (robotToBuild == ore && robots[ore] >= maxOre) ||
                (robotToBuild == clay && robots[clay] >= blueprint[obsidian][clay]) ||
                (robotToBuild == obsidian && (robots[clay] == 0 || robots[obsidian] >= blueprint[geode][obsidian])) ||
                (robotToBuild == geode && robots[obsidian] == 0) ||
                (!isBetterThanBest(robots, inventory, time))
            ) continue
            val newRobots = robots.toMutableList()
            val newInventory = inventory.toMutableList()
            val timeLeft = fastForwardToBuild(blueprint, newRobots, newInventory, time, robotToBuild)
            val newGeode = mineGeode(blueprint, maxOre, newRobots, newInventory, timeLeft)
            geodeCollected = listOf(geodeCollected, newGeode).max()
        }
        return geodeCollected
    }

    fun part1(input: List<String>): Int {
        val blueprints = mutableListOf<List<List<Int>>>()
        for (it in input) {
            blueprints.add(parseInput(it))
        }
        var answer = 0
        for (i in blueprints.indices) {
            best = 0
            val maxOre = blueprints[i].maxOf { it[0] }
            val geodesMined = mineGeode(blueprints[i], maxOre, listOf(1, 0, 0, 0), listOf(0, 0, 0, 0), 24)
            answer += (i + 1) * geodesMined
        }
        return answer
    }

    fun part2(input: String): Int {
        best = 0
        val blueprint = parseInput(input)
        val maxOre = blueprint.maxOf { it[0] }
        val answer = mineGeode(blueprint, maxOre, listOf(1, 0, 0, 0), listOf(0, 0, 0, 0), 32)
        return answer
    }

    val testInput = readInput("Day19_test")
    check(part1(testInput) == 33)
    check(part2(testInput[0]) == 56)
    check(part2(testInput[1]) == 62)

    val input = readInput("Day19")
    println(part1(input))
    val blueprint1 = part2(input[0])
    val blueprint2 = part2(input[1])
    val blueprint3 = part2(input[2])
    println(blueprint1 * blueprint2 * blueprint3)
}
