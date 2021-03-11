package performedCommandStorage

/**
 * Interface of action.
 */
interface Action {
    /**
     * Function that execute action.
     *
     * @param numbers List of the numbers.
     */
    fun execute(numbers: MutableList<Int>)

    /**
     * Funtion that undo action.
     *
     * @param numbers List of the numbers.
     */
    fun undo(numbers: MutableList<Int>)
}

/**
 * This class supports adding number at the end of the list.
 *
 * @param value The value to be added at the end.
 */
class InsertBack(private val value: Int) : Action {
    /**
     * This function execute adding number at the end of the list.
     *
     * @param numbers List of the numbers.
     */
    override fun execute(numbers: MutableList<Int>) = numbers.add(numbers.size, value)
    /**
     * This function undo adding number at the end of the list.
     *
     * @param numbers List of the numbers.
     */
interface Action {
    fun execute(numbers: MutableList<Int>)
    fun undo(numbers: MutableList<Int>)
}

class InsertBack(private val value: Int) : Action {
    override fun execute(numbers: MutableList<Int>) = numbers.add(numbers.size, value)
    override fun undo(numbers: MutableList<Int>) {
        numbers.removeLast()
    }
}

/**
 * This class supports adding number at the beginning of the list.
 *
 * @param value The value to be added at the beginning.
 */
class Push(private val value: Int) : Action {
    /**
     * This function execute adding number at the beginning of the list.
     *
     * @param numbers List of the numbers.
     */
    override fun execute(numbers: MutableList<Int>) = numbers.add(0, value)
    /**
     * This function undo adding number at the beginning of the list.
     *
     * @param numbers List of the numbers.
     */
class Push(private val value: Int) : Action {
    override fun execute(numbers: MutableList<Int>) = numbers.add(0, value)
    override fun undo(numbers: MutableList<Int>) {
        numbers.removeFirst()
    }
}

/**
 * This class supports swapping two elements in a list
 *
 * @param to Position of the first element.
 * @param from Position of the second element.
 */
class Rearrange(private val to: Int, private val from: Int) : Action {
    /**
     * This function checks for the current index within the array.
     *
     * @param index Current index.
     */
    private fun MutableList<Int>.containsIndex(index: Int): Boolean = (index >= 0 && index < this.size)
    /**
     * This function swap two elements in a list.
     *
     * @param to Position of the first element.
     * @param from Position of the second element.
     */
    private fun replace(to: Int, from: Int, numbers: MutableList<Int>) {
        if (numbers.containsIndex(to) && numbers.containsIndex(from)) {
            val fromValue = numbers[from]
            numbers[from] = numbers[to]
            numbers[to] = fromValue
        }
    }
    /**
     * This function execute swapping two elements in a list.
     *
     * @param numbers List of the numbers.
     */
    override fun execute(numbers: MutableList<Int>) = replace(to, from, numbers)
    /**
     * This function undo swapping two elements in a list.
     *
     * @param numbers List of the numbers.
     */

class Rearrange(private val to: Int, private val from: Int) : Action {
    private fun MutableList<Int>.containsIndex(index: Int): Boolean = (index >= 0 && index < this.size)
    private fun replace(to: Int, from: Int, numbers: MutableList<Int>) {
        if (numbers.containsIndex(to) && numbers.containsIndex(from)) {
            val temp = numbers.removeAt(from)
            numbers.add(to, temp)
        }
    }

    override fun execute(numbers: MutableList<Int>) = replace(to, from, numbers)
    override fun undo(numbers: MutableList<Int>) = replace(to, from, numbers)
}
