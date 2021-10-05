package homework2

import performedCommandStorage.InsertBack
import performedCommandStorage.PerformedCommandStorage
import performedCommandStorage.PerformedCommandStorage.IntJson.readFromJson
import performedCommandStorage.PerformedCommandStorage.IntJson.serialize
import performedCommandStorage.Push
import performedCommandStorage.Rearrange

@Suppress("MagicNumber")

private fun MutableList<Int>.print() {
    if (this.isEmpty()) {
        print("Empty.")
    } else {
        this.forEach { print("$it ") }
    }
    println()
}

fun main() {
    val resources = "src/main/resources/homework2"
    val commandStorage = PerformedCommandStorage<Int>()
    val numbers = mutableListOf<Int>()
    commandStorage.executeOperation(InsertBack(0), numbers)
    commandStorage.executeOperation(InsertBack(1), numbers)
    commandStorage.executeOperation(Push(2), numbers)
    commandStorage.executeOperation(Push(1), numbers)
    commandStorage.executeOperation(Rearrange(0, 2), numbers)
    commandStorage.executeOperation(Rearrange(1, 2), numbers)
    commandStorage.serialize("$resources/output.json")
    numbers.print()
    commandStorage.readFromJson("$resources/input.json", numbers)
    numbers.print()
}
