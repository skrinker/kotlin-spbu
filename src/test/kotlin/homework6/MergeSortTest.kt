package homework6

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MergeSortTest {

    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                intArrayOf(0, 2, 3, 1, 5),
                intArrayOf(0, 1, 2, 3, 5)
            ),
            Arguments.of(
                intArrayOf(5, 2, 3, 1, 0),
                intArrayOf(0, 1, 2, 3, 5)
            ),
            Arguments.of(
                intArrayOf(),
                intArrayOf()
            ),
            Arguments.of(
                intArrayOf(5, 2, 3, 1, 0, 4, 10, 6, 9, 7, 8),
                intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun mergeSort(inputArray: IntArray, outputArray: IntArray) {
        for (threads in 1..64) {
            MergeSort(threads).sort(inputArray)
            assertEquals(outputArray.toList(), inputArray.toList())
        }
    }
}
