package homework2

import performedCommandStorage.InsertBack
import performedCommandStorage.PerformedCommandStorage
import performedCommandStorage.Push
import performedCommandStorage.Rearrange

const val NUMBER0 = 0
const val NUMBER1 = 1
const val NUMBER2 = 2
const val PATH = "input.json"

private fun MutableList<Int>.print() {
    if (this.isEmpty()) {
        print("Empty.")
    } else {
        this.forEach { print("$it ") }
    }
    println()
}

fun main() {
    val commandStorage = PerformedCommandStorage()
    val numbers = mutableListOf<Int>()
    commandStorage.executeOperation(InsertBack(NUMBER0), numbers)
    commandStorage.executeOperation(InsertBack(NUMBER1), numbers)
    commandStorage.executeOperation(Push(NUMBER2), numbers)
    commandStorage.executeOperation(Push(NUMBER1), numbers)
    commandStorage.executeOperation(Rearrange(0, 2), numbers)
    commandStorage.executeOperation(Rearrange(1, 2), numbers)
    commandStorage.serialize("output.json")
    numbers.print()
    commandStorage.readFromJson(PATH, numbers)
    numbers.print()
}
