fun main() {
    fun part2(input: List<String>): Int {
        fun scenicScore(grid: List<List<Int>>, coord: Pair<Int, Int>): Int {
            val tree = grid[coord.first][coord.second]
            var west = 0
            for (i in coord.first - 1 downTo 0) {
                west++
                if (grid[i][coord.second] >= tree) {
                    break
                }
            }
            var east = 0
            for (i in coord.first + 1 until grid.size) {
                east++
                if (grid[i][coord.second] >= tree) {
                    break
                }
            }
            var north = 0
            for (j in coord.second - 1 downTo 0) {
                north++
                if (grid[coord.first][j] >= tree) {
                    break
                }
            }
            var south = 0
            for (j in coord.second + 1 until grid[0].size) {
                south++
                if (grid[coord.first][j] >= tree) {
                    break
                }
            }
            return north * south * east * west
        }

        val grid = mutableListOf<List<Int>>()
        val score = mutableListOf<MutableList<Int>>()
        for (it in input) {
            val intArr = mutableListOf<Int>()
            for (char in it.toList()) {
                intArr.add(char.digitToInt())
            }
            grid.add(intArr)
            score.add(MutableList(it.length) { 0 })
        }
        for (i in 1 until grid.size - 1) {
            for (j in 1 until grid[i].size - 1) {
                score[i][j] = scenicScore(grid, Pair(i, j))
            }
        }
        return score.maxOf { it.max() }
    }

    val testInput = readInput("Day08_test")
    check(part2(testInput) == 8)

    val input = readInput("Day08")
    println(part2(input))
}
