package cc.femto.chompy.data.api

import cc.femto.chompy.data.api.model.ListMenuResponse
import retrofit2.adapter.rxjava.Result
import retrofit2.http.GET
import rx.Observable

interface MenuService {

    companion object {
        const val API_KEY = "e0c8754b089142e7bb79d83fbca2c9505e283c032290b83e7ea93d655f4f1a7b23319320595b68308ade13e3213536281196671a6c2411e2dffe1a874a428fc45ca179051d7a8c5eca5fed135d9d59c8"
        const val INPUT_SPRIG = "0bc14123-fc06-49f9-bd0e-02c07d25e13b/_query?input=webpage/url:http%3A%2F%2Frender.import.io%2F%3Furl%3Dhttps%3A%2F%2Fwww.sprig.com%2F%23%2F"
        const val INPUT_CAVIAR = "fc7b7854-f591-44f7-b550-b0e3fe737d96/_query?input=webpage/url:https%3A%2F%2Fwww.trycaviar.com%2Ffastbite%2Flanding"
    }

    @GET("$INPUT_SPRIG&&_apikey=$API_KEY")
    fun listSprigMenu(): Observable<Result<ListMenuResponse>>

    @GET("$INPUT_CAVIAR&&_apikey=$API_KEY")
    fun listCaviarMenu(): Observable<Result<ListMenuResponse>>
}

