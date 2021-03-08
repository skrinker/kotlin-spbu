package performedCommandStorage

import java.util.Stack

/**
 * This class can save actions, execute and undo them.
 */
class PerformedCommandStorage {
    private var actions: Stack<Action> = Stack()

    /**
     * This function can execute and save actions.
     *
     * @param action Action on list of the numbers.
     * @param numbers List of the numbers.
     */
    fun executeOperation(action: Action, numbers: MutableList<Int>) {
        actions.add(action)
        action.execute(numbers)
    }

    /**
     * This function can undo and remove actions.
     *
     * @param numbers List of the numbers.
     */
    fun undoOperation(numbers: MutableList<Int>) {
        if (actions.empty()) {
            println("\nStorage empty.")
        } else {
            actions.lastElement().undo(numbers)
            actions.pop()
        }
    }
}
