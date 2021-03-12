package performedCommandStorage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Action {
    abstract fun execute(numbers: MutableList<Int>)
    abstract fun undo(numbers: MutableList<Int>)
}

@Serializable
@SerialName("InsertBack")
class InsertBack(private val value: Int) : Action() {
    override fun execute(numbers: MutableList<Int>) = numbers.add(numbers.size, value)
    override fun undo(numbers: MutableList<Int>) {
        numbers.removeLast()
    }
}

@Serializable
@SerialName("Push")
class Push(private val value: Int) : Action() {
    override fun execute(numbers: MutableList<Int>) = numbers.add(0, value)
    override fun undo(numbers: MutableList<Int>) {
        numbers.removeFirst()
    }
}

@Serializable
@SerialName("Rearrange")
class Rearrange(private val to: Int, private val from: Int) : Action() {
    private fun MutableList<Int>.containsIndex(index: Int): Boolean = (index >= 0 && index < this.size)
    private fun replace(to: Int, from: Int, numbers: MutableList<Int>) {
        if (numbers.containsIndex(to) && numbers.containsIndex(from)) {
            val fromValue = numbers[from]
            numbers[from] = numbers[to]
            numbers[to] = fromValue
        }
    }

    override fun execute(numbers: MutableList<Int>) = replace(to, from, numbers)
    override fun undo(numbers: MutableList<Int>) = replace(to, from, numbers)
}
