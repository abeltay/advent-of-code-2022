fun main() {
    data class Input(
        val grid: List<List<Int>>,
        val start: Pair<Int, Int>,
        val end: Pair<Int, Int>
    )

    fun parseInput(input: List<String>): Input {
        val grid = mutableListOf<List<Int>>()
        var start = Pair(0, 0)
        var end = Pair(0, 0)
        for ((i, v) in input.withIndex()) {
            val row = MutableList(v.length) { 0 }
            val characters = v.toCharArray()
            for ((i1, v1) in characters.withIndex()) {
                when (v1) {
                    'S' -> {
                        start = Pair(i, i1)
                        row[i1] = 1
                    }

                    'E' -> {
                        end = Pair(i, i1)
                        row[i1] = 'z' - 'a' + 1
                    }

                    else -> row[i1] = v1 - 'a' + 1
                }
            }
            grid.add(row)
        }
        return Input(
            grid = grid,
            start = start,
            end = end
        )
    }

    data class Explorer(
        var location: Pair<Int, Int>,
        var steps: Int = 0
    )

    fun move(
        grid: List<List<Int>>,
        visited: MutableList<MutableList<Boolean>>,
        explorer: Explorer,
        newLocation: Pair<Int, Int>
    ): Explorer? {
        if (newLocation.first < 0 || newLocation.first >= grid.size || newLocation.second < 0 || newLocation.second >= grid[0].size)
            return null
        if (visited[newLocation.first][newLocation.second]) {
            return null
        }
        if (grid[newLocation.first][newLocation.second] > grid[explorer.location.first][explorer.location.second] + 1) {
            return null
        }
        visited[newLocation.first][newLocation.second] = true
        return Explorer(
            location = newLocation,
            steps = explorer.steps + 1
        )
    }

    fun moveDown(
        grid: List<List<Int>>,
        visited: MutableList<MutableList<Boolean>>,
        explorer: Explorer,
        newLocation: Pair<Int, Int>
    ): Explorer? {
        if (newLocation.first < 0 || newLocation.first >= grid.size || newLocation.second < 0 || newLocation.second >= grid[0].size)
            return null
        if (visited[newLocation.first][newLocation.second]) {
            return null
        }
        if (grid[newLocation.first][newLocation.second] < grid[explorer.location.first][explorer.location.second] - 1) {
            return null
        }
        visited[newLocation.first][newLocation.second] = true
        return Explorer(
            location = newLocation,
            steps = explorer.steps + 1
        )
    }

    fun part1(input: List<String>): Int {
        val data = parseInput(input)
        val visited = MutableList(data.grid.size) { MutableList(data.grid[0].size) { false } }
        visited[data.start.first][data.start.second] = true
        val queue = mutableListOf<Explorer>()
        queue.add(Explorer(location = data.start))
        while (queue.isNotEmpty()) {
            val explorer = queue.removeFirst()
            if (explorer.location == data.end) {
                return explorer.steps
            }
            val up = move(data.grid, visited, explorer, Pair(explorer.location.first - 1, explorer.location.second))
            if (up != null) queue.add(up)
            val down = move(data.grid, visited, explorer, Pair(explorer.location.first + 1, explorer.location.second))
            if (down != null) queue.add(down)
            val left = move(data.grid, visited, explorer, Pair(explorer.location.first, explorer.location.second - 1))
            if (left != null) queue.add(left)
            val right = move(data.grid, visited, explorer, Pair(explorer.location.first, explorer.location.second + 1))
            if (right != null) queue.add(right)
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val data = parseInput(input)
        val visited = MutableList(data.grid.size) { MutableList(data.grid[0].size) { false } }
        visited[data.end.first][data.end.second] = true
        val queue = mutableListOf<Explorer>()
        queue.add(Explorer(location = data.end))
        while (queue.isNotEmpty()) {
            val explorer = queue.removeFirst()
            if (data.grid[explorer.location.first][explorer.location.second] == 1) {
                return explorer.steps
            }
            val up = moveDown(data.grid, visited, explorer, Pair(explorer.location.first - 1, explorer.location.second))
            if (up != null) queue.add(up)
            val down =
                moveDown(data.grid, visited, explorer, Pair(explorer.location.first + 1, explorer.location.second))
            if (down != null) queue.add(down)
            val left =
                moveDown(data.grid, visited, explorer, Pair(explorer.location.first, explorer.location.second - 1))
            if (left != null) queue.add(left)
            val right =
                moveDown(data.grid, visited, explorer, Pair(explorer.location.first, explorer.location.second + 1))
            if (right != null) queue.add(right)
        }
        return 0
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 31)
    check(part2(testInput) == 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
