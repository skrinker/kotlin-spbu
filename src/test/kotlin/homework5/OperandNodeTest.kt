package homework5

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

internal class OperandNodeTest {
    companion object {
        @JvmStatic
        fun inputDataCalculate(): List<Arguments> = listOf(
            Arguments.of(
                OperandNode(
                    "+", -1,
                    OperandNode(null, 200, null, null),
                    OperandNode(null, 3, null, null)
                ),
                203
            ),
            Arguments.of(OperandNode(null, -10, null, null), -10)
        )

        @JvmStatic
        fun inputDataPrint(): List<Arguments> = listOf(
            Arguments.of(
                OperandNode(
                    "+", -1,
                    OperandNode(null, 200, null, null),
                    OperandNode(null, 3, null, null)
                ),
                "+\r\n" +
                    "....200\r\n" +
                    "....3\r\n"
            ),
            Arguments.of(OperandNode(null, -10, null, null), "-10\r\n")
        )
    }

    @MethodSource("inputDataCalculate")
    @ParameterizedTest(name = "test")
    fun calculateTest(operandNode: OperandNode, value: Int) {
        assertEquals(value, operandNode.calculate())
    }

    @MethodSource("inputDataPrint")
    @ParameterizedTest(name = "test")
    fun print(operandNode: OperandNode, output: String) {
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        operandNode.print(0)
        assertEquals(output, outputStream.toString())
        System.setOut(null)
    }
}
