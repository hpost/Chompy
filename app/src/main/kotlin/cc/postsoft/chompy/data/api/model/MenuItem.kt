package cc.postsoft.chompy.data.api.model

import com.squareup.moshi.Json

data class MenuItem(
        var dish: String? = null,
        var description: String? = null,
        @Json(name = "image_url") var imageUrl: String? = null
)


