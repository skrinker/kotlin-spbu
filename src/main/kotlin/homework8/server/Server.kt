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

class Game {
    private var isStarted: Boolean = false
    private val connections: MutableList<Connection> = mutableListOf<Connection>()
    private val players: MutableList<PlayerInformation> = mutableListOf<PlayerInformation>()

    private fun setStartValues() {
        players[0].playerType = CellValue.Cross
        players[0].playerId = 0
        players[1].playerType = CellValue.Circle
        players[1].playerId = 1
    }

    fun getNewPlayerInformation() = PlayerInformation(connections.size, CellValue.Empty, GameInformation())

    suspend fun sendGameInformation(gameInformation: GameInformation) {
        players.forEach {
            it.gameInformation = gameInformation
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
            println("Sended")
        }
    }

    suspend fun startGame() {
        if (players.size == 2 && !isStarted) {
            val updatedGameInformation = GameInformation(gameState = GameState.STARTED, currentMoveId = 0)
            println("Game started")
            setStartValues()
            sendGameInformation(updatedGameInformation)
            isStarted = true
        }
    }

    fun addConnection(connection: Connection) {
        connections.add(connection)
    }

    fun addPlayer(player: PlayerInformation) {
        players.add(player)
    }

    private fun checkWinner(player: PlayerInformation, board: Array<CellValue>): Int {
        if (player.playerType == CellValue.Empty) {
            return DEFAULT_VALUE
        }
        if (board[0] == player.playerType && board[1] == board[2] && player.playerType == board[1]) {
            return player.playerId
        }
        return DEFAULT_VALUE
    }

    private fun checkDraw(board: Array<CellValue>): Boolean = board.indexOf(CellValue.Empty) == -1

    private suspend fun updateGameInformation(gameInformation: GameInformation) {
        for (i in players.indices) {
            gameInformation.winnerId = checkWinner(players[i], gameInformation.board)
        }
        gameInformation.currentMoveId = (gameInformation.currentMoveId + 1) % players.size
        if (checkDraw(gameInformation.board)) {
            gameInformation.isDraw = checkDraw(gameInformation.board)
            gameInformation.gameState = GameState.ENDED
        }
        sendGameInformation(gameInformation)
    }

    suspend fun handleRequest(webSocket: DefaultWebSocketSession) {
        for (frame in webSocket.incoming) {
            val playerInformationFrame = frame as? Frame.Text ?: continue
            println(playerInformationFrame.readText())
            val gameInformation = Json.decodeFromString(
                PlayerInformation.serializer(),
                playerInformationFrame.readText()
            ).gameInformation
            updateGameInformation(gameInformation)
        }
    }
}

class Connection(val session: DefaultWebSocketSession)

fun main() {
    embeddedServer(Netty, port = 8000) {
        install(WebSockets) {
            masking = false
        }

        val game = Game()
        routing {
            webSocket("/") {
                game.addConnection(Connection(this))
                game.addPlayer(game.getNewPlayerInformation())
                game.sendGameInformation(GameInformation(GameState.LOADING, DEFAULT_VALUE, false))
                game.startGame()
                try {
                    game.handleRequest(this)
                } catch (error: Exception) {
                    println(error.localizedMessage)
                }
            }
        }
    }.start(wait = true)
}
