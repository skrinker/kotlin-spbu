package homework5

interface HashFunction<K> {
    fun hash(input: K): Int
}

object SimpleHash : HashFunction<String> {
		private val border: Int = Int.MAX_VALUE
    override fun hash(input: String): Int {
        var result = 0
        input.forEach { result += (it - 'a') % border }
        return result
    }
}

object DefaultHash : HashFunction<String> {
    override fun hash(input: String): Int = input.hashCode()
}
