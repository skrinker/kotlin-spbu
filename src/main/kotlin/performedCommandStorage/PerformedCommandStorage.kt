package performedCommandStorage

import java.util.Stack

class PerformedCommandStorage {
    private var actions: Stack<Action> = Stack()

    fun executeOperation(action: Action, numbers: MutableList<Int>) {
        actions.add(action)
        action.execute(numbers)
    }

    fun undoOperation(numbers: MutableList<Int>) {
        if (actions.empty()) {
            println("\nStorage empty.")
        } else {
            actions.lastElement().undo(numbers)
            actions.pop()
        }
    }
}
