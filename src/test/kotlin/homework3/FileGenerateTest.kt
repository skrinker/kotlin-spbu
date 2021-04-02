package homework3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class FileGenerateTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                "GeneratorTest1.kt",
                Config(
                    "homework3",
                    "PerformedCommandStorage",
                    listOf(
                        FunctionName("Push"),
                        FunctionName("InsertBack")
                    )
                )
            ),
            Arguments.of(
                "GeneratorTest1.kt",
                Config(
                    "homework3",
                    "PerformedCommandStorage",
                    listOf(
                        FunctionName("Push")
                    )
                )
            )
        )
    }
    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun generateTest(expectedPath: String, config: Config) {
        val expectedTest = javaClass.getResource(expectedPath).readText()
        assertEquals(expectedTest, FileGenerator(config).file)
    }
}
