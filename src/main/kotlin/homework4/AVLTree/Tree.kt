import homework4.AVLTree.Node

class Tree<K : Comparable<K>, V> : MutableMap<K, V> {
    private var treeSize = 0

    override val size: Int
        get() = treeSize

    private var root: Node<K, V>? = null

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = mutableSetOf<MutableMap.MutableEntry<K, V>>().also { root?.writeInSet(it) }
    override val keys: MutableSet<K>
        get() = entries.map { it.key }.toMutableSet()
    override val values: MutableCollection<V>
        get() = entries.map { it.value }.toMutableList()

    override fun containsKey(key: K) = get(key) != null

    override fun containsValue(value: V) =
        values.contains(value)

    override fun get(key: K) =
        root?.getNodeByKey(key)?.value

    override fun isEmpty() =
        size == 0

    override fun clear() {
        treeSize = 0
        root = null
    }

    override fun put(key: K, value: V): V? =
        get(key).also {
            if (it == null) treeSize++
            root = root?.insert(key, value) ?: Node(key, value)
        }

    override fun remove(key: K): V? =
        get(key).also {
            if (it != null) {
                treeSize--
                root = root?.remove(key)
            }
        }

    override fun putAll(from: Map<out K, V>) {
        from.forEach { (key: K, value: V) -> put(key, value) }
    }
}
