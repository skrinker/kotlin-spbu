package homework3

import com.charleskorn.kaml.Yaml
import java.io.File

/**
 * Parses yaml to [Config]
 * @param inputPath Input path
 */
fun parseYaml(inputPath: String) = Yaml.default.decodeFromString(Config.serializer(), File(inputPath).readText())

/**
 * Generate test file from config
 * @param config Parsed data
 * @param outputPath Output path
 */
fun generateTest(config: Config, outputPath: String) {
    val generatedFile = FileGenerator(config).file
    generatedFile.writeTo(File(outputPath))
}

fun main(args: Array<String>) {
    if (args.size != 2) {
        throw IllegalArgumentException("Too few arguments to function")
    }
    generateTest(parseYaml(args[0]), args[1])
}
