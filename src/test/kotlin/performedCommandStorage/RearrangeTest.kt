package performedCommandStorage

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class RearrangeTest {

    @Test
    fun execute() {
        val numbers = mutableListOf(0, 1, 2)
        // Index inside
        Rearrange(0, 1).execute(numbers) 
        assertEquals(mutableListOf(1, 0, 2), numbers)

        // Index outside
        Rearrange(0, 3).execute(numbers) 
        assertEquals(mutableListOf(1, 0, 2), numbers)
    }

    @Test
    fun undo() {
        val numbers = mutableListOf(0, 1, 2)
        // Index inside
        Rearrange(0, 1).undo(numbers)
        assertEquals(mutableListOf(1, 0, 2), numbers)

        // Index outside
        Rearrange(0, 3).undo(numbers)
        assertEquals(mutableListOf(1, 0, 2), numbers)
    }
}
