package homework8.controller

import homework8.model.Game
import homework8.model.Player
import javafx.scene.input.MouseEvent
import tornadofx.Controller

class PlayerController(private val gameController: GameController, private val game: Game, private val player: Player) : Controller() {
    @Suppress("MagicNumber")
    fun registerMove(mouseEvent: MouseEvent) {
        if (game.currentMoveId == player.id) {
            val x = (mouseEvent.sceneX.toInt() - 1) / 200
            val y = (mouseEvent.sceneY.toInt() - 1) / 200
            game.board[y * 3 + x] = player.type
            gameController.updateBoard(game.board.toList())
//            webSocket.sendPlayerInformation()
        }
    }
}