package homework8.model

import homework8.CellValue
import homework8.GameState
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class Game {
    val state: ObservableList<GameState> = FXCollections.observableArrayList()
    val board: ObservableList<CellValue> = FXCollections.observableArrayList()
    var mod: ModType? = null
    var currentMoveId: Int? = null
    val players = mutableListOf<Player>()
}

enum class ModType {
    ONLINE, SELF
}
