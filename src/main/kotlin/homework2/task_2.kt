package homework2

import java.lang.Integer.parseInt

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

fun main() {
    val numbers = getInputListOfNumbers() ?: throw Error("Numeric values were expected")
    val uniqueNumbers = mutableListOf<Int>()
    numbers.asReversed().forEach {
        if (!uniqueNumbers.contains(it)) uniqueNumbers.add(it)
    }
    uniqueNumbers.asReversed().forEach { number -> print("$number ") }
}
