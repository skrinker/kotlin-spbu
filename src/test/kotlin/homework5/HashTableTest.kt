package homework5

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class HashTableTest {
    private val table = HashTable<String, String>(DefaultHash)

    @Test
    fun add() {
        val dataList = listOf(Pair("a", "1"), Pair("b", "2"), Pair("c", "3"))
        dataList.forEach { table.add(it.first, it.second) }
        dataList.forEach { assertEquals(it.second, table[it.first]?.value) }
    }

    @Test
    fun get() {
        val dataList = listOf(Pair("a", "1"), Pair("b", "2"), Pair("c", "3"))
        table.add(dataList[0].first, dataList[0].second)
        assertEquals(table["a"]?.value, dataList[0].second)
    }

    @Test
    fun remove() {
        val dataList = listOf(Pair("a", "1"), Pair("b", "2"), Pair("c", "3"))
        dataList.forEach { table.add(it.first, it.second) }
        assertEquals("1", table.remove("a")?.value)
        assertEquals(null, table.remove("a"))
    }

    @Test
    fun contains() {
        val dataList = listOf(Pair("a", "1"), Pair("b", "2"), Pair("c", "3"))
        dataList.forEach { table.add(it.first, it.second) }
        assertEquals(false, table.contains("d"))
        assertEquals(true, table.contains("a"))
        table.add("d", "4")
        assertEquals(true, table.contains("d"))
    }

    @Test
    fun changeHashFunction() {
        val dataList = listOf(Pair("a", "1"), Pair("b", "2"), Pair("c", "3"))
        dataList.forEach { table.add(it.first, it.second) }
        assertEquals(table["a"]?.value, "1")
        table.changeHashFunction(SimpleHash)
        assertEquals(table["a"]?.value, "1")
    }

    @Test
    fun getStatistics() {
        val dataList = listOf(Pair("a", "1"), Pair("b", "2"), Pair("c", "3"))
        dataList.forEach { table.add(it.first, it.second) }
        assertEquals(
            "Size: 3\n" +
                "Buckets: 8\n" +
                "Load factor: 0.375\n" +
                "Conflicts number: 0\n" +
                "Maximum bucket size: 1\n",
            table.getStatistics()
        )
    }
}
