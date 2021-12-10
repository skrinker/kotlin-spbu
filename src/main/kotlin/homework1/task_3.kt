package homework1

import performedCommandStorage.InsertBack
import performedCommandStorage.PerformedCommandStorage
import performedCommandStorage.Push
import performedCommandStorage.Rearrange

const val NUMBER0 = 0
const val NUMBER1 = 1
const val NUMBER2 = 2

private fun MutableList<Int>.print() {
    if (this.isEmpty()) {
        print("Empty.")
    } else {
        this.forEach { print("$it ") }
    }
    println()
}

fun main() {
    val commandStorage = PerformedCommandStorage<Int>()
    val numbers = mutableListOf<Int>()

    numbers.print()

    commandStorage.executeOperation(Push(NUMBER0), numbers)
    numbers.print()

    commandStorage.executeOperation(Push(NUMBER1), numbers)
    numbers.print()

    commandStorage.executeOperation(Push(NUMBER2), numbers)
    numbers.print()

    commandStorage.undoOperation(numbers)
    numbers.print()

    commandStorage.undoOperation(numbers)
    numbers.print()

    commandStorage.executeOperation(InsertBack(NUMBER1), numbers)
    numbers.print()

    commandStorage.executeOperation(InsertBack(NUMBER2), numbers)
    numbers.print()

    commandStorage.undoOperation(numbers)
    numbers.print()

    commandStorage.undoOperation(numbers)
    numbers.print()

    commandStorage.executeOperation(Rearrange(NUMBER0, NUMBER1), numbers)
    numbers.print()

    commandStorage.executeOperation(InsertBack(NUMBER1), numbers)
    numbers.print()

    commandStorage.executeOperation(Rearrange(NUMBER0, NUMBER1), numbers)
    numbers.print()

    commandStorage.undoOperation(numbers)
    numbers.print()

    commandStorage.undoOperation(numbers)
    numbers.print()

    commandStorage.undoOperation(numbers)
    numbers.print()

    commandStorage.undoOperation(numbers)
    numbers.print()

    commandStorage.undoOperation(numbers)
}
