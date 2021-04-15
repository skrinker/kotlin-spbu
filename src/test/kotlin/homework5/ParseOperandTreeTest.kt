package homework5

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class ParseOperandTreeTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(mutableListOf("+", "200", "3"), OperandNode("+", -1, OperandNode(null, 200, null, null), OperandNode(null, 3, null, null))),
            Arguments.of(mutableListOf("-10"), OperandNode(null, -10, null, null))
        )
    }
    @MethodSource("inputData")
    @ParameterizedTest(name = "test")
    fun parseListToOperandTest(list: MutableList<String>, expectedOperand: OperandNode) {
        assertEquals(expectedOperand.calculate(), ParseOperandNode().parse(list).calculate())
    }
}
