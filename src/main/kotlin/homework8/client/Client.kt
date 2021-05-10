package homework8.client

// import io.ktor.client.HttpClient
// import io.ktor.client.features.websocket.ClientWebSocketSession
// import io.ktor.client.features.websocket.DefaultClientWebSocketSession
// import io.ktor.client.features.websocket.WebSockets
// import io.ktor.client.features.websocket.webSocketSession
// import io.ktor.client.features.websocket.wss
// import io.ktor.http.HttpMethod
// import io.ktor.http.cio.websocket.ExperimentalWebSocketExtensionApi
// import io.ktor.http.cio.websocket.WebSocketSession
//
// fun Array<CellValue>.print() {
//    var result = StringBuilder()
//    for (i in this.indices) {
//        var temp = when (this[i]) {
//            CellValue.Circle -> "O"
//            CellValue.Cross -> "X"
//            CellValue.Empty -> " "
//        }
//        result.append(temp)
//        if ((i + 1) % 3 == 0) {
//            result.append("\n")
//        }
//    }
//    print(result)
// }
//
// fun main() {
//    val client = HttpClient {
//        install(WebSockets)
//    }
//
//    runBlocking {
//        client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8000) {
//            while (true) {
//                val msg = incoming.receive() as? Frame.Text ?: continue
//                var playerInformation = Json.decodeFromString(PlayerInformation.serializer(), msg.readText())
//                if (playerInformation.gameInformation.isStarted) {
//                    var board = playerInformation.gameInformation.board
//                    if (playerInformation.playerId == playerInformation.gameInformation.currentMoveId) {
//                        board.print()
//                        println("Your move")
//                        var move = readLine()
//                        board[move!!.toInt()] = playerInformation.playerType
//                        board.print()
//                        playerInformation.gameInformation.board = board
//                        outgoing.send(
//                            Frame.Text(
//                                Json.encodeToJsonElement(PlayerInformation.serializer(), playerInformation)
//                                    .toString()
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }
//    client.close()
// }

import homework8.CellValue
import homework8.PlayerInformation
import javafx.application.Application
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import tornadofx.App
import tornadofx.ItemFragment
import tornadofx.View
import tornadofx.button
import tornadofx.circle
import tornadofx.clear
import tornadofx.gridpane
import tornadofx.group
import tornadofx.line
import tornadofx.onChange
import tornadofx.opcr
import tornadofx.row

fun Array<CellValue>.print() {
    val result = StringBuilder()
    for (i in this.indices) {
        val temp = when (this[i]) {
            CellValue.Circle -> "O"
            CellValue.Cross -> "X"
            CellValue.Empty -> " "
        }
        result.append(temp)
        if ((i + 1) % 3 == 0) {
            result.append("\n")
        }
    }
    print(result)
}

private var webSocket: WebSocket = connect()
private var playerInformation = PlayerInformation()
private val board = FXCollections.observableArrayList<CellValue>()

fun connect(): WebSocket = runBlocking {
    val client = OkHttpClient.Builder().build()
    val request = Request.Builder().url("ws://localhost:8000/").build()
    val webSocketListener = EchoWebSocketListener()
    return@runBlocking client.newWebSocket(request, webSocketListener)
}

fun main(args: Array<String>) {
    Application.launch(TicTacToe::class.java, *args)
}

private fun updateBoard(newBoard: Array<CellValue>) {
    board.removeAll()
    for (i in newBoard.indices) {
        Platform.runLater { board.add(newBoard[i]) }
    }
}

private class EchoWebSocketListener : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        println("Connected")
    }

    override fun onMessage(webSocket: WebSocket?, text: String) {
        playerInformation = Json.decodeFromString(PlayerInformation.serializer(), text)
        Platform.runLater { updateBoard(playerInformation.gameInformation.board) }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        webSocket.close(100, null)
    }
}

class TicTacToe : App(TicTacToeGrid::class)

class TicTacToeGrid : View("Tic Tac Toe") {
    private fun GridPane.printBoard(board: Array<CellValue>) {
        for (i in 0..2) {
            row {
                for (j in 0..2) {
                    when (board[i * 3 + j]) {
                        CellValue.Empty -> empty { }
                        CellValue.Cross -> cross { }
                        CellValue.Circle -> circle { }
                    }
                }
            }
        }
    }

    override val root =
        gridpane {
            style = "-fx-background-color: #ffffff"
            printBoard(playerInformation.gameInformation.board)
            board.onChange {
                clear()
                printBoard(playerInformation.gameInformation.board)
            }
        }

    private fun Pane.empty(op: (Button.() -> Unit)) =
        opcr(this, tornadofx.find(Empty::class).root, op)

    private fun Pane.cross(op: (Button.() -> Unit)) =
        opcr(this, tornadofx.find(Cross::class).root, op)

    private fun Pane.circle(op: (Button.() -> Unit)) =
        opcr(this, tornadofx.find(Circle::class).root, op)
}

class Circle : ItemFragment<CellValue>() {
    override val root = button {
        prefWidth = 200.0
        prefHeight = 200.0
        style = "-fx-background-color: #ffffff; -fx-border-color:black; -fx-border-width: 1 0 0 1;"
        graphic = group {
            circle {
                centerX = 30.0
                centerY = 30.0
                radius = 20.0
                stroke = Color.BLACK
                fill = null
                strokeWidth = 1.0
            }
        }
    }
}

class Empty : ItemFragment<CellValue>() {
    val controller = GameController()
    override val root = button {
        prefWidth = 200.0
        prefHeight = 200.0
        style = "-fx-background-color: #ffffff; -fx-border-color:black; -fx-border-width: 1 0 0 1;"
        setOnMouseClicked {
            if (playerInformation.gameInformation.currentMoveId == playerInformation.playerId) {
                val x = (it.sceneX.toInt() - 1) / 200
                val y = (it.sceneY.toInt() - 1) / 200
                playerInformation.gameInformation.board[y * 3 + x] = playerInformation.playerType
                updateBoard(playerInformation.gameInformation.board)
                controller.sendPlayerInformation(playerInformation, webSocket)
            }
        }
    }
}

class Cross : ItemFragment<CellValue>() {
    override val root = button {
        prefWidth = 200.0
        prefHeight = 200.0
        style = "-fx-background-color: #ffffff; -fx-border-color:black; -fx-border-width: 1 0 0 1;"
        graphic = group {
            group {
                line {
                    startX = 10.0
                    startY = 10.0
                    endX = 50.0
                    endY = 50.0
                    stroke = Color.BLACK
                    strokeWidth = 1.0
                }
                line {
                    startX = 10.0
                    startY = 50.0
                    endX = 50.0
                    endY = 10.0
                    stroke = Color.BLACK
                    strokeWidth = 1.0
                }
            }
        }
    }
}
