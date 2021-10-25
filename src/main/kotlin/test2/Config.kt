package test2

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class Config(
    @SerialName("token")
    val token: String
) {
    companion object {
        fun parseJson(inputPath: String) = Json.decodeFromString(this.serializer(), File(inputPath).readText())
    }
}
