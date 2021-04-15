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
    println("Result: " + ParseOperandNode().parse(convertInput(input)).calculate())
}
