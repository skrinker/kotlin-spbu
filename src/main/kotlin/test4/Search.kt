package test4

import javafx.collections.ObservableList
import javafx.scene.control.TextField
import tornadofx.View
import tornadofx.action
import tornadofx.button
import tornadofx.hbox
import tornadofx.label
import tornadofx.listview
import tornadofx.observableListOf
import tornadofx.singleAssign
import tornadofx.textfield
import tornadofx.vbox

class Search : View() {
    private val results: ObservableList<String> = observableListOf()
    private val controller = Controller()
    private var searchString: TextField by singleAssign()

    override val root = vbox {
        hbox {
            label("Input string")
            searchString = textfield()
        }
        button("Search") {
            action {
                println(controller.getResults(searchString.text, results))
            }
        }
        listview(results)
    }
}
