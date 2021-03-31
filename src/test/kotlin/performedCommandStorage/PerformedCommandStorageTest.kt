package performedCommandStorage

import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals

internal class PerformedCommandStorageTest {
    private val commandStorage = PerformedCommandStorage()
    private val numbers = mutableListOf<Int>()
    private val resources = "src/test/resources/homework2"

    @Test
    fun testPerformedCommandStorage() {
        commandStorage.executeOperation(Push(0), numbers)
        commandStorage.executeOperation(Push(1), numbers)
        commandStorage.executeOperation(InsertBack(2), numbers)
        commandStorage.executeOperation(InsertBack(3), numbers)
        commandStorage.executeOperation(Rearrange(0, 1), numbers)
        commandStorage.executeOperation(Rearrange(0, 5), numbers)
        assertEquals(mutableListOf(0, 1, 2, 3), numbers)
        commandStorage.undoOperation(numbers)
        assertEquals(mutableListOf(0, 1, 2, 3), numbers)
        commandStorage.undoOperation(numbers)
        assertEquals(mutableListOf(1, 0, 2, 3), numbers)
        commandStorage.undoOperation(numbers)
        assertEquals(mutableListOf(1, 0, 2), numbers)
        commandStorage.undoOperation(numbers)
        assertEquals(mutableListOf(1, 0), numbers)
        commandStorage.undoOperation(numbers)
        assertEquals(mutableListOf(0), numbers)
    }

    @Test
    fun readFromJson() {
        commandStorage.readFromJson("$resources/inputTest.json", numbers)
        assertEquals(mutableListOf(0, 2, 1, 1), numbers)
    }

    @Test
    fun serialize() {
        commandStorage.executeOperation(InsertBack(0), numbers)
        commandStorage.executeOperation(InsertBack(1), numbers)
        commandStorage.executeOperation(Push(2), numbers)
        commandStorage.executeOperation(Push(1), numbers)
        commandStorage.executeOperation(Rearrange(0, 2), numbers)
        commandStorage.executeOperation(Rearrange(1, 2), numbers)
        commandStorage.serialize("$resources/testFile.json", false)
        assertEquals(File("$resources/testFile.json").readText(), File("$resources/outputTest.json").readText())
    }
}
