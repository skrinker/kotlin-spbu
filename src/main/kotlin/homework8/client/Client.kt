package homework8.client

import homework8.CellValue
import homework8.GameState
import javafx.application.Application
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.scene.control.Button
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.App
import tornadofx.ItemFragment
import tornadofx.View
import tornadofx.button
import tornadofx.circle
import tornadofx.clear
import tornadofx.gridpane
import tornadofx.group
import tornadofx.label
import tornadofx.line
import tornadofx.onChange
import tornadofx.opcr
import tornadofx.row
import tornadofx.vbox

private val board = FXCollections.observableArrayList<CellValue>()
private val gameState = FXCollections.observableArrayList<GameState>()

fun main(args: Array<String>) {
    Application.launch(TicTacToe::class.java, *args)
}

fun updateBoard(newBoard: Array<CellValue>) {
    board.removeAll()
    for (i in newBoard.indices) {
        Platform.runLater { board.add(newBoard[i]) }
    }
}

fun updateGameState(newGameState: GameState) {
    gameState.removeAll()
    gameState.add(newGameState)
}

class TicTacToe : App(TicTacToeGrid::class)

class StatusWindow : View("Status window") {
    private val windowController = WindowController()
    override val root = vbox {
        gameState.onChange {
            label(windowController.getStatusText())
        }
    }
}

class TicTacToeGrid : View("Tic-Tac-Toe") {
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

    private val controller = WindowController()

    override val root = vbox {
        gridpane {
            prefWidth = 600.0
            prefHeight = 600.0
            style = "-fx-background-color: #ffffff"
            printBoard(playerInformation.gameInformation.board)
            board.onChange {
                clear()
                printBoard(playerInformation.gameInformation.board)
            }
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
    private val playerController = PLayerController()
    override val root = button {
        prefWidth = 200.0
        prefHeight = 200.0
        style = "-fx-background-color: #ffffff; -fx-border-color:black; -fx-border-width: 1 0 0 1;"
        setOnMouseClicked {
            playerController.registerMove(it)
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
