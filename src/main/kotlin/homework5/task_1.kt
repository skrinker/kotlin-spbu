package homework5

fun convertInput(input: String): MutableList<String> {
    return input.replace("(", "")
        .replace(")", "")
        .split(" ")
        .toMutableList()
}

fun getUserInput(): String {
    println("Input expression in PRN:")
    val input = readLine()
    return input.toString()
}

fun main() {
    val input = getUserInput()
    val expressionRoot = ParseOperandNode().parse(convertInput(input), 0)
    println("Result: " + expressionRoot.calculate())
    println(expressionRoot.toString())
}
