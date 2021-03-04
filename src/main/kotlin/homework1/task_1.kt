package homework1

import java.lang.Integer.parseInt
import java.lang.NumberFormatException

fun getFactorialRec(number: Int): Int {
    return if (number < 1) {
        1
    } else {
        number * getFactorialRec(number - 1)
    }
}

fun getFactorialIterative(number: Int): Int {
    var result = 1
    for (i in 1..number) result *= i
    return result
}

fun getInputNumber(): Int? {
    println("Enter a number: ")
    val input = readLine()
    return try {
        parseInt(input)
    } catch (e: NumberFormatException) {
        null
    }
}

fun main() {
    val number: Int = getInputNumber() ?: -1
    if (number > -1) {
        println(getFactorialRec(number))
        println(getFactorialIterative(number))
    }
}
