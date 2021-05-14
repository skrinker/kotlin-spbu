package homework5

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

internal class ParseOperandTreeTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(mutableListOf("+", "200", "3"), OperandNode("+", 0, OperatorNode(200, 1), OperatorNode(3, 1))),
            Arguments.of(mutableListOf("/", "200", "10"), OperandNode("/", 0, OperatorNode(200, 1), OperatorNode(10, 1)))
        )
    }
    @MethodSource("inputData")
    @ParameterizedTest(name = "test")
    fun parseListToOperandTest(list: MutableList<String>, expectedOperand: OperandNode) {
        assertEquals(expectedOperand.calculate(), ParseOperandNode().parse(list, 0).calculate())
    }
}
