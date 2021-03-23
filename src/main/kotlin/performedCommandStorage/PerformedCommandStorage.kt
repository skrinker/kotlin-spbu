package performedCommandStorage

import java.util.Stack

/**
 * Saves actions, execute and undo them.
 */
class PerformedCommandStorage {
    private var actions: Stack<Action> = Stack()

    /**
     * Executes and saves actions.
     *
     * @param action Action on list of the numbers.
     * @param numbers List of the numbers.
     */
    fun executeOperation(action: Action, numbers: MutableList<Int>) {
        actions.add(action)
        action.execute(numbers)
    }

    /**
     * Undo and removes actions.
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
