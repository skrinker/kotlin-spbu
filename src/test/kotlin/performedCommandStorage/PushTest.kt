package performedCommandStorage

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class PushTest {

    @Test
    fun execute() {
        val numbers = mutableListOf(1, 2)
        Push(0).execute(numbers)
        assertEquals(mutableListOf(0, 1, 2), numbers)
    }

    @Test
    fun undo() {
        val numbers = mutableListOf(0, 1, 2)
        Push(0).undo(numbers)
        assertEquals(mutableListOf(1, 2), numbers)
    }
}
