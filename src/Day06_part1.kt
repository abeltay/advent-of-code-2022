fun main() {
    fun part1(input: String): Int {
        val inArr = input.toCharArray()
        val map = mutableMapOf<Char, Int>()
        for (i in inArr.indices) {
            map[inArr[i]] = map.getOrDefault(inArr[i], 0) + 1
            if (i - 4 >= 0) {
                map[inArr[i - 4]] = map.getOrDefault(inArr[i - 4], 0) - 1
            }
            if (i >= 3 && !map.any { it.value > 1 }) {
                return i + 1
            }
        }
        return 0
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput.first()) == 7)
    check(part1("bvwbjplbgvbhsrlpgdmjqwftvncz") == 5)
    check(part1("nppdvjthqldpwncqszvftbrmjlhg") == 6)
    check(part1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 10)
    check(part1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 11)

    val input = readInput("Day06")
    println(part1(input.first()))
}
