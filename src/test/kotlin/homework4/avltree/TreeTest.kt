package homework4.avltree

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class TreeTest {
    private val tree = Tree<String, String>()

    companion object {
        @JvmStatic
        fun sizeData() = listOf(
            Arguments.of(1, mapOf("a" to "abc")),
            Arguments.of(0, mapOf<String, String>())
        )

        @JvmStatic
        fun containsData() = listOf(
            Arguments.of(true, Pair("a", "abc"), mapOf("a" to "abc", "b" to "cab")),
            Arguments.of(false, Pair("", "ab"), mapOf("a" to "abc", "b" to "cab")),
        )
    }

    @MethodSource("sizeData")
    @ParameterizedTest
    fun getSize(expectedSize: Int, actualMap: Map<String, String>) {
        tree.putAll(actualMap)
        assertEquals(expectedSize, tree.size)
    }

    @MethodSource("containsData")
    @ParameterizedTest
    fun containsKey(expected: Boolean, element: Pair<String, String>, actualMap: Map<String, String>) {
        tree.putAll(actualMap)
        assertEquals(expected, tree.containsKey(element.first))
    }

    @MethodSource("containsData")
    @ParameterizedTest
    fun containsValue(expected: Boolean, element: Pair<String, String>, actualMap: Map<String, String>) {
        tree.putAll(actualMap)
        assertEquals(expected, tree.containsValue(element.second))
    }

    @Test
    fun getEntries() {
        tree["a"] = "b"
        tree["b"] = "a"
        assertEquals(mutableMapOf("a" to "b", "b" to "a").entries, tree.entries)
    }

    @Test
    fun getValues() {
        tree["a"] = "b"
        tree["b"] = "a"
        assertEquals(mutableListOf("b", "a"), tree.values)
    }

    @Test
    fun getKeys() {
        tree["a"] = "b"
        tree["b"] = "a"
        assertEquals(mutableSetOf("a", "b"), tree.keys)
    }


    @Test
    fun get() {
        tree["a"] = "b"
        assertEquals("b", tree["a"])
    }

    @Test
    fun isEmpty() {
        assertEquals(true, tree.isEmpty())
    }

    @Test
    fun clear() {
        tree["a"] = "b"
        tree["b"] = "a"
        tree.clear()
        assertEquals(true, tree.isEmpty())
    }

    @Test
    fun put() {
        tree["a"] = "b"
        assertEquals(true, tree.containsKey("a"))
    }

    @Test
    fun remove() {
        tree["a"] = "b"
        tree["b"] = "a"
        tree.remove("a")
        assertEquals(false, tree.contains("a"))
    }

    @Test
    fun putAll() {
        val data = mapOf("a" to "b", "b" to "a")
        tree.putAll(data)
        assertEquals(data.entries, tree.entries)
    }
}
