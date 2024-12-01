import kotlin.math.abs

fun main() {
    fun extractListsFrom(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        val delimiter = "   "

        input.forEach { line ->
            leftList.add(line.substringBefore(delimiter).toInt())
            rightList.add(line.substringAfter(delimiter).toInt())
        }

        leftList.sort()
        rightList.sort()
        return Pair(leftList, rightList)
    }

    fun part1(input: List<String>): Int {
        val (leftList, rightList) = extractListsFrom(input)
        val pairs = leftList.zip(rightList)
        val distance = pairs.stream().map { abs(it.first - it.second) }.toList().sum()
        return distance
    }

    fun part2(input: List<String>): Int {
        val (leftList, rightList) = extractListsFrom(input)

        var similarityScore = 0
        leftList.forEach { leftItem ->
            val similarity = leftItem * rightList.filter { it == leftItem }.size
            similarityScore += similarity
        }
        return similarityScore
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("3   4")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
