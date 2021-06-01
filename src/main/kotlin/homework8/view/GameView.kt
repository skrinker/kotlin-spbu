package homework8.view

import homework8.CellValue
import homework8.controller.PlayerController
import homework8.model.Game
import javafx.application.Platform
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
import tornadofx.line
import tornadofx.onChange
import tornadofx.opcr
import tornadofx.row
import tornadofx.vbox

class TicTacToe : App(TicTacToeGrid(Game())::class)

class TicTacToeGrid(game: Game) : View("Tic-Tac-Toe") {
    @Suppress("MagicNumber")
    private fun GridPane.printBoard(board: List<CellValue>) {
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

    @Suppress("MagicNumber")
    override val root = vbox {
        gridpane {
            prefWidth = 600.0
            prefHeight = 600.0
            style = "-fx-background-color: #ffffff"
            printBoard(game.board.toList())
            game.board.onChange {
                Platform.runLater {
                    clear()
                    printBoard(game.board.toList())
                }
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

@Suppress("MagicNumber")
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

@Suppress("MagicNumber")
class Empty : ItemFragment<CellValue>() {
    private val playerController = PlayerController()
    override val root = button {
        prefWidth = 200.0
        prefHeight = 200.0
        style = "-fx-background-color: #ffffff; -fx-border-color:black; -fx-border-width: 1 0 0 1;"
        setOnMouseClicked {
            playerController.registerMove(it)
        }
    }
}

@Suppress("MagicNumber")
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
