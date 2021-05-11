package homework8

import kotlinx.serialization.Serializable

const val DEFAULT_VALUE = -2

@Serializable
data class PlayerInformation(
    var playerId: Int = -1,
    var playerType: CellValue = CellValue.Empty,
    var gameInformation: GameInformation = GameInformation()
)

@Serializable
enum class CellValue {
    Cross, Circle, Empty
}

@Serializable
data class GameInformation(
    var gameState: GameState = GameState.LOADING,
    var currentMoveId: Int = DEFAULT_VALUE,
    var isDraw: Boolean = false,
    var board: Array<CellValue> = Array(9) { CellValue.Empty },
    var winnerId: Int = DEFAULT_VALUE,
)

@Serializable
enum class GameState {
    LOADING, STARTED, ENDED
}
