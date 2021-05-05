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
    val minThreads = bind { SimpleIntegerProperty() }
    val maxThreads = bind { SimpleIntegerProperty() }
    val arraySize = bind { SimpleIntegerProperty() }
    val stepSize = bind { SimpleIntegerProperty() }
    val averageStep = bind { SimpleIntegerProperty() }
}

class InputView : View() {
    override val root = vbox {
        form {
            fieldset {
                field("Minimum number of threads (2^N)") {
                    textfield(inputData.minThreads) {
                        required()
                    }
                }
                field("Maximum number of threads (2^N)") {
                    textfield(inputData.maxThreads) {
                        required()
                    }
                }
                field("Array size") {
                    textfield(inputData.arraySize) {
                        required()
                    }
                }
                field("Step for array size") {
                    textfield(inputData.stepSize) {
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
            getMethodsList(inputData.minThreads.value, inputData.maxThreads.value).forEach {
                series(it.name) {
                    Statistics(it).getStatistics(
                        inputData.arraySize.value,
                        inputData.stepSize.value,
                        inputData.averageStep.value
                    ).forEach { data(it.first, it.second) }
                }
            }
        }
    }
}

class ChartApp : App(InputView::class)
