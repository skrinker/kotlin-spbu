package homework5

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class OperandNodeTest {
    companion object {
        @JvmStatic
        fun inputDataCalculate(): List<Arguments> = listOf(
            Arguments.of(
                OperandNode(
                    "+", 0,
                    OperatorNode(200, 1),
                    OperatorNode(3, 1),
                ),
                203
            ),
            Arguments.of(OperatorNode(-10, 0), -10)
        )

        @JvmStatic
        fun inputDataPrint(): List<Arguments> = listOf(
            Arguments.of(
                OperandNode(
                    "+", 0,
                    OperatorNode(200, 1),
                    OperatorNode(3, 1),
                ),
                "+\n" +
                    "....200\n" +
                    "....3\n"
            ),
            Arguments.of(OperatorNode(-10, 0), "-10")
        )
    }

    @MethodSource("inputDataCalculate")
    @ParameterizedTest(name = "test")
    fun calculateTest(operandNode: Node, value: Int) {
        assertEquals(value, operandNode.calculate())
    }

    @MethodSource("inputDataPrint")
    @ParameterizedTest(name = "test")
    fun print(operandNode: Node, output: String) {
        assertEquals(output, operandNode.toString())
    }
}
