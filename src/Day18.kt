fun main() {
    data class Object3d(
        val x: Int,
        val y: Int,
        val z: Int
    )

    fun parseInput(input: String): Object3d {
        val inputLineRegex = """(\d+),(\d+),(\d+)""".toRegex()

        val (x, y, z) = inputLineRegex
            .matchEntire(input)
            ?.destructured
            ?: throw IllegalArgumentException("Incorrect input line $input")
        return Object3d(
            x.toInt(),
            y.toInt(),
            z.toInt()
        )
    }

    fun sidesExposed(points: List<Object3d>, image: List<List<List<Int>>>): Int {
        var exposed = 0
        for (it in points) {
            for (x in it.x - 1..it.x + 1) {
                if (x < 0 || x >= image.size) {
                    exposed++
                    continue
                }
                if (x == it.x) continue
                if (image[x][it.y][it.z] == 0) {
                    // println("exposed at $x, ${it.y}, ${it.z}")
                    exposed++
                }
            }
            for (y in it.y - 1..it.y + 1) {
                if (y < 0 || y >= image[0].size) {
                    exposed++
                    continue
                }
                if (y == it.y || y >= image[0].size) continue
                if (image[it.x][y][it.z] == 0) {
                    // println("exposed at ${it.x}, ${y}, ${it.z}")
                    exposed++
                }
            }
            for (z in it.z - 1..it.z + 1) {
                if (z < 0 || z >= image[0][0].size) {
                    exposed++
                    continue
                }
                if (z == it.z || z >= image[0][0].size) continue
                if (image[it.x][it.y][z] == 0) {
                    // println("exposed at ${it.x}, ${it.y}, ${z}")
                    exposed++
                }
            }
        }
        return exposed
    }

    fun part1(input: List<String>): Int {
        val points = mutableListOf<Object3d>()
        for (it in input) {
            points.add(parseInput(it))
        }
        val image =
            MutableList(points.maxOf { it.x + 1 }) { MutableList(points.maxOf { it.y + 1 }) { MutableList(points.maxOf { it.z + 1 }) { 0 } } }
        for (it in points) {
            image[it.x][it.y][it.z] = 1
        }
        return sidesExposed(points, image)
    }

    fun floodFillNegative(image: MutableList<MutableList<MutableList<Int>>>, queue: MutableList<Object3d>) {
        val object3d = queue.removeFirst()
        if (
            object3d.x < 0 || object3d.x >= image.size ||
            object3d.y < 0 || object3d.y >= image[0].size ||
            object3d.z < 0 || object3d.z >= image[0][0].size
        ) {
            return
        }
        if (image[object3d.x][object3d.y][object3d.z] == 0) {
            image[object3d.x][object3d.y][object3d.z] = -1
            queue.add(Object3d(object3d.x - 1, object3d.y, object3d.z))
            queue.add(Object3d(object3d.x + 1, object3d.y, object3d.z))
            queue.add(Object3d(object3d.x, object3d.y - 1, object3d.z))
            queue.add(Object3d(object3d.x, object3d.y + 1, object3d.z))
            queue.add(Object3d(object3d.x, object3d.y, object3d.z - 1))
            queue.add(Object3d(object3d.x, object3d.y, object3d.z + 1))
        }
    }

    fun part2(input: List<String>): Int {
        val points = mutableListOf<Object3d>()
        for (it in input) {
            points.add(parseInput(it))
        }
        val image =
            MutableList(points.maxOf { it.x + 1 }) { MutableList(points.maxOf { it.y + 1 }) { MutableList(points.maxOf { it.z + 1 }) { 0 } } }
        for (it in points) {
            image[it.x][it.y][it.z] = 1
        }
        var answer = sidesExposed(points, image)
        val queue = mutableListOf(Object3d(0, 0, 0))
        while (queue.isNotEmpty()) {
            floodFillNegative(image, queue)
        }
        val airPoints = mutableListOf<Object3d>()
        val airImage =
            MutableList(points.maxOf { it.x + 1 }) { MutableList(points.maxOf { it.y + 1 }) { MutableList(points.maxOf { it.z + 1 }) { 0 } } }
        for (x in image.indices) {
            for (y in image[0].indices) {
                for (z in image[0][0].indices) {
                    if (image[x][y][z] == 0) {
                        airPoints.add(Object3d(x, y, z))
                        airImage[x][y][z] = 1
                    } else {
                        airImage[x][y][z] = 0
                    }
                }
            }
        }
        answer -= sidesExposed(airPoints, airImage)
        return answer
    }

    val testInput = readInput("Day18_test")
    check(part1(listOf("1,1,1", "2,1,1")) == 10)
    check(part1(testInput) == 64)
    check(part2(testInput) == 58)

    val input = readInput("Day18")
    println(part1(input))
    println(part2(input))
}
