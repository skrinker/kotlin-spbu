package homework5

interface HashFunction<K> {
    fun hash(input: K): Int
}

class SimpleHash(private val border: Int) : HashFunction<String> {
    override fun hash(input: String): Int {
        var result = 0
        input.forEach { result += (it - 'a') % border }
        return result
    }
}

class DefaultHash : HashFunction<String> {
    override fun hash(input: String): Int = input.hashCode()
}
