package test3

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class NullTest {

    @Test
    fun throwTest() {
        assertThrows<IndexOutOfBoundsException> {
            BubbleSort(mutableListOf<Int>()).sort(Comparator.naturalOrder())
        }
    }
}