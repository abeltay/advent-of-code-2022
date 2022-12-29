fun main() {
    val right = 0
    val down = 1
    val left = 2
    val up = 3

    fun parseDirections(input: String): MutableList<String> {
        val output = mutableListOf<String>()
        var buffer = ""
        for (it in input) {
            if (it.isDigit()) {
                buffer += it
            } else {
                output.add(buffer)
                buffer = ""
                output.add(it.toString())
            }
        }
        return output
    }

    data class Facing(
        val x: Int,
        val y: Int,
        val direction: Int
    )

    fun moveStraight(map: List<List<Char>>, facing: Facing, steps: Int): Facing {
        if (steps == 0) {
            return facing
        }
        var x = facing.x
        var y = facing.y
        when (facing.direction) {
            right -> {
                x++
                if (x >= map[y].size || map[y][x] == ' ') {
                    x = map[y].indexOfFirst { it != ' ' }
                }
            }

            left -> {
                x--
                if (x < 0 || map[y][x] == ' ') {
                    x = map[y].indexOfLast { it != ' ' }
                }
            }

            down -> {
                y++
                if (y >= map.size || map[y].size <= x || map[y][x] == ' ') {
                    for (col in map.indices) {
                        if (map[col].size > x && map[col][x] != ' ') {
                            y = col
                            break
                        }
                    }
                }
            }

            else -> {
                y--
                if (y < 0 || map[y].size <= x || map[y][x] == ' ') {
                    for (col in map.indices.reversed()) {
                        if (map[col].size > x && map[col][x] != ' ') {
                            y = col
                            break
                        }
                    }
                }
            }
        }
        if (map[y][x] == '#') {
            return facing
        }
        val newFacing = Facing(x = x, y = y, direction = facing.direction)
        return moveStraight(map, newFacing, steps - 1)
    }

    fun solve(map: List<List<Char>>, directions: MutableList<String>, facing: Facing): Facing {
        if (directions.isEmpty()) {
            return facing
        }
        val direction = directions.removeFirst()
        val steps = direction.toIntOrNull()
        if (steps != null) {
            val newFacing = moveStraight(map, facing, steps)
            return solve(map, directions, newFacing)
        } else {
            var newDirection: Int
            if (direction == "L") {
                newDirection = facing.direction - 1
                if (newDirection < 0) newDirection += 4
            } else {
                newDirection = (facing.direction + 1) % 4
            }
            return solve(
                map, directions, Facing(
                    x = facing.x,
                    y = facing.y,
                    direction = newDirection
                )
            )
        }
    }

    fun part1(input: List<String>): Int {
        val part2 = input.indexOf("")
        val map = input.slice(0 until part2).map { it.toCharArray().toList() }
        val facing = solve(map, parseDirections(input.last()), Facing(x = map[0].indexOf('.'), y = 0, direction = 0))
        return (1000 * (facing.y + 1)) + (4 * (facing.x + 1)) + facing.direction
    }

    fun part2(input: List<String>): Int {
        val part2 = input.indexOf("")
        val map = input.slice(0 until part2).map { it.toCharArray().toList() }
        val facing = solve(map, parseDirections(input.last()), Facing(x = map[0].indexOf('.'), y = 0, direction = 0))
        return (1000 * (facing.y + 1)) + (4 * (facing.x + 1)) + facing.direction
    }

    val testInput = readInput("Day22_test")
    check(part1(testInput) == 6032)
    // check(part2(testInput) == 5031)

    val input = readInput("Day22")
    println(part1(input))
    // println(part2(input))
}
