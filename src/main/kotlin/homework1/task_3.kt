package homework1
import performedcommandstorage.DequeOfNumbers
import performedcommandstorage.PerformedCommandStorage
import performedcommandstorage.Push

fun main() {
    val commandStorage = PerformedCommandStorage()
    val numbersArray: ArrayDeque<Int> = ArrayDeque()
    val numbers = DequeOfNumbers(numbersArray)
    commandStorage.executeOperation(Push(1, numbers))
    commandStorage.executeOperation(Push(2, numbers))
    commandStorage.executeOperation(Push(3, numbers))
    commandStorage.undoOperation()
    commandStorage.undoOperation()
    commandStorage.undoOperation()
    commandStorage.undoOperation()

    numbers.print()
}
