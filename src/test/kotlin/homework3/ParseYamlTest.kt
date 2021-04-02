package homework3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class ParseYamlTest {

    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                Config(
                    "homework3",
                    "PerformedCommandStorage",
                    listOf(
                        FunctionName("Push"),
                        FunctionName("InsertBack")
                    )
                ),
                "src/test/resources/homework3/ConfigTest1.yaml"
            ),
            Arguments.of(
                Config(
                    "homework3",
                    "PerformedCommandStorage",
                    listOf(
                        FunctionName("Push")
                    )
                ),
                "src/test/resources/homework3/ConfigTest2.yaml"
            )
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun parseYaml(expected: Config, inputPath: String) {
        assertEquals(expected, parseYaml(inputPath))
    }
}
