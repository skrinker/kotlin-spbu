package test2

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL

private fun getWeather(cityName: String, token: String): String {
    val url: URL = URL("https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$token&units=metric")
    val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

    conn.connect()
    val reader = conn.inputStream.reader()
    var content: String
    reader.use {
        content = it.readText()
    }

    return content
}

fun main() = runBlocking {
    val cityList = mutableListOf<String>("Moscow", "London", "Kazan")
    val config = Config.parseJson("config.json")

    cityList.forEach() {
        async {
            val result = Json.decodeFromString(WeatherResponse.serializer(), getWeather(it, config.token))
            println("${result.name} ${result.main.temp}")
        }
    }
}
