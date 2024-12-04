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

class Point(val x: Int, val y: Int) {
    fun move(d: Compass) = Point(x + d.x, y + d.y)
}

class Grid(val data: List<String>) {
    val rangeX = 0..data[0].lastIndex
    val rangeY = 0..data.lastIndex
    val points = rangeY.flatMap { y -> rangeX.map { x -> Point(x, y) } }
    fun get(p: Point) = if (p.x in rangeX && p.y in rangeY) data[p.y][p.x] else '.'
}

enum class Compass(val x: Int, val y: Int) {
    NORTH(0, 1),
    NORTHEAST(1, 1),
    EAST(1, 0),
    SOUTHEAST(1, -1),
    SOUTH(0, -1),
    SOUTHWEST(-1, -1),
    WEST(-1, 0),
    NORTHWEST(-1, 1),
}
