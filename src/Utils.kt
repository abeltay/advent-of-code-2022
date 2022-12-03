import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

// https://stackoverflow.com/questions/65248942/how-to-split-a-list-into-sublists-using-a-predicate-with-kotlin
fun splitListByEmptyLine(lines: List<String>): List<List<String>> {
    return lines
        .flatMapIndexed { index, x ->
            when {
                index == 0 || index == lines.lastIndex -> listOf(index)
                x.isEmpty() -> listOf(index - 1, index + 1)
                else -> emptyList()
            }
        }
        .windowed(size = 2, step = 2) { (from, to) -> lines.slice(from..to) }
}
