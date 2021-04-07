package performedCommandStorage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RearrangeTest {

    @Test
    fun testExecuteInsideIndex() {
        val numbers = mutableListOf(0, 1, 2)
        Rearrange(0, 1).execute(numbers)
        assertEquals(mutableListOf(1, 0, 2), numbers)
    }

    @Test
    fun testExecuteOutsideIndex() {
        val numbers = mutableListOf(0, 1, 2)
        Rearrange(0, 3).execute(numbers)
        assertEquals(mutableListOf(0, 1, 2), numbers)
    }
		
    @Test
    fun testUndoInsideIndex() {
        val numbers = mutableListOf(0, 1, 2)
        Rearrange(0, 1).undo(numbers)
        assertEquals(mutableListOf(1, 0, 2), numbers)
    }

    @Test
    fun testUndoOutsideIndex() {
        val numbers = mutableListOf(0, 1, 2)
        Rearrange(0, 3).undo(numbers)
        assertEquals(mutableListOf(0, 1, 2), numbers)
    }
}
