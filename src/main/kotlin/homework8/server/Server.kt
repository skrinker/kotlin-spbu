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
import kotlinx.serialization.json.Json
import java.util.concurrent.atomic.AtomicInteger

suspend fun startGame(players: List<PlayerInformation>, connections: List<Connection>) {
    val updatedGameInformation = GameInformation(isStarted = true, currentMoveId = connections[0].id)
    players[0].playerType = CellValue.Cross
    players[0].playerId = connections[0].id
    players[1].playerType = CellValue.Circle
    players[1].playerId = connections[1].id
    players.forEach {
        it.gameInformation = updatedGameInformation
    }
    for (connection in connections) {
        connection.session.outgoing.send(
            Frame.Text(
                Json.encodeToJsonElement(
                    PlayerInformation.serializer(),
                    players[connections.indexOf(connection)]
                ).toString()
            )
        )
    }
}

class Connection(val session: DefaultWebSocketSession) {
    companion object {
        var lastId = AtomicInteger(0)
    }

    val id = lastId.getAndIncrement()
}

fun main() {
    embeddedServer(Netty, port = 8000) {
        install(WebSockets) {
            masking = false
        }
        val players = mutableListOf<PlayerInformation>()
        val connections = mutableListOf<Connection>()
        var gameIsStarted = false
        routing {
            webSocket("/") {
                val thisConnection = Connection(this)
                connections.add(thisConnection)
                val defaultGameInformation = GameInformation()
                val newPlayer = PlayerInformation(thisConnection.id, gameInformation = defaultGameInformation)
                players.add(newPlayer)
                if (players.size == 2 && !gameIsStarted) {
                    startGame(players, connections)
                    println("Game is started")
                    gameIsStarted = true
                }
                try {
                    for (frame in incoming) {
                        val playerInformationFrame = frame as? Frame.Text ?: continue
                        val gameInformation = Json.decodeFromString(
                            PlayerInformation.serializer(),
                            playerInformationFrame.readText()
                        ).gameInformation
                        val updatedGameInformation = GameInformation(
                            isStarted = true,
                            board = gameInformation.board,
                            currentMoveId = (gameInformation.currentMoveId + 1) % players.size
                        )
                        players.forEach {
                            it.gameInformation = updatedGameInformation
                        }
                        for (connection in connections) {
                            connection.session.outgoing.send(
                                Frame.Text(
                                    Json.encodeToJsonElement(
                                        PlayerInformation.serializer(),
                                        players[connections.indexOf(connection)]
                                    ).toString()
                                )
                            )
                        }
                    }
                } catch (error: Exception) {
                    println(error.localizedMessage)
                }
            }
        }
    }.start(wait = true)
}
