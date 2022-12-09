fun main() {
    fun part1(input: List<String>): Int {
        var headX = 0
        var headY = 0
        var tailX = 0
        var tailY = 0
        val set = mutableSetOf(Pair(tailX, tailY))
        for (it in input) {
            val direction = it.substringBefore(' ')
            val steps = it.substringAfter(' ').toInt()
            when (direction) {
                "R" -> headX += steps
                "L" -> headX -= steps
                "U" -> headY += steps
                else -> headY -= steps
            }
            while (true) {
                if (headX - 1 <= tailX && headX + 1 >= tailX && headY - 1 <= tailY && headY + 1 >= tailY) {
                    break
                }
                if (headX > tailX) tailX++
                if (headX < tailX) tailX--
                if (headY > tailY) tailY++
                if (headY < tailY) tailY--
                set.add(Pair(tailX, tailY))
            }
        }
        return set.size
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)

    val input = readInput("Day09")
    println(part1(input))
}
