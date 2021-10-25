package test4.Response

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("query")
    val query: String,

    @SerializedName("number_of_results")
    val numberOfResults: Int,

    @SerializedName("results")
    val results: List<Results>,

    @SerializedName("answers")
    val answers: List<String>,

    @SerializedName("corrections")
    val corrections: List<String>,

    @SerializedName("infoboxes")
    val infoboxes: List<Infoboxes>,

    @SerializedName("suggestions")
    val suggestions: List<String>,

    @SerializedName("unresponsive_engines")
    val unresponsiveEngines: List<String>
)
