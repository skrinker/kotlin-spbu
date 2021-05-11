package homework8.client

import homework8.DEFAULT_VALUE
import homework8.GameState
import homework8.PlayerInformation
import javafx.application.Platform
import javafx.scene.input.MouseEvent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import tornadofx.Controller

var playerInformation = PlayerInformation()
private var webSocket: WebSocketController = WebSocketController()

class PLayerController : Controller() {
    fun registerMove(mouseEvent: MouseEvent) {
        if (playerInformation.gameInformation.currentMoveId == playerInformation.playerId) {
            val x = (mouseEvent.sceneX.toInt() - 1) / 200
            val y = (mouseEvent.sceneY.toInt() - 1) / 200
            playerInformation.gameInformation.board[y * 3 + x] = playerInformation.playerType
            updateBoard(playerInformation.gameInformation.board)
            println("Sent")
            webSocket.sendPlayerInformation()
        }
    }
}

class WindowController : Controller() {
    fun getStatusText(): String {
        val statusText: String
        val gameInformation = playerInformation.gameInformation
        println(gameInformation.toString())
        statusText = when (gameInformation.gameState) {
            GameState.LOADING -> "Loading"
            GameState.ENDED -> 
                if (gameInformation.winnerId != DEFAULT_VALUE) {
                    if (gameInformation.winnerId == playerInformation.playerId) "Win"
                    else "Lose"
                } else
                    "Draw"
            else -> "Wrong"
        }
        return statusText
    }
}

class WebSocketController {
    private var webSocket: WebSocket = connect()

    private fun connect(): WebSocket = runBlocking {
        val client = OkHttpClient.Builder().build()
        val request = Request.Builder().url("ws://localhost:8000/").build()
        val webSocketListener = EchoWebSocketListener()
        return@runBlocking client.newWebSocket(request, webSocketListener)
    }

    fun sendPlayerInformation() {
        webSocket.send(Json.encodeToJsonElement(PlayerInformation.serializer(), playerInformation).toString())
    }
}

class EchoWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        updateGameState(GameState.LOADING)
    }

    override fun onMessage(webSocket: WebSocket?, text: String) {
        println(text)
        playerInformation = Json.decodeFromString(PlayerInformation.serializer(), text)
        val gameInformation = playerInformation.gameInformation
        if (gameInformation.gameState == GameState.STARTED)
            Platform.runLater {
                updateBoard(gameInformation.board)
            }
        if (gameInformation.gameState == GameState.ENDED || gameInformation.gameState == GameState.LOADING) {
            Platform.runLater {
                updateBoard(gameInformation.board)
                updateGameState(gameInformation.gameState)
            }
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(100, null)
    }
}
