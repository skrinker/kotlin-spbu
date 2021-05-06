package homework8

import io.ktor.application.install
import io.ktor.http.cio.websocket.DefaultWebSocketSession
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.readText
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.websocket.WebSockets
import io.ktor.websocket.webSocket
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.LinkedHashSet
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class PlayerInformation(
    val playerId: Int,
    val gameInformation: GameInformation
)

@Serializable
data class GameInformation(
    val winnerId: Int = -1,
    val draw: Boolean = false,
    val currentMoveId: Int = -1,
    val board: IntArray = IntArray(9) { -1 }
)

class Connection(val session: DefaultWebSocketSession) {
    companion object {
        var lastId = AtomicInteger(0)
    }

    val userId = lastId.getAndIncrement()
}

fun main() {
    val connections = Collections.synchronizedSet<Connection?>(LinkedHashSet())
    embeddedServer(Netty, port = 8000) {
        install(WebSockets) {
            masking = false
        }
        routing {
            webSocket("/") {
                val newConnection = Connection(this)
                connections += newConnection
                for (frame in incoming) {
                    val playerInformationFrame = frame as? Frame.Text ?: continue
                    if (connections.size < 2) {
                        outgoing.send((Frame.Text("Game status waiting")))
                    } else {
                        val playerInformation =
                            Json.decodeFromString(PlayerInformation.serializer(), playerInformationFrame.readText())
                        if (playerInformation.gameInformation.currentMoveId == -1) var currentMoveId = connections.first().userId
                    }
                }
            }
        }
    }.start(wait = true)
}
