import kotlin.math.abs

fun main() {

    fun getRuleMap(input: List<String>): Map<String, List<String>> =
        input.map { it.split("|") }.map { it[0] to it[1] }.groupBy({ it.first }, { it.second })

    fun isValidUpdate(pages: List<String>, rules: Map<String, List<String>>): Boolean {
        val okList = mutableListOf<String>()

        pages.forEach { p ->
            val pageRules = rules.getOrDefault(p, emptyList())
            if (pageRules.any { okList.contains(it) }) {
                return false
            } else {
                okList.add(p)
            }
        }

        return true
    }

    fun part1(input: List<String>): Int {
        val sectionBreak = input.indexOfFirst { it.isBlank() }
        val rules = getRuleMap(input.take(sectionBreak))
        val updates = input.drop(sectionBreak+1).map { it.split(",") }

        val correctUpdates = updates.filter { isValidUpdate(it, rules) }

        return correctUpdates.sumOf { it[it.size/2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val sectionBreak = input.indexOfFirst { it.isBlank() }
        val rules = getRuleMap(input.take(sectionBreak))
        val updates = input.drop(sectionBreak+1).map { it.split(",") }

        val comparator: Comparator<String> = Comparator { left, right ->
            val leftSuccessors = rules[left]
            val rightSuccessors = rules[right]

            when {
                rightSuccessors != null && left in rightSuccessors -> 1
                leftSuccessors != null && right in leftSuccessors -> -1
                else -> 0
            }
        }

        val incorrectUpdates = updates.filterNot { isValidUpdate(it, rules) }
        val correctedUpdates = incorrectUpdates.map { it.sortedWith(comparator) }
        return correctedUpdates.sumOf { it[it.size/2].toInt() }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143) { "Expected 143 but got ${part1(testInput)}" }
    check(part2(testInput) == 123) { "Expected 123 but got ${part2(testInput)}" }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
