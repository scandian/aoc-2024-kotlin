fun main() {

    val mulRegex = """mul\(\d+,\d+\)""".toRegex()
    val instRegex = """mul\(\d+,\d+\)|do\(\)|don't\(\)""".toRegex()

    fun getMultiplications(input: String) = mulRegex.findAll(input).map { it.value }
    fun getInstructions(input: String) = instRegex.findAll(input).map { it.value }


    fun doMultiplication(it: String): Long {
        val a = it.substringBefore(",").substringAfter("(").toLong()
        val b = it.substringAfter(",").substringBefore(")").toLong()
        return a * b
    }

    fun part1(input: List<String>): Long {
        val multiplications = input.flatMap(::getMultiplications)
        return multiplications.sumOf(::doMultiplication)
    }

    fun part2(input: List<String>): Long {
        val instructions = input.flatMap(::getInstructions)
        var active = true
        return instructions.fold(0L) { acc, instruction ->
            when (instruction) {
                "do()" -> {
                    active = true
                    acc
                }
                "don't()" -> {
                    active = false
                    acc
                }
                else -> {
                    if (active) {
                        acc + doMultiplication(instruction)
                    }
                    else {
                        acc
                    }
                }
            }
        }
    }

    val check1 = """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"""
    val answer1 = listOf("mul(2,4)", "mul(5,5)", "mul(11,8)", "mul(8,5)")
    check(getMultiplications(check1).toList() == answer1)
    { "Expected $answer1 but got ${getMultiplications(check1)}"}

    val check2 = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"""
    val sections2 = listOf("xmul(2,4)&mul[3,7]!^", "?mul(8,5))")


    check(sections2.flatMap { getMultiplications(it) } == listOf("mul(2,4)", "mul(8,5)"))

    check(part1(listOf(check1)) == 161L)
    check(part2(listOf(check2)) == 48L) { "Expected 48 but got ${part2(listOf(check1))}" }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
