package homework2

import java.lang.Integer.parseInt
import java.util.InputMismatchException

fun getInputListOfNumbers(): List<Int>? {
    println("Input numbers separated by a space")
    val stringOfNumbers = readLine().toString()
    val buffer = stringOfNumbers.replace(" ", "")
    if (buffer.matches(Regex("[0-9]+"))) {
        return stringOfNumbers.split(" ").map { parseInt(it) }
    }
    return null
}

fun getRightOccurrences(numbers: List<Int>) = numbers.asReversed().distinct().asReversed()

fun main() {
    val numbers = getInputListOfNumbers() ?: throw InputMismatchException("Numeric values were expected")
    getRightOccurrences(numbers).forEach { print("$it ") }
}
