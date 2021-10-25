package test1

class Node<T>(val value: T, var next: Node<T>? = null, var previous: Node<T>? = null)

class DoubleLinkedList<T> {
    private var head: Node<T>? = null
    private var size: Int = 0

    fun isEmpty(): Boolean {
        return (size == 0)
    }

    fun add(data: T) {
        if (head == null) {
            head = Node(data)
        } else {
            var current = head
            while (current?.next != null) {
                current = current.next
            }
            current?.next = Node(data, previous = current)
        }
        size++
    }

    fun add(data: T, position: Int) {
        if (position > size || position < 0) throw IndexOutOfBoundsException()
        if (position == 0) {
            val prevHead = head
            head = Node(data)
            head!!.next = prevHead
            prevHead!!.previous = head
        } else {
            var current = head
            for (i in 0 until position) {
                current = current?.next
            }
            current?.previous?.next = Node(data, current, current?.previous)
        }
        size++
    }

    fun remove(position: Int) {
        require(size > 0) {
            throw IllegalArgumentException()
        }
        if (position >= size || position < 0) throw IndexOutOfBoundsException()
        if (position == 0) {
            head = head?.next
            head?.previous = null
        } else {
            var current = head
            for (i in 0 until position) {
                current = current?.next
            }
            current?.previous = current?.next
        }
        size--
    }

    fun get(): Node<T>? {
        return head
    }

    fun get(position: Int): Node<T>? {
        if (position > size || position < 0) throw IndexOutOfBoundsException()
        var current = head
        for (i in 0 until position) {
            current = current?.next
        }
        return current
    }

    override fun toString(): String {
        val output = StringBuilder()
        if (head != null) {
            output.append(head!!.value)
            var current = head
            while (current?.next != null) {
                current = current.next
                output.append(" ")
                output.append(current?.value)
            }
        }
        return output.toString()
    }
}
