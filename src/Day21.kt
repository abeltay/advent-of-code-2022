fun main() {
    val human = "humn"
    fun parseInput(input: List<String>): MutableMap<String, String> {
        val out = mutableMapOf<String, String>()
        for (it in input) {
            val parts = it.split(": ")
            out[parts[0]] = parts[1]
        }
        return out
    }

    fun monkeyYell(monkeys: MutableMap<String, String>, monkey: String): Long {
        val yell = monkeys[monkey]
        val num = yell?.toLongOrNull()
        if (num != null) {
            return num
        }
        val str = yell!!.split(" ")
        return when (str[1]) {
            "+" -> monkeyYell(monkeys, str[0]) + monkeyYell(monkeys, str[2])
            "-" -> monkeyYell(monkeys, str[0]) - monkeyYell(monkeys, str[2])
            "*" -> monkeyYell(monkeys, str[0]) * monkeyYell(monkeys, str[2])
            else -> monkeyYell(monkeys, str[0]) / monkeyYell(monkeys, str[2])
        }
    }

    fun isHumanHere(monkeys: MutableMap<String, String>, monkey: String): Boolean {
        if (monkey == human) {
            return true
        }
        val yell = monkeys[monkey]
        val num = yell?.toLongOrNull()
        if (num != null) {
            return false
        }
        val str = yell!!.split(" ")
        if (isHumanHere(monkeys, str[0])) return true
        if (isHumanHere(monkeys, str[2])) return true
        return false
    }

    fun workBackwards(monkeys: MutableMap<String, String>, monkey: String, result: Long): Long {
        val yell = monkeys[monkey]
        val str = yell!!.split(" ")
        val humanIsRight = isHumanHere(monkeys, str[2])
        val num = if (humanIsRight) {
            monkeyYell(monkeys, str[0])
        } else {
            monkeyYell(monkeys, str[2])
        }
        val nextResult = when (str[1]) {
            "+" -> result - num
            "-" -> {
                if (humanIsRight) {
                    num - result
                } else {
                    result + num
                }
            }

            "*" -> result / num
            else -> {
                if (humanIsRight) {
                    (num / result) + 1
                } else {
                    num * result
                }
            }
        }
        if (str[0] == human) {
            return nextResult
        }
        if (str[1] == human) {
            return nextResult
        }
        return if (humanIsRight) {
            workBackwards(monkeys, str[2], nextResult)
        } else {
            workBackwards(monkeys, str[0], nextResult)
        }
    }

    fun monkeyYell2(monkeys: MutableMap<String, String>): Long {
        val str = monkeys["root"]!!.split(" ")
        val humanIsRight = isHumanHere(monkeys, str[2])
        val num = if (humanIsRight) {
            monkeyYell(monkeys, str[0])
        } else {
            monkeyYell(monkeys, str[2])
        }
        return if (humanIsRight) {
            workBackwards(monkeys, str[2], num)
        } else {
            workBackwards(monkeys, str[0], num)
        }
    }

    fun part1(input: List<String>): Long {
        val monkeys = parseInput(input)
        return monkeyYell(monkeys, "root")
    }

    fun part2(input: List<String>): Long {
        val monkeys = parseInput(input)
        return monkeyYell2(monkeys)
    }

    val testInput = readInput("Day21_test")
    check(part1(testInput) == 152L)
    check(part2(testInput) == 301L)

    val input = readInput("Day21")
    println(part1(input))
    println(part2(input))
}
