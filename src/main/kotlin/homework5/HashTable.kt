package homework5

class HashTable<K, V>(private var hashFunction: HashFunction<K>) {
    companion object {
        const val MAX_LOAD_FACTOR = 0.7
    }

    data class HashElement<K, V>(val key: K, var value: V)

    private var size = 0
    private var bucketCount = 1
    private val loadFactor: Double
        get() {
            return size / bucketCount.toDouble()
        }
    private var buckets = Array(bucketCount) { mutableListOf<HashElement<K, V>>() }

    fun add(key: K, value: V) {
        val hash = hashFunction.hash(key) % bucketCount
        val element = buckets[hash].find { key == it.key }
        if (element != null) {
            if (value != element.value) {
                element.value = value
            }
        } else {
            buckets[hash].add(HashElement(key, value))
            size++
        }
        if (loadFactor >= MAX_LOAD_FACTOR) {
            this.expand()
        }
    }

    private fun expand() {
        bucketCount *= 2
        recreateHashTable(bucketCount)
    }

    private fun recreateHashTable(newSize: Int = bucketCount) {
        val newBuckets = Array(newSize) { mutableListOf<HashElement<K, V>>() }
        buckets.forEach { list ->
            list.forEach { hashElement ->
                val hash = hashFunction.hash(hashElement.key) % (newSize)
                newBuckets[hash].add(hashElement)
            }
        }
        buckets = newBuckets
    }

    operator fun get(key: K): V? {
        val hash = hashFunction.hash(key) % bucketCount
        return buckets[hash].find { key == it.key }?.value
    }

    fun remove(key: K): Boolean {
        val hash = hashFunction.hash(key) % (bucketCount)
        val element = buckets[hash].find { key == it.key }
        if (element != null) {
            buckets[hash].remove(element)
            --size
        }
        return element != null
    }

    fun contains(key: K): Boolean {
        val hash = hashFunction.hash(key) % bucketCount
        return buckets[hash].find { key == it.key } != null
    }

    fun changeHashFunction(newHashFunction: HashFunction<K>) {
        hashFunction = newHashFunction
        recreateHashTable()
    }

    override fun toString(): String {
        var numberOfConflicts = 0
        var maximumBucketLength = 0
        buckets.forEach {
            if (it.size > 1)
                ++numberOfConflicts
            if (it.size > maximumBucketLength)
                maximumBucketLength = it.size
        }
        return "Size: " + this.size + "\n" +
            "Buckets: " + this.bucketCount + "\n" +
            "Load factor: " + this.loadFactor + "\n" +
            "Conflicts number" + numberOfConflicts + "\n" +
            "Maximum bucket size" + maximumBucketLength + "\n"
    }
}
