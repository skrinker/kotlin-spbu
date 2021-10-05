package performedCommandStorage
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.io.TempDir
import performedCommandStorage.PerformedCommandStorage.IntJson.readFromJson
import performedCommandStorage.PerformedCommandStorage.IntJson.serialize
import java.io.File
import java.nio.file.Path

internal class PushTest {
    private val numbers = mutableListOf<Int>()
    private val performedCommandStorage = PerformedCommandStorage<Int>()

    @Test
    fun doAction() {
        performedCommandStorage.executeOperation(Push(3), numbers)
        performedCommandStorage.executeOperation(Push(2), numbers)
        performedCommandStorage.executeOperation(Push(1), numbers)
        assertEquals(mutableListOf(1, 2, 3), numbers)
    }

    @Test
    fun undoAction() {
        performedCommandStorage.executeOperation(Push(1), numbers)
        performedCommandStorage.undoOperation(numbers)
        assertEquals(mutableListOf<Int>(), numbers)
    }
}

internal class InsertBackTest {
    private val numbers = mutableListOf<Int>()
    private val performedCommandStorage = PerformedCommandStorage<Int>()

    @Test
    fun doAction() {
        performedCommandStorage.executeOperation(InsertBack(3), numbers)
        performedCommandStorage.executeOperation(InsertBack(2), numbers)
        performedCommandStorage.executeOperation(InsertBack(1), numbers)
        assertEquals(mutableListOf(3, 2, 1), numbers)
    }

    @Test
    fun undoAction() {
        performedCommandStorage.executeOperation(InsertBack(3), numbers)
        performedCommandStorage.executeOperation(InsertBack(2), numbers)
        performedCommandStorage.undoOperation(numbers)
        assertEquals(mutableListOf(3), numbers)
    }
}

internal class RearrangeTest {
    private val numbers = mutableListOf<Int>()
    private val performedCommandStorage = PerformedCommandStorage<Int>()

    @Test
    fun doAction() {
        performedCommandStorage.executeOperation(Push(3), numbers)
        performedCommandStorage.executeOperation(Push(2), numbers)
        performedCommandStorage.executeOperation(Push(1), numbers)
        performedCommandStorage.executeOperation(Rearrange(0, 2), numbers)
        assertEquals(mutableListOf(3, 1, 2), numbers)
    }

    @Test
    fun undoAction() {
        performedCommandStorage.executeOperation(Push(3), numbers)
        performedCommandStorage.executeOperation(Push(2), numbers)
        performedCommandStorage.executeOperation(Push(1), numbers)
        performedCommandStorage.executeOperation(Rearrange(0, 2), numbers)
        performedCommandStorage.undoOperation(numbers)
        assertEquals(mutableListOf(1, 2, 3), numbers)
    }
}

internal class SerializationTest {
    private val numbers = mutableListOf<Int>()
    private val performedCommandStorage = PerformedCommandStorage<Int>()

    @Test
    fun serializeToFile(@TempDir directory: Path) {
        val tempFile = directory.resolve("savedActionsSave.json").toString()
        performedCommandStorage.executeOperation(Push(3), numbers)
        performedCommandStorage.executeOperation(InsertBack(2), numbers)
        performedCommandStorage.executeOperation(Rearrange(0, 1), numbers)
        performedCommandStorage.serialize(tempFile)
        assertEquals(javaClass.getResource("savedActionsSave.json").readText(), File(tempFile).readText())
    }
}

internal class ReadFromJsonTest {
    private val numbers = mutableListOf<Int>()
    private val performedCommandStorage = PerformedCommandStorage<Int>()

    @Test
    fun readFromJson() {
        performedCommandStorage.readFromJson(javaClass.getResource("savedActionsSave.json").path, numbers)
        assertEquals(mutableListOf(2, 3), numbers)
    }
}
