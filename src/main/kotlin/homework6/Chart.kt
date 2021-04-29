import homework6.Statistics
import homework6.getMethodsList
import javafx.beans.property.SimpleIntegerProperty
import javafx.scene.chart.NumberAxis
import tornadofx.App
import tornadofx.View
import tornadofx.ViewModel
import tornadofx.action
import tornadofx.button
import tornadofx.data
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.linechart
import tornadofx.required
import tornadofx.series
import tornadofx.textfield
import tornadofx.vbox

private val inputData = object : ViewModel() {
    val numberOfThreads = bind { SimpleIntegerProperty() }
    val arraySize = bind { SimpleIntegerProperty() }
    val offset = bind { SimpleIntegerProperty() }
    val averageStep = bind { SimpleIntegerProperty() }
}

class InputView : View() {
    override val root = vbox {
        form {
            fieldset {
                field("Number of threads") {
                    textfield(inputData.numberOfThreads) {
                        required()
                    }
                }
                field("Array size") {
                    textfield(inputData.arraySize) {
                        required()
                    }
                }
                field("Offset") {
                    textfield(inputData.offset) {
                        required()
                    }
                }
                field("Number of steps to calculate average time") {
                    textfield(inputData.averageStep) {
                        required()
                    }
                }
                button("OK") {
                    isDefaultButton = true
                    action {
                        this@InputView.replaceWith(ChartView::class)
                    }
                }
            }
        }
    }
}

class ChartView : View() {
    override val root = vbox {
        linechart("Multi-threaded merge sort algorithm", NumberAxis(), NumberAxis()) {
            getMethodsList().forEach {
                series(it.name) {
                    Statistics(it).getStatistics(
                        inputData.arraySize.value,
                        inputData.offset.value,
                        inputData.averageStep.value,
                        inputData.averageStep.value
                    ).forEach { data(it.first, it.second) }
                }
            }
        }
    }
}

class ChartApp : App(InputView::class)
