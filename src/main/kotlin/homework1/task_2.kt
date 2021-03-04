package homework1

fun getStringInput(inputSuggestion: String): String {
    println(inputSuggestion)
    return readLine().toString()
}

fun String.countMatches(substring: String): Int {
    var index = 0
    var count = 0
    if (this == substring) {
        count++
        return count
    }
    index = this.indexOf(substring, index)
    while (index != -1 && substring.isNotEmpty()) {
        count++
        index++
        index = this.indexOf(substring, index)
    }
    return count
}

fun main() {
    val text = getStringInput("Input text:")
    val substring = getStringInput("Input string to find:")
    val matches = text.countMatches(substring)
    println(matches)
}
