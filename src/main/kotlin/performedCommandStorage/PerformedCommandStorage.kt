package performedCommandStorage

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
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
    fun readFromJson(path: String, numbers: MutableList<Int>) {
        val input = File(path).readText(Charsets.UTF_8)
        Json.decodeFromString<List<Action>>(input).forEach { it.execute(numbers) }
    }

    fun serialize(path: String) {
        val file = File(path)
        file.writeText(Json { prettyPrint = true }.encodeToString(actions.toList()))
    }
}
