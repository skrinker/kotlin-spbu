
import homework6.Statistics
import homework6.getMethodsList
import javafx.scene.chart.NumberAxis
import tornadofx.App
import tornadofx.View
import tornadofx.data
import tornadofx.linechart
import tornadofx.series
import tornadofx.vbox

class MyView() : View() {
    var maxArraySize = 0
    var offset = 0
    override val root = vbox {
        linechart("Multi-threaded merge sort algorithm", NumberAxis(), NumberAxis()) {
            getMethodsList().forEach {
                series(it.name) {
                    Statistics(it).getStatistics(100, 3).forEach { data(it.first, it.second) }
                }
            }
        }
    }
}

class ChartApp : App(MyView::class)
