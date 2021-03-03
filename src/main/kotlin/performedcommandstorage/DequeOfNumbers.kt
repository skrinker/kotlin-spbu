package performedcommandstorage

class DequeOfNumbers(private val numbers: ArrayDeque<Int>) {
    fun insertBack(value: Int) = numbers.addFirst(value)
    fun push(value: Int) = numbers.addLast(value)
    fun rearrange(to: Int, from: Int) {
        if (containsIndex(to) && containsIndex(from)) {
            val fromValue = numbers.asReversed()[from]
            numbers.asReversed()[from] = numbers.asReversed()[to]
            numbers.asReversed()[to] = fromValue
        }
    }
    fun deleteLast() {
        numbers.removeFirst()
        return
    }
    fun pop() {
        numbers.removeLast()
        return
    }
    fun print() {
        println()
        if (numbers.isEmpty()) {
            print("Empty.")
        } else {
            numbers.asReversed().forEach { number -> print("$number ") }
        }
    }

    private fun containsIndex(index: Int): Boolean {
        return (index >= 0 && index < numbers.size)
    }
}
