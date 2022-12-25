fun main() {
    fun convertToNum(snafu: Char): Int {
        return when (snafu) {
            '-' -> -1
            '=' -> -2
            else -> snafu.digitToInt()
        }
    }

    fun convertToSNAFU(num: Int): Char {
        return when (num) {
            -1 -> '-'
            -2 -> '='
            else -> Character.forDigit(num, 10)
        }
    }

    fun snafuToList(snafu: String): List<Int> {
        val output = mutableListOf<Int>()
        for (it in snafu) {
            output.add(convertToNum(it))
        }
        return output
    }

    fun snafuAdder(snafu1: List<Int>, snafu2: List<Int>): List<Int> {
        if (snafu2.size > snafu1.size) {
            return snafuAdder(snafu2, snafu1)
        }
        val output = snafu1.toMutableList()
        var pointer = output.size - 1
        for (it in snafu2.reversed()) {
            output[pointer] += it
            pointer--
        }
        return output
    }

    fun normalise(base: Int, snafu: MutableList<Int>): String {
        val output = mutableListOf<Char>()
        for (i in snafu.indices.reversed()) {
            val original = snafu[i] + 2
            var dividend = original / base
            var remainder = (original % base) - 2
            if (remainder < -2) {
                remainder += base
                dividend--
            }
            output.add(0, convertToSNAFU(remainder))
            if (i - 1 >= 0)
                snafu[i - 1] += dividend
        }
        return String(output.toCharArray())
    }

    fun part1(input: List<String>): String {
        var result = listOf(0)
        for (it in input) {
            result = snafuAdder(result, snafuToList(it))
        }
        return normalise(5, result.toMutableList())
    }

    val testInput = readInput("Day25_test")
    check(part1(testInput) == "2=-1=0")

    val input = readInput("Day25")
    println(part1(input))
}
