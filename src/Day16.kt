import java.util.*

fun main() {
    data class Valve(
        val name: String,
        val flowRate: Int,
        val vertex: MutableList<Int> = mutableListOf()
    )

    data class State(
        val openedValves: Set<Int>,
        val location: Int,
        val pressure: Int,
        val timeLeft: Int
    )

    val stateComparator = Comparator<State> { a, b ->
        if (a.timeLeft != b.timeLeft) b.timeLeft - a.timeLeft else b.pressure - a.pressure
    }

    fun parseInput(input: List<String>): List<Valve> {
        val inputLineRegex = """Valve ([A-Z]+) has flow rate=(\d+)""".toRegex()
        val valves = mutableListOf<Valve>()
        for (it in input) {
            val part = it.substringBefore(';')
            val (valve, flowString) = inputLineRegex
                .matchEntire(part)
                ?.destructured
                ?: throw IllegalArgumentException("Incorrect input line $it")
            valves.add(Valve(name = valve, flowRate = flowString.toInt()))
        }
        for ((idx, value) in input.withIndex()) {
            val part = if (value.contains("valves")) {
                value.substringAfter("; tunnels lead to valves ")
            } else {
                value.substringAfter("; tunnel leads to valve ")
            }
            val linkedValves = part.split(", ")
            for (it in linkedValves) {
                val valve = valves.indexOfFirst { valve -> valve.name == it }
                if (valve == -1) {
                    throw IllegalArgumentException("Incorrect input $valve")
                }
                valves[idx].vertex.add(valve)
            }
        }
        return valves
    }

    fun updateMatrix(map: MutableList<MutableList<Int>>, from: Int, to: Int) {
        val cost = map[from][to]
        for (i in map[to].indices) {
            val newCost = cost + map[from][i]
            if (newCost < map[to][i]) {
                map[to][i] = newCost
                map[i][to] = newCost
            }
        }
    }

    fun createAdjacencyMatrix(valves: List<Valve>): MutableList<MutableList<Int>> {
        val limit = 9
        val adjacencyMatrix = MutableList(valves.size) { MutableList(valves.size) { limit } }
        for (i in valves.indices) {
            for (j in valves[i].vertex) {
                adjacencyMatrix[i][j] = 1
                adjacencyMatrix[j][i] = 1
            }
        }
        for (i in valves.indices) {
            adjacencyMatrix[i][i] = 0
        }
        for (i in adjacencyMatrix.indices) {
            for (j in adjacencyMatrix[i].indices) {
                updateMatrix(adjacencyMatrix, i, j)
            }
        }
        return adjacencyMatrix
    }

    fun getFlow(valves: List<Valve>, openedValves: Set<Int>): Int {
        var flow = 0
        for (it in openedValves) {
            flow += valves[it].flowRate
        }
        return flow
    }

    fun explore(
        valves: List<Valve>,
        adjacencyMatrix: List<List<Int>>,
        priorityQueue: PriorityQueue<State>,
        optimisedMap: HashMap<Set<Int>, Int>
    ) {
        val state = priorityQueue.poll()
        val flowRate = getFlow(valves, state.openedValves)
        if (!state.openedValves.contains(state.location) && valves[state.location].flowRate > 0) {
            val newValves = state.openedValves.toMutableSet()
            newValves.add(state.location)
            priorityQueue.add(
                State(
                    openedValves = newValves,
                    location = state.location,
                    pressure = state.pressure + flowRate,
                    timeLeft = state.timeLeft - 1
                )
            )
            val newEndPressure =
                state.pressure + flowRate + ((flowRate + valves[state.location].flowRate) * (state.timeLeft - 1))
            val sorted = newValves.sorted().toSet()
            val existingPressure = optimisedMap[sorted]
            if (existingPressure == null || existingPressure < newEndPressure) {
                optimisedMap[sorted] = newEndPressure
            }
            return
        }
        for (i in adjacencyMatrix[state.location].indices) {
            if (i == state.location || valves[i].flowRate == 0 || state.openedValves.contains(i)) continue
            val timeAfterMoving = state.timeLeft - adjacencyMatrix[state.location][i]
            if (timeAfterMoving > 0) {
                priorityQueue.add(
                    State(
                        openedValves = state.openedValves,
                        location = i,
                        pressure = state.pressure + flowRate * adjacencyMatrix[state.location][i],
                        timeLeft = state.timeLeft - adjacencyMatrix[state.location][i]
                    )
                )
            }
        }
    }

    fun part1(input: List<String>): Int {
        val valves = parseInput(input)
        val adjacencyMatrix = createAdjacencyMatrix(valves)
        val priorityQueue = PriorityQueue(stateComparator)
        priorityQueue.add(
            State(
                openedValves = setOf(),
                location = valves.indexOfFirst { it.name == "AA" },
                pressure = 0,
                timeLeft = 30
            )
        )
        val optimisedMap = HashMap<Set<Int>, Int>()
        while (priorityQueue.isNotEmpty()) {
            explore(valves, adjacencyMatrix, priorityQueue, optimisedMap)
        }
        return optimisedMap.maxOf { it.value }
    }

    fun part2(input: List<String>): Int {
        val valves = parseInput(input)
        val adjacencyMatrix = createAdjacencyMatrix(valves)
        val priorityQueue = PriorityQueue(stateComparator)
        priorityQueue.add(
            State(
                openedValves = setOf(),
                location = valves.indexOfFirst { it.name == "AA" },
                pressure = 0,
                timeLeft = 26
            )
        )
        val optimisedMap = HashMap<Set<Int>, Int>()
        while (priorityQueue.isNotEmpty()) {
            explore(valves, adjacencyMatrix, priorityQueue, optimisedMap)
        }
        val usableValves = mutableListOf<Int>()
        for ((idx, valve) in valves.withIndex()) {
            if (valve.flowRate > 0)
                usableValves.add(idx)
        }
        var answer = 0
        for ((idx, value) in optimisedMap) {
            for ((idx2, value2) in optimisedMap) {
                if (idx.intersect(idx2).isEmpty()) {
                    val pressure = value + value2
                    if (pressure > answer)
                        answer = pressure
                }
            }
        }
        return answer
    }

    val testInput = readInput("Day16_test")
    check(part1(testInput) == 1651)
    check(part2(testInput) == 1707)

    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
