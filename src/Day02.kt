fun main() {

    fun checkReport(report: List<Int>): Int {
        var incCount = 0
        var decCount = 0

        report.windowed(2).map {
            when (it[1] - it[0]) {
                1, 2, 3 -> {
                    incCount++
                }

                -1, -2, -3 -> {
                    decCount++
                }

                else -> return 0
            }
        }

        return when {
            incCount > 0 && decCount > 0 -> 0
            else -> 1
        }
    }

    fun applyDamper(report: List<Int>): Int {
        for (i in 0 until report.size - 1) {
            val subReport = report.filterIndexed { index, _ -> index != i }
            require(subReport.size == report.size - 1)
            val checkSubReport = checkReport(subReport)
            if (checkSubReport == 1) {
                return 1
            }
        }

        return 0
    }

    fun part1(input: List<String>): Int {
        return input.map { report ->
            report.split(" ").map { it.toInt() }
        }.map(::checkReport)
            .sum()
    }

    fun part2(input: List<String>): Int {
        val reports = input.map { report ->
            report.split(" ").map { it.toInt() }
        }

        return reports.sumOf {
            when (checkReport(it)) {
                1 -> 1
                else -> applyDamper(it)
            }
        }
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("7 6 4 2 1")) == 1)

    check(applyDamper(listOf(8, 6, 4, 4, 1)) == 1) { "Expected 1 but got ${applyDamper(listOf(8, 6, 4, 4, 1))}" }
    check(part2(listOf("8 6 4 4 1")) == 1) { "Expected 1 but got ${part2(listOf("8 6 4 4 1"))}" }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2) { "Expected 2 but got ${part1(testInput)}" }
    check(part2(testInput) == 4) { "Expected 4 but got ${part2(testInput)}" }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
