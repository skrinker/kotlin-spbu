package homework3

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.io.TempDir
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.nio.file.Path

internal class ComplexTest {
    companion object {
        @JvmStatic
        fun inputData(): List<Arguments> = listOf(
            Arguments.of(
                "ConfigTest1", "GeneratorTest1.kt"
            ),
            Arguments.of(
                "ConfigTest2", "GeneratorTest2.kt"
            ),
        )
    }

    @MethodSource("inputData")
    @ParameterizedTest(name = "test {index}, {1}")
    fun complexTest(configName: String, generatedFileName: String, @TempDir tempDir: Path) {
        val inputPath = javaClass.getResource("$configName.yaml").path
        val config = Config.parseYaml(inputPath)
        val expectedCode = javaClass.getResource(generatedFileName).readText().replace("\r\n", "\n")
        generateTest(inputPath, tempDir.toString())
        assertEquals(
            expectedCode,
            tempDir.resolve("${config.packageName}/${config.className}Test.kt").toFile().readText()
        )
    }
}
