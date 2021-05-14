package homework7

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class MatrixTest {

    companion object {

        @JvmStatic
        fun multiplyTest(): List<Arguments> = listOf(
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(4522, 727, 157, 3653),
                        intArrayOf(6150, 997, 137, 2284),
                        intArrayOf(16191, 2799, -179, -2824),
                        intArrayOf(2124, 439, 7, 3630)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(30, 15, 0, -23),
                        intArrayOf(40, 10, -1, 12),
                        intArrayOf(98, -9, 21, 34),
                        intArrayOf(12, 14, 15, -86)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(157, 25, 0, -6),
                        intArrayOf(-11, 0, 12, 254),
                        intArrayOf(32, 15, -5, 4),
                        intArrayOf(1, 1, 1, -1)
                    )
                )
            ),
            Arguments.of(
                Matrix(
                    arrayOf(
                        intArrayOf(4558, 841, 283, 3287, 312),
                        intArrayOf(6198, 1149, 305, 1796, 1239),
                        intArrayOf(16251, 2989, 31, -3434, 2805),
                        intArrayOf(2196, 667, 259, 2898, -1029),
                        intArrayOf(3273, 2941, 2750, -8124, 3429)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(30, 15, 0, -23, 6),
                        intArrayOf(40, 10, -1, 12, 8),
                        intArrayOf(98, -9, 21, 34, 10),
                        intArrayOf(12, 14, 15, -86, 12),
                        intArrayOf(15, 0, 4, -2, 132)
                    )
                ),
                Matrix(
                    arrayOf(
                        intArrayOf(157, 25, 0, -6, 15),
                        intArrayOf(-11, 0, 12, 254, 18),
                        intArrayOf(32, 15, -5, 4, 21),
                        intArrayOf(1, 1, 1, -1, 24),
                        intArrayOf(6, 19, 21, -61, 24)
                    )
                )
            )
        )
    }

    @MethodSource("multiplyTest")
    @ParameterizedTest(name = "correctDataTest{index}, {1}")
    fun matrixTimesCorrectDataTest(expectedMatrix: Matrix, firstMatrix: Matrix, secondMatrix: Matrix) {
        assertEquals(expectedMatrix.toString(), (firstMatrix * secondMatrix).toString())
    }
}
