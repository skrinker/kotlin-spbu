package homework1
import performedcommandstorage.DequeOfNumbers
import performedcommandstorage.InsertBack
import performedcommandstorage.PerformedCommandStorage
import performedcommandstorage.Push
import performedcommandstorage.Rearrange

const val NUMBER0 = 0
const val NUMBER1 = 1
const val NUMBER2 = 2

fun main() {
    val commandStorage = PerformedCommandStorage()
    val numbersArray: ArrayDeque<Int> = ArrayDeque()
    val numbers = DequeOfNumbers(numbersArray)
    numbers.print()

    commandStorage.executeOperation(Push(NUMBER0, numbers))
    numbers.print()

    commandStorage.executeOperation(Push(NUMBER1, numbers))
    numbers.print()

    commandStorage.executeOperation(Push(NUMBER2, numbers))
    numbers.print()

    commandStorage.undoOperation()
    numbers.print()

    commandStorage.undoOperation()
    numbers.print()

    commandStorage.executeOperation(InsertBack(NUMBER1, numbers))
    numbers.print()

    commandStorage.executeOperation(InsertBack(NUMBER2, numbers))
    numbers.print()

    commandStorage.undoOperation()
    numbers.print()

    commandStorage.undoOperation()
    numbers.print()

    commandStorage.executeOperation(Rearrange(NUMBER0, NUMBER1, numbers))
    numbers.print()

    commandStorage.executeOperation(InsertBack(NUMBER1, numbers))
    numbers.print()

    commandStorage.executeOperation(Rearrange(NUMBER0, NUMBER1, numbers))
    numbers.print()

    commandStorage.undoOperation()
    numbers.print()

    commandStorage.undoOperation()
    numbers.print()

    commandStorage.undoOperation()
    numbers.print()

    commandStorage.undoOperation()
    numbers.print()

    commandStorage.undoOperation()
}
