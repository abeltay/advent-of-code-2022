fun main() {
    fun part2(input: String): Int {
        val inArr = input.toCharArray()
        val map = mutableMapOf<Char, Int>()
        for (i in inArr.indices) {
            map[inArr[i]] = map.getOrDefault(inArr[i], 0) + 1
            if (i - 14 >= 0) {
                map[inArr[i - 14]] = map.getOrDefault(inArr[i - 14], 0) - 1
            }
            if (i >= 13 && !map.any { it.value > 1 }) {
                return i + 1
            }
        }
        return 0
    }

    val testInput = readInput("Day06_test")
    check(part2(testInput.first()) == 19)
    check(part2("bvwbjplbgvbhsrlpgdmjqwftvncz") == 23)
    check(part2("nppdvjthqldpwncqszvftbrmjlhg") == 23)
    check(part2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg") == 29)
    check(part2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw") == 26)

    val input = readInput("Day06")
    println(part2(input.first()))
}
