package homework8

import kotlinx.serialization.Serializable

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
    val isStarted: Boolean = false,
    var currentMoveId: Int = -2,
    var board: Array<CellValue> = Array(9) { CellValue.Empty },
    val winnerId: Int = -2,
    val draw: Boolean = false
)
