package homework3

import com.charleskorn.kaml.Yaml
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

/**
 * Stores config information
 * @param packageName Package name
 * @param className Class name
 * @param functions List of functions
 */

@Serializable
data class Config(
    @SerialName(" package name ")  
    val packageName: String,
    @SerialName(" class name ")  
    val className: String,
    val functions: List<FunctionName>
) {
    companion object {
        /**
         * Parses yaml to [Config]
         * @param inputPath Input path
         */
        fun parseYaml(inputPath: String) = Yaml.default.decodeFromString(this.serializer(), File(inputPath).readText())
    }
}

/**
 * Stores data about a function
 * @param name Name of function
 */
@Serializable
data class FunctionName(val name: String)
