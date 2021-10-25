package test4

import javafx.application.Application
import tornadofx.App

class SearchApp : App(Search::class)

fun main() {
    Application.launch(SearchApp::class.java)
}
