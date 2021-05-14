package homework5

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

interface Action<K, T> {
    fun execute(hashtable: HashTable<K, T>)
    fun getUserInput(suggestion: String): String {
        println("Input $suggestion:")
        return readLine() ?: ""
    }
}

object Add : Action<String, String> {
    override fun execute(hashtable: HashTable<String, String>) {
        var input = getUserInput("value")
        var key = getUserInput("key")
        hashtable.add(key, input)
    }
}

object Remove : Action<String, String> {
    override fun execute(hashtable: HashTable<String, String>) {
        var key = getUserInput("key")
        hashtable.remove(key) ?: println("No such key")
    }
}

object Find : Action<String, String> {
    override fun execute(hashtable: HashTable<String, String>) {
        var key = getUserInput("key")
        val element = hashtable[key]
        if (element != null) println(element.value)
        else println("No such key")
    }
}

object PrintStatistics : Action<String, String> {
    override fun execute(hashtable: HashTable<String, String>) = print(hashtable.getStatistics())
}

object ChangeHashFunction : Action<String, String> {
    private val hashFunctions = listOf(DefaultHash, SimpleHash)

    private fun getSuggestion(function: HashFunction<String>, index: Int) {
        val nameList = function.javaClass.name
            .split(Regex("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")).drop(1)
        print("$index -")
        nameList.forEach { print(" ${it.toLowerCase()}") }
        println()
    }

    private fun getInstruction(functions: List<HashFunction<String>>) {
        functions.forEachIndexed() { index, value -> getSuggestion(value, index) }
    }

    override fun execute(hashtable: HashTable<String, String>) {
        println("Pick hash function:")
        getInstruction(hashFunctions)
        val index = getUserInput("value").toInt()
        hashtable.changeHashFunction(hashFunctions[index])
    }
}

object ImportFromFile : Action<String, String> {
    override fun execute(hashtable: HashTable<String, String>) {
        val path = getUserInput("path to file")
        val input = File(path).readText()
        val fileInputs = Json.decodeFromString<HashMap<String, String>>(input)
        fileInputs.forEach { hashtable.add(it.key, it.value) }
    }
}
