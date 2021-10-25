package test1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class DoubleLinkedListTest {
    @Test
    fun isEmptyTest() {
        val list = DoubleLinkedList<Int>()
        assertEquals(true, list.isEmpty())
    }

    @Test
    fun isNonEmptyTest() {
        val list = DoubleLinkedList<Int>()
        list.add(10)
        assertEquals(false, list.isEmpty())
    }

    @Test
    fun testThrowMoreThanSizeAdd() {
        val list = DoubleLinkedList<Int>()
        assertThrows<IndexOutOfBoundsException> { list.add(10, 1) }
    }

    @Test
    fun testThrowLessThanZeroAdd() {
        val list = DoubleLinkedList<Int>()
        assertThrows<IndexOutOfBoundsException> { list.add(10, -1) }
    }

    @Test
    fun removeThrowLessThanZeroTest() {
        val list = DoubleLinkedList<Int>()
        list.add(1)
        assertThrows<IndexOutOfBoundsException> { list.remove(-1) }
    }

    @Test
    fun removeThrowEmptyTest() {
        val list = DoubleLinkedList<Int>()
        assertThrows<IllegalArgumentException> { list.remove(0) }
    }

    @Test
    fun removeThrowMoreThanSizeTest() {
        val list = DoubleLinkedList<Int>()
        list.add(1)
        assertThrows<IndexOutOfBoundsException> { list.remove(1) }
    }

    @Test
    fun addComplexTest() {
        val list = DoubleLinkedList<Int>()
        list.add(1)
        list.add(2)
        list.add(3)
        assertEquals("1 2 3", list.toString())
    }

    @Test
    fun getComplexTest() {
        val list = DoubleLinkedList<Int>()
        list.add(1)
        assertEquals(1, list.get(0)?.value)
    }
}
