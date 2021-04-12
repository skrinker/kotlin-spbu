package performedCommandStorage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class InsertBackTest {

    @Test
    fun execute() {
        val numbers = mutableListOf(0, 1)
        InsertBack(2).execute(numbers)
        assertEquals(mutableListOf(0, 1, 2), numbers)
    }

    @Test
    fun undo() {
        val numbers = mutableListOf(0, 1, 2)
        InsertBack(2).undo(numbers)
        assertEquals(mutableListOf(0, 1), numbers)
    }
}
