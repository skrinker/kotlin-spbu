package performedCommandStorage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Action.
 */
@Serializable
sealed class Action<T> {
     /**
     * Executes action.
     *
     * @param values List of the values.
     */
    abstract fun execute(values: MutableList<T>)
  /**
     * Undo action.
     *
     * @param values List of the values.
     */
    abstract fun undo(values: MutableList<T>)
}

/**
 * Adds number at the end of the list.
 *
 * @param value The value to be added at the end.
 */
@Serializable
@SerialName("InsertBack")
class InsertBack<T> (private val value: T) : Action<T>() {
    /**
     * Executes adding number at the end of the list.
     *
     * @param values List of the values.
     */
    override fun execute(values: MutableList<T>) = values.add(values.size, value)
    /**
     * Undo adding number at the end of the list.
     *
     * @param values List of the values.
     */
    override fun undo(values: MutableList<T>) {
        values.removeLast()
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
     * Executes adding number at the beginning of the list.
     *
     * @param values List of the values.
     */
    override fun execute(values: MutableList<T>) = values.add(0, value)
    /**
     * Undo adding number at the beginning of the list.
     *
     * @param values List of the values.
     */
    override fun undo(values: MutableList<T>) {
        values.removeFirst()
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
    private fun replace(to: Int, from: Int, values: MutableList<T>) {
        if (values.containsIndex(to) && values.containsIndex(from)) {
            val temp = values.removeAt(from)
            values.add(to, temp)
        }
    }
    /**
     * Executes changing element position.
     *
     * @param values List of the values.
     */
    override fun execute(values: MutableList<T>) = replace(to, from, values)
    /**
     * Undo changing element position.
     *
     * @param values List of the values.
     */
    override fun undo(values: MutableList<T>) = replace(from, to, values)
}
