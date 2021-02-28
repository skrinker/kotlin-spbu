package homework1

import java.lang.Integer.parseInt
import java.lang.NumberFormatException

fun factorialRec(number: Int): Int {
    if (number < 1) return 1
    else return number * factorialRec(number - 1)
}

fun factorialLoop(number: Int): Int {
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
        println(factorialRec(number))
        println(factorialLoop(number))
    }
}
