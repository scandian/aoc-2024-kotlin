import kotlin.io.path.Path
import kotlin.io.path.readText


data class VecL(val x: Long, val y: Long) {
    infix operator fun plus(o: VecL) = VecL(x+o.x, y+o.y)
    infix operator fun minus(o: VecL) = VecL(x-o.x, y-o.y)
}
fun Pair<Long, Long>.toVecL() = VecL(this.first, this.second)
infix operator fun Long.times(point: VecL) = VecL(this*point.x, this*point.y)

data class Machine(val a: VecL, val b: VecL, val prize: VecL)

val regex = """\bX[+=](?<X>\d+), Y[+=](?<Y>\d+)\b""".toRegex()
const val shift: Long = 10000000000000L
val offset = VecL(shift, shift)

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R): ResultOf<R>()
    data class Failure(val message: String? = null): ResultOf<Nothing>()
}

typealias Input = String

private fun Input.parse(prizeOffset: VecL = VecL(0, 0)): List<Machine> = buildList {
    this@parse.split("\n\n").map { section ->
        val points: List<Pair<Long, Long>> = section.lines().map { line ->
            val (x, y) = regex.find(line)?.destructured ?: error("No match")
            Pair(x.toLong(), y.toLong())
        }
        add(Machine(points[0].toVecL(), points[1].toVecL(), points[2].toVecL() + prizeOffset))
    }
}

fun Machine.solve(): ResultOf<Long> {
    val a1 = a.x
    val a2 = a.y
    val b1 = b.x
    val b2 = b.y
    val p1 = prize.x
    val p2 = prize.y

    val detM = a1 * b2 - a2 * b1
    if (detM == 0L) return ResultOf.Failure("No unique solutions")

    val detMx = p1 * b2 - p2 * b1
    val detMy = a1 * p2 - a2 * p1

    val x = detMx / detM
    val y = detMy / detM

    return if ((detMx % detM == 0L) && (detMy % detM == 0L)) {
        ResultOf.Success((3 * x) + y)
    } else {
        ResultOf.Failure("No integer solutions found")
    }
}

fun List<Machine>.solve(): Long = this.map { it.solve() }.mapNotNull { result ->
    when (result) {
        is ResultOf.Success<Long> -> result.value
        is ResultOf.Failure -> null
    }
}.sum()


fun main() {

    val testMachines = Path("src/Day13_test.txt").readText().trim().parse()
    val machines = Path("src/Day13.txt").readText().trim().parse()
    val machines2 = Path("src/Day13.txt").readText().trim().parse(VecL(shift, shift))


    testMachines.solve().println()
    machines.solve().println()
    machines2.solve().println()
}