package test3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ListTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> =
            listOf(
                Arguments.of(mutableListOf(1, 2, 3, 7, 8, 9, 10), mutableListOf(1, 2, 3, 10, 7, 8, 9)),
                Arguments.of(mutableListOf(2, 3, 7, 8, 10), mutableListOf(2, 3, 10, 7, 8)),
                Arguments.of(mutableListOf(1, 7, 8, 9, 10), mutableListOf(1, 7, 8, 9, 10))
            )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun listTest(expected: MutableList<Int>, actual: MutableList<Int>) =
        assertEquals(
            expected.toString(),
            BubbleSort(actual).sort(Comparator.naturalOrder()).toString()
        )
}
