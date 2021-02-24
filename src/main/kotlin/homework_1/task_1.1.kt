import java.util.Scanner

fun factorialRec(number: Int): Int =  if (number < 1) 1 else number * factorialRec(number - 1);

fun factorialLoop(number: Int) : Int {
    var result = 1;
    for (i in 1..number) result *= i
    return result
}

fun main() {
    val reader = Scanner(System.`in`)
    print("Enter a number: ")
    var number:Int = reader.nextInt()

    println(factorialRec(number))
    println(factorialLoop(number))
}
