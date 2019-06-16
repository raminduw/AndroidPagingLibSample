package raminduweeraman.com.androidpaging.lib.sample.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
        @SerializedName("page") val page : Int,
        @SerializedName("pageSize") val pageSize : Int,
        @SerializedName("totalPageCount") val totalPageCount : Int,
        @SerializedName("wkda") val wkdaMap : Map<String,String>

)
