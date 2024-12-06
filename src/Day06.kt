fun main() {

    fun part1(input: List<String>): Int {
        val grid = Grid(input)
        val position = grid.points.first { grid.get(it) == '^' }

        val (_, visited) = patrol(position, grid)

        return visited.size
    }

    fun part2(input: List<String>): Int {
        val grid = Grid(input)
        val initialPosition = grid.points.first { grid.get(it) == '^' }

        val (_, possibleObstacles) = patrol(initialPosition, grid)

        return possibleObstacles.count { position ->
            position != initialPosition && patrol(initialPosition, grid, position).first
        }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41) { "Expected 41 but got ${part1(testInput)}" }
    check(part2(testInput) == 6) { "Expected 6 but got ${part2(testInput)}" }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}

private fun patrol(
    initialPosition: Point,
    grid: Grid,
    obstacle: Point? = null
): Pair<Boolean, Set<Point>> {
    var position = initialPosition
    var direction = Compass.NORTH
    val visited: MutableSet<Pair<Point,Compass>> = mutableSetOf()

    var loop = false

    while (position.inBounds(grid) && !loop) {
        loop = !visited.add(position to direction)
        val newPos = position.move(direction)
        if (newPos.inBounds(grid) && (grid.get(newPos) == '#' || newPos == obstacle)) {
            direction = direction.rotate90Right()
        } else {
            position = newPos
        }
    }

    return loop to visited.mapTo(mutableSetOf()) { it.first }
}

private fun Compass.rotate90Right(): Compass {
    return when (this) {
        Compass.NORTH -> Compass.EAST
        Compass.NORTHEAST -> Compass.SOUTHEAST
        Compass.EAST -> Compass.SOUTH
        Compass.SOUTHEAST -> Compass.SOUTHWEST
        Compass.SOUTH -> Compass.WEST
        Compass.SOUTHWEST -> Compass.NORTHWEST
        Compass.WEST -> Compass.NORTH
        Compass.NORTHWEST -> Compass.NORTHEAST
    }
}
