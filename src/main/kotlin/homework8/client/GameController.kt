package homework8.client

import homework8.PlayerInformation
import kotlinx.serialization.json.Json
import okhttp3.WebSocket
import tornadofx.Controller

class GameController : Controller() {
    fun sendPlayerInformation(playerInformation: PlayerInformation, client: WebSocket) {
        client.send(
            Json.encodeToJsonElement(PlayerInformation.serializer(), playerInformation).toString()
        )
    }
}
