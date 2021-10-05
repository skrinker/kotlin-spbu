package performedCommandStorage

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.modules.SerializersModule
import java.io.File
import java.util.Stack

/**
 * Saves actions, execute and undo them.
 */
class PerformedCommandStorage<T> {
    private var actions: Stack<Action<T>> = Stack()

    /**
     * Executes and saves actions.
     *
     * @param action Action on list of the numbers.
     * @param numbers List of the numbers.
     */
    fun executeOperation(action: Action<T>, numbers: MutableList<T>) {
        actions.add(action)
        action.execute(numbers)
    }

    /**
     * Undo and removes actions.
     *
     * @param numbers List of the numbers.
     */
    fun undoOperation(numbers: MutableList<T>) {
        if (actions.empty()) {
            println("\nStorage empty.")
        } else {
            actions.lastElement().undo(numbers)
            actions.pop()
        }
    }

    companion object IntJson {
        private val json = Json {
            useArrayPolymorphism = true
            serializersModule = SerializersModule {
                polymorphic(Any::class) {
                    subclass(IntAsObjectSerializer)
                }
            }
        }

        fun PerformedCommandStorage<Int>.readFromJson(path: String, numbers: MutableList<Int>) {
            val input = File(path).readText(Charsets.UTF_8)
            json.decodeFromString<List<Action<Int>>>(input).forEach { it.execute(numbers) }
        }

        fun PerformedCommandStorage<Int>.serialize(path: String) {
            val file = File(path)
            file.writeText(json.encodeToString(actions.toList()))
        }
    }
}
