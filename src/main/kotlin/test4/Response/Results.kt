package test4.Response

import com.google.gson.annotations.SerializedName

data class Results(

    @SerializedName("url")
    val url: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("engine")
    val engine: String,

    @SerializedName("parsed_url")
    val parsedUrl: List<String>,

    @SerializedName("engines")
    val engines: List<String>,

    @SerializedName("positions")
    val positions: List<Int>,

    @SerializedName("score")
    val score: Double,

    @SerializedName("category")
    val category: String,

    @SerializedName("pretty_url")
    val prettyUrl: String
)
