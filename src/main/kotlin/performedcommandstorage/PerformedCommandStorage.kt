package performedcommandstorage
import java.util.Stack

class PerformedCommandStorage {
    private var actions: Stack<Action> = Stack()

    fun executeOperation(action: Action) {
        actions.add(action)
        action.execute()
    }

    fun undoOperation() {
        if (actions.empty()) {
            println("Storage empty")
        } else {
            actions.lastElement().undo()
            actions.pop()
        }
    }

    fun getHistory() = actions
}
