fun main() {
    fun part1(input: List<String>): Int {
        val grid = mutableListOf<List<Int>>()
        val seen = mutableListOf<MutableList<Boolean>>()
        for (it in input) {
            val intArr = mutableListOf<Int>()
            for (char in it.toList()) {
                intArr.add(char.digitToInt())
            }
            grid.add(intArr)
            seen.add(MutableList(it.length) { false })
        }
        for (i in grid.indices) {
            var curHeight = -1
            for (j in grid[i].indices) {
                val tree = grid[i][j]
                if (tree > curHeight) {
                    seen[i][j] = true
                    curHeight = tree
                }
            }
            curHeight = -1
            for (j in grid[i].indices.reversed()) {
                val tree = grid[i][j]
                if (tree > curHeight) {
                    seen[i][j] = true
                    curHeight = tree
                }
            }
        }
        for (j in grid[0].indices) {
            var curHeight = -1
            for (i in grid.indices) {
                val tree = grid[i][j]
                if (tree > curHeight) {
                    seen[i][j] = true
                    curHeight = tree
                }
            }
            curHeight = -1
            for (i in grid.indices.reversed()) {
                val tree = grid[i][j]
                if (tree > curHeight) {
                    seen[i][j] = true
                    curHeight = tree
                }
            }
        }
        return seen.sumOf { row -> row.count { it } }
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21)

    val input = readInput("Day08")
    println(part1(input))
}
