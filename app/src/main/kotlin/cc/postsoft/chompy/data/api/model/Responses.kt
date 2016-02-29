package cc.postsoft.chompy.data.api.model


open class ListMenuResponse {
    open val results: List<MenuItem>? = null
}

class ListUberMenuResponse : ListMenuResponse() {
    override val results: List<MenuItem>?
        get() = menu?.get(0)?.meals

    val menu: List<Menu>? = null

    data class Menu(
            val date: String? = null,
            val meals: List<MenuItem>? = null
    )
}
