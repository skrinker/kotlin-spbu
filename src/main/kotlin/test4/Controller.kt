package test4

import Response
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL
import javafx.collections.ObservableList
import tornadofx.runAsync

class Controller {
    fun getResults(input: String, links: ObservableList<String>): String {
        val url = URL("https://searx.roughs.ru/search?q=$input&format=json")
        val gson = Gson()
        val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

        runAsync {
            conn.connect()
        }
        val reader = conn.inputStream.reader()
        var results: String
        reader.use {
            results = it.readText()
        }

        val buffer = gson.fromJson(results, Response::class.java)

        buffer.results.forEach {
            links.add(it.url)
        }

        return results
    }
}