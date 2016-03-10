package cc.femto.chompy.data.api.model

import com.squareup.moshi.Json

data class MenuItem(
        val dish: String? = null,
        val restaurant: String? = null,
        val description: String? = null,
        @Json(name = "image_url") val imageUrl: String? = null,
        val price: String? = null
)
