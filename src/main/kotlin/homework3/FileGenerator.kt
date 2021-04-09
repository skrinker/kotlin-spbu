package homework3

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec

/**
 * Generates file with tests
 * @param config Parsed yaml config
 * @property file Generated file
 */

class FileGenerator(private val config: Config) {
    private val name = "${config.className}Test"

    private fun createFunction(function: FunctionName) = FunSpec.builder(function.name)
        .addAnnotation(ClassName("org.junit.jupiter.api", "Test"))
        .build()

    private fun createClass(functions: List<FunctionName>) =
        TypeSpec.classBuilder(name)
            .addModifiers(KModifier.INTERNAL)
            .addFunctions(functions.map { createFunction(it) })
            .build()

    val file: FileSpec
        get() = FileSpec.builder(config.packageName, name)
            .addType(createClass(config.functions))
            .build()
}
