package homework1

fun getStringInput(inputSuggestion: String): String {
    println(inputSuggestion)
    return readLine().toString()
}

fun countMatches(text: String, substring: String): Int {
    var index = 0
    var count = 0
    while (true) {
        index = text.indexOf(substring, index)
        if (index != -1) {
            count++
            index++
        } else {
            return count
        }
    }
}

fun main() {
    val text = getStringInput("Input text:")
    val substring = getStringInput("Input string to find:")
    val matches = countMatches(text, substring)
    println(matches)
}
