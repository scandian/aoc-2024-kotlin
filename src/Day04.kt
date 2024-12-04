
private const val XMAS = "XMAS"


fun main() {

    fun Grid.isXmas(point: Point, direction: Compass): Boolean =
        generateSequence(point) {it.move(direction)}.map { get(it) }.take(4).joinToString("") == XMAS

    fun part1(input: List<String>): Int {
        val grid = Grid(input)

        return grid.points.sumOf { point -> Compass.entries.toTypedArray().count { grid.isXmas(point, it) } }
    }

    fun Grid.isXmas2(point: Point): Boolean {
        return if (get(point) == 'A') {
            val nw = get(point.move(Compass.NORTHWEST))
            val se = get(point.move(Compass.SOUTHEAST))

            val ne = get(point.move(Compass.NORTHEAST))
            val sw = get(point.move(Compass.SOUTHWEST))

            val ms = setOf('M', 'S')
            setOf(nw, se) == ms && setOf(ne, sw) == ms
        } else false
    }

    fun part2(input: List<String>): Int {
        val grid = Grid(input)

        return grid.points.count { grid.isXmas2(it) }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18) { "Expected 18 but got ${part1(testInput)}" }

    val test2Input = readInput("Day04_test2")
    check(part2(test2Input) == 9) { "Expected 9 but got ${part2(test2Input)}" }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
