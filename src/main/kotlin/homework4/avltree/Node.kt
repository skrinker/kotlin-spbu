package homework4.avltree

import java.lang.Integer.max

@Suppress("TooManyFunctions")
class Node<K : Comparable<K>, V>(override val key: K, override var value: V) : MutableMap.MutableEntry<K, V> {
    private var left: Node<K, V>? = null
    private var right: Node<K, V>? = null
    private var height: Int = 1
    private val size: Int
        get() = listOfNotNull(left, right).size
    private val unbalancedStateFactor: Int = 2
    override fun setValue(newValue: V): V {
        return newValue.also { this.value = it }
    }
    private val balanceFactor: Int
        get() {
            return (right?.height ?: 0) - (left?.height ?: 0)
        }

    fun getNodeByKey(key: K): Node<K, V>? =
        when {
            key < this.key -> this.left?.getNodeByKey(key)
            key > this.key -> this.right?.getNodeByKey(key)
            else -> this
        }

    private fun updateHeight(): Int {
        val maxHeight = max(left?.height ?: 0, right?.height ?: 0) + 1
        this.height = maxHeight
        return maxHeight
    }

    private fun balance(): Node<K, V>? {
        this.updateHeight()
        return when (this.balanceFactor) {
            unbalancedStateFactor -> {
                if ((this.right?.balanceFactor ?: 0) < 0) {
                    this.right = this.right?.rotateRight()
                }
                this.rotateLeft()
            }
            -unbalancedStateFactor -> {
                if ((this.left?.balanceFactor ?: 0) > 0) {
                    this.left = this.left?.rotateLeft()
                }
                this.rotateRight()
            }
            else -> this
        }
    }

    private fun rotateLeft(): Node<K, V>? {
        val pivot = this.right
        this.right = pivot?.left
        pivot?.left = this
        this.updateHeight()
        pivot?.updateHeight()
        return pivot
    }

    private fun rotateRight(): Node<K, V>? {
        val pivot = this.left
        this.left = pivot?.right
        pivot?.right = this
        this.updateHeight()
        pivot?.updateHeight()
        return pivot
    }

    fun insert(key: K, value: V): Node<K, V>? =
        when {
            key < this.key -> {
                this.left = this.left?.insert(key, value) ?: Node(key, value)
                this.balance()
            }
            key > this.key -> {
                this.right = this.right?.insert(key, value) ?: Node(key, value)
                this.balance()
            }
            else -> {
                this.setValue(value)
                this
            }
        }

    private fun getMaxInSubtree(): Node<K, V>? {
        while (right != null) {
            return right?.getMaxInSubtree()
        }
        return this
    }

    fun remove(key: K): Node<K, V>? =
        when {
            key < this.key -> {
                this.left = this.left?.remove(key)
                this.balance()
            }
            key > this.key -> {
                this.right = this.right?.remove(key)
                this.balance()
            }
            else -> {
                when (this.size) {
                    0 -> {
                        null
                    }
                    1 -> {
                        this.left ?: this.right
                    }
                    else -> {
                        val replacement = this.left?.getMaxInSubtree()
                        this.left = replacement?.let { this.left?.remove(it.key) }
                        replacement?.left = this.left
                        replacement?.right = this.right
                        replacement?.height = this.height
                        replacement?.balance()
                    }
                }
            }
        }
    fun writeInSet(set: MutableSet<MutableMap.MutableEntry<K, V>>) {
        this.left?.writeInSet(set)
        set.add(this)
        this.right?.writeInSet(set)
    }
}
