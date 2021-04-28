package homework5

fun getSuggestion(action: Action<String, String>, index: Int) {
    val nameList = action.javaClass.name.split(Regex("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")).drop(1)
    print("$index -")
    nameList.forEach { print(" ${it.toLowerCase()}") }
    println()
}

fun printInstruction(actions: List<Action<String, String>>) {
    actions.forEachIndexed { index, value -> getSuggestion(value, index) }
}

fun getUserInput(suggestion: String): String {
    println("Input $suggestion: ")
    return readLine() ?: ""
}

fun main() {
    val actions = listOf(Add, Remove, Find, PrintStatistics, ChangeHashFunction, ImportFromFile)
    printInstruction(actions)
    println("or -1 to exit")
    val hashTable = HashTable<String, String>(DefaultHash)
    while (true) {
        val number = getUserInput("number of action").toInt()
        if (number != -1) actions[number].execute(hashTable)
        else break
    }
}
