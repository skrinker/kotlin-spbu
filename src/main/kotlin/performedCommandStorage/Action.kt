package performedCommandStorage

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Action.
 */
@Serializable
sealed class Action<T> {
    /**
     * Executes action.*
     * @param numbers List of the numbers.
     */
    abstract fun execute(numbers: MutableList<T>)

    /**
     * Undo action.
     *
     * @param numbers List of the numbers.
     */
    abstract fun undo(numbers: MutableList<T>)
}

/**
 * Adds number at the end of the list.
 *
 * @param value The value to be added at the end.
 */
@Serializable
@SerialName("InsertBack")
class InsertBack<T>(private val value: T) : Action<T>() {
    /**
     * Executes adding number at the end of the list.
     *
     * @param numbers List of the numbers.
     */
    override fun execute(numbers: MutableList<T>) = numbers.add(numbers.size, value)

    /**
     * Undo adding number at the end of the list.
     *
     * @param numbers List of the numbers.
     */
    override fun undo(numbers: MutableList<T>) {
        numbers.removeLast()
    }
}

/**
 * Adds number at the beginning of the list.
 *
 * @param value The value to be added at the beginning.
 */
@Serializable
@SerialName("Push")
class Push<T>(private val value: T) : Action<T>() {
    /**
     * * Executes adding number at the beginning of the list.
     *
     * @param numbers List of the numbers.
     */
    override fun execute(numbers: MutableList<T>) = numbers.add(0, value)

    /**
     * Undo adding number at the beginning of the list.
     *
     * @param numbers List of the numbers.
     */
    override fun undo(numbers: MutableList<T>) {
        numbers.removeFirst()
    }
}

/**
 * Changes element's position.
 */
@Serializable
@SerialName("Rearrange")
class Rearrange<T>(private val to: Int, private val from: Int) : Action<T>() {
    private fun MutableList<T>.containsIndex(index: Int): Boolean = (index >= 0 && index < this.size)

    /**
     * Supports changing element position.
     *
     * @param to New position.
     * @param from Previous position.
     */
    private fun replace(to: Int, from: Int, numbers: MutableList<T>) {
        if (numbers.containsIndex(to) && numbers.containsIndex(from)) {
            val temp = numbers.removeAt(from)
            numbers.add(to, temp)
        }
    }

    /**
     * Executes changing element position.
     *
     * @param numbers List of the numbers.
     */
    override fun execute(numbers: MutableList<T>) = replace(to, from, numbers)

    /**
     * Undo changing element position.
     *
     * @param numbers List of the numbers.
     */
    override fun undo(numbers: MutableList<T>) = replace(to, from, numbers)
}
