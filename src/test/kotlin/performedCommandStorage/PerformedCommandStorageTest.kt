package performedCommandStorage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.nio.file.Path

internal class PerformedCommandStorageTest {

    @Test
    fun testExecute() {
        val commandStorage = PerformedCommandStorage()
        val numbers = mutableListOf<Int>()
        commandStorage.executeOperation(Push(0), numbers)
        commandStorage.executeOperation(InsertBack(1), numbers)
        commandStorage.executeOperation(Rearrange(0, 1), numbers)
        assertEquals(mutableListOf(1, 0), numbers)
    }

    @Test
    fun testUndo() {
        val commandStorage = PerformedCommandStorage()
        val numbers = mutableListOf<Int>()
        commandStorage.executeOperation(Push(0), numbers)
        commandStorage.executeOperation(InsertBack(1), numbers)
        commandStorage.executeOperation(Rearrange(0, 1), numbers)
        commandStorage.undoOperation(numbers)
        commandStorage.undoOperation(numbers)
        commandStorage.undoOperation(numbers)
        assertEquals(mutableListOf<Int>(), numbers)
    }

    @Test
    fun testUndoOnEmptyStorage() {
        val commandStorage = PerformedCommandStorage()
        val numbers = mutableListOf<Int>()
        commandStorage.undoOperation(numbers)
        assertEquals(mutableListOf<Int>(), numbers)
    }

    @Test
    fun readFromJson() {
        val commandStorage = PerformedCommandStorage()
        val numbers = mutableListOf<Int>()
        commandStorage.readFromJson(javaClass.getResource("/homework2/testFile.json").path, numbers)
        assertEquals(mutableListOf(0, 2, 1, 1), numbers)
    }

    @Test
    fun serialize(@TempDir tempDir: Path) {
        val commandStorage = PerformedCommandStorage()
        val numbers = mutableListOf<Int>()
        val temp = tempDir.resolve("testFile.json").toString()
        commandStorage.executeOperation(InsertBack(0), numbers)
        commandStorage.executeOperation(InsertBack(1), numbers)
        commandStorage.executeOperation(Push(2), numbers)
        commandStorage.executeOperation(Push(1), numbers)
        commandStorage.executeOperation(Rearrange(0, 2), numbers)
        commandStorage.executeOperation(Rearrange(1, 2), numbers)
        commandStorage.serialize(temp, false)
        assertEquals(javaClass.getResource("/homework2/testFile.json").readText(), File(temp).readText())
    }
}
