package homework3

import java.io.File

/**
 * Generate test file from config
 * @param inputPath Yaml config path
 * @param outputPath Output path
 */
fun generateTest(inputPath: String, outputPath: String) {
    val config = Config.parseYaml(inputPath)
    val generatedFile = FileGenerator(config).file

    generatedFile.writeTo(File(outputPath))
}

fun main(args: Array<String>) {
    if (args.size != 2) {
        throw IllegalArgumentException("Too few arguments to function")
    }

    generateTest(args[0], args[1])
}
