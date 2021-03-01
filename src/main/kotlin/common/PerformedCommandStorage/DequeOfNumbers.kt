package common.PerformedCommandStorage

class DequeOfNumbers(private val numbers: ArrayDeque<Int>) {
    fun insertBack(value: Int) = numbers.addFirst(value)
    fun push(value: Int) = numbers.addLast(value)
    fun rearrange(to: Int, from: Int) {
        val fromValue = numbers.asReversed()[from]
        numbers.asReversed()[from] = numbers.asReversed()[to]
        numbers.asReversed()[to] = fromValue
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
        numbers.asReversed().forEach { number -> println(number) }
    }
}