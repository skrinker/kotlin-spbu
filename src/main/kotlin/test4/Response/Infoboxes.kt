package test4.Response

import com.google.gson.annotations.SerializedName

data class Infoboxes (
    @SerializedName("infobox") val infobox : String,
    @SerializedName("id") val id : String,
    @SerializedName("content") val content : String,
    @SerializedName("img_src") val img_src : String,
    @SerializedName("urls") val urls : List<Urls>,
    @SerializedName("attributes") val attributes : List<String>,
    @SerializedName("engine") val engine : String,
    @SerializedName("engines") val engines : List<String>
)
