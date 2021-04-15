package homework4

import performedCommandStorage.PerformedCommandStorage
import performedCommandStorage.Push

fun main() {
    val storage = PerformedCommandStorage<String>()
    val list = mutableListOf<String>()
    storage.executeOperation(Push<String>("Ff"), list)
    println(list)
}
