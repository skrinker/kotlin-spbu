package homework8

import homework8.server.PORT
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

@Suppress("MagicNumber")
class Game {
    private var gameState: GameState = GameState.LOADING

    private val connections = mutableListOf<Connection>()
    private val players = mutableListOf<PlayerInformation>()

    private val winIndex = listOf(
        intArrayOf(0, 1, 2),
        intArrayOf(3, 4, 5),
        intArrayOf(6, 7, 8),
        intArrayOf(0, 4, 8),
        intArrayOf(2, 4, 6),
        intArrayOf(0, 3, 6),
        intArrayOf(1, 4, 7),
        intArrayOf(2, 5, 8)
    )

    private fun setStartValues() {
        require(players.size > 1) {
            throw IndexOutOfBoundsException()
        }

        for (currentPlayerIndex in 0 until players.size) {
            players[currentPlayerIndex].playerId = currentPlayerIndex
            players[currentPlayerIndex].playerType = when (currentPlayerIndex) {
                0 -> CellValue.Cross
                1 -> CellValue.Circle
                else -> CellValue.Empty
            }
        }
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
        }
    }

    suspend fun startNewGame() {
        if (players.size == 2 && gameState != GameState.STARTED) {
            val updatedGameInformation = GameInformation(gameState = GameState.STARTED, currentMoveId = 0)
            setStartValues()
            sendGameInformation(updatedGameInformation)
            gameState = GameState.WAITING
        }
    }

    fun addConnection(connection: Connection) {
        connections.add(connection)
    }

    fun addPlayer(player: PlayerInformation) {
        players.add(player)
    }

    private fun checkWinner(player: PlayerInformation, board: Array<CellValue>): Int {
        var winnerId: Int = DEFAULT_VALUE
        winIndex.forEach {
            when {
                board[it[0]] == board[it[1]] && board[it[1]] == board[it[2]] &&
                    board[it[1]] == player.playerType && player.playerType != CellValue.Empty -> {
                    winnerId = player.playerId
                }
            }
        }
        return winnerId
    }

    private fun checkDraw(board: Array<CellValue>): Boolean = board.indexOf(CellValue.Empty) == -1

    private suspend fun updateGameInformation(gameInformation: GameInformation) {
        for (player in players) {
            val winnerId = checkWinner(player, gameInformation.board)
            if (winnerId != DEFAULT_VALUE) {
                gameInformation.winnerId = winnerId
                gameInformation.gameState = GameState.ENDED
            }
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
    embeddedServer(Netty, PORT) {
        install(WebSockets) {
            masking = false
        }

        val game = Game()
        routing {
            webSocket("/") {
                game.addConnection(Connection(this))
                game.addPlayer(game.getNewPlayerInformation())
                game.sendGameInformation(GameInformation(GameState.LOADING, DEFAULT_VALUE, false))
                game.startNewGame()
                game.handleRequest(this)
            }
        }
    }.start(wait = true)
}
