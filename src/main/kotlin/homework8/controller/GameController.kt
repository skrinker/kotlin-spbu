package homework8.controller

import homework8.CellValue
import homework8.GameState
import homework8.model.Game
import homework8.model.ModType
import javafx.application.Platform

class GameController(private val game: Game) {
    fun updateBoard(newBoard: List<CellValue>) {
        game.board.removeAll()
        for (i in newBoard.indices) {
            Platform.runLater { game.board.add(newBoard[i]) }
        }
    }

    fun updateState(newGameState: GameState) {
        game.state.removeAll()
        game.state.add(newGameState)
    }

    fun updateMod(newMod: ModType) {
        game.mod = newMod
    }

    private fun togglePlayerId() {
        for (player in game.players) {
            if (game.currentMoveId != player.id) {
                game.currentMoveId = player.id
                break
            }
        }
    }

    fun updateCurrentMoveId() {
        require(game.players.size == 2) {
            throw IndexOutOfBoundsException()
        }
        if (game.currentMoveId == null) {
            game.currentMoveId = game.players.first().id
        } else {
            togglePlayerId()
        }
    }
}