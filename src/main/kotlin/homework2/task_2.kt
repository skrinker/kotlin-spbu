package homework2

import java.lang.Integer.parseInt
import java.util.InputMismatchException

fun getInputListOfNumbers(): MutableList<Int>? {
    println("Input numbers separated by a space")
    val stringOfNumbers = readLine().toString()
    val buffer = stringOfNumbers.replace(" ", "")
    if (buffer.matches(Regex("[0-9]+"))) {
        val numbers = mutableListOf<Int>()
        stringOfNumbers.split(" ").forEach { numbers.add(parseInt(it)) }
        return numbers
    }
    return null
}

fun getRightOccurrences(numbers: MutableList<Int>) = numbers.asReversed().distinct().asReversed()

fun main() {
    val numbers = getInputListOfNumbers() ?: throw InputMismatchException("Numeric values were expected")
    getRightOccurrences(numbers).forEach { number -> print("$number ") }
}
