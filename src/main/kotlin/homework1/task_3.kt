package homework1

import common.PerformedCommandStorage.*

fun main() {
    val commandStorage = PerformedCommandStorage()
    val numbersArray: ArrayDeque<Int> = ArrayDeque()
    val numbers = DequeOfNumbers(numbersArray)
    commandStorage.executeOperation(Push(1, numbers))
    commandStorage.executeOperation(Push(2, numbers))
    commandStorage.executeOperation(Push(3, numbers))
    commandStorage.executeOperation(InsertBack(4, numbers))
    commandStorage.executeOperation(Rearrange(0, 1, numbers))
    commandStorage.undoOperation()
    commandStorage.undoOperation()
    commandStorage.undoOperation()
    commandStorage.undoOperation()

    numbers.print()
}

