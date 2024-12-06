fun main() {



    fun part1(input: List<String>): Int {
        val grid = Grid(input)
        val visited: MutableSet<Point> = mutableSetOf()
        var direction = Compass.NORTH
        var position = grid.points.first { grid.get(it) == '^' }

        while (position.inBounds(grid)) {
            visited += position
            var newPos = position.move(direction)
            if (grid.get(newPos) == '#') {
                direction = direction.rotate90Right()
                newPos = position.move(direction)
            }
            position = newPos
        }

        return visited.size
    }

    fun part2(input: List<String>): Int {

        return 0
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41) { "Expected 41 but got ${part1(testInput)}" }
//    check(part2(testInput) == 123) { "Expected 123 but got ${part2(testInput)}" }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
//    part2(input).println()
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
