package homework3

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Stores config information
 * @param packageName Package name
 * @param className Class name
 * @param functions List of functions
 */

@Serializable
data class Config(
    @SerialName("package name")
    val packageName: String,
    @SerialName("class name")
    val className: String,
    val functions: List<FunctionName>
)

/**
 * Stores data about a function
 * @param name Name of function
 */
@Serializable
data class FunctionName(val name: String)
