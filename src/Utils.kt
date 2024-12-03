import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)


/**
 * Returns a new list with the element at the specified index omitted.
 *
 * @param index the index of the element to omit from the list.
 * @return a new list with the specified element omitted.
 */
fun <T> List<T>.omit(index: Int) = filterIndexed { i, _ -> i != index }
