fun main() {

    val ops: List<(Long, Long) -> Long> = listOf(Long::plus, Long::times, { a, b -> "$a$b".toLong() })

    fun eval(result: Long, operands: List<Long>, operations: List<(Long, Long) -> Long>, acc: Long = 0): Boolean {
        val head = operands.first()
        val tail = operands.drop(1)
        return operations.any {
            when {
                tail.isEmpty() -> it(acc, head) == result
                else -> eval(result, tail, operations, it(acc, head))
            }
        }
    }

    fun part1(input: List<String>): Long {

        val calibrations = input.map { it.split(": ") }
            .map { cal -> cal[0].toLong() to cal[1].split(" ").map { it.toLong() } }

        return calibrations.filter { (value, operands) -> eval(value, operands, ops.subList(0,2)) }.sumOf { (value, _) -> value }
    }

    fun part2(input: List<String>): Long {
        val calibrations = input.map { it.split(": ") }
            .map { cal -> cal[0].toLong() to cal[1].split(" ").map { it.toLong() } }

        return calibrations.filter { (value, operands) -> eval(value, operands, ops) }.sumOf { (value, _) -> value }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L) { "Expected 3749 but got ${part1(testInput)}" }
//    check(part2(testInput) == 6) { "Expected 6 but got ${part2(testInput)}" }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
