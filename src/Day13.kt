fun main() {
    fun getNumber(list: MutableList<Char>): Int? {
        if (!list[0].isDigit()) {
            return null
        }
        if (list[1].isDigit()) {
            val number = "" + list[0] + list[1]
            return number.toInt()
        }
        return list[0].digitToInt()
    }

    fun bracket(array: MutableList<Char>) {
        var closing = 1
        if (array[1].isDigit()) {
            closing++
        }
        array.add(closing, ']')
        array.add(0, '[')
    }

    fun isInOrder(first: MutableList<Char>, second: MutableList<Char>): Boolean {
        val firstNumber = getNumber(first)
        val secondNumber = getNumber(second)
        if (firstNumber != null && secondNumber != null) {
            if (firstNumber < secondNumber) return true
            if (firstNumber > secondNumber) return false
        }
        if (firstNumber != null && second[0] == '[') {
            bracket(first)
        }
        if (first[0] == '[' && secondNumber != null) {
            bracket(second)
        }
        return when {
            first[0] == ']' && second[0] != ']' -> true
            first[0] != ']' && second[0] == ']' -> false
            else -> isInOrder(first.subList(1, first.size), second.subList(1, second.size))
        }
    }

    fun part1(input: List<String>): Int {
        var answer = 0
        var i = 0
        while (i < input.size) {
            if (isInOrder(input[i].toMutableList(), input[i + 1].toMutableList())) {
                answer += (i / 3 + 1)
            }
            i += 3
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        val compare: (String, String) -> Int = { a: String, b: String ->
            Int
            if (isInOrder(a.toMutableList(), b.toMutableList())) -1 else 1
        }

        val newInput = input.toMutableList()
        while (newInput.remove("")) {
        }
        newInput.add("[[2]]")
        newInput.add("[[6]]")
        newInput.sortWith(compare)
        return (newInput.indexOf("[[2]]") + 1) * (newInput.indexOf("[[6]]") + 1)
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 140)

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}
