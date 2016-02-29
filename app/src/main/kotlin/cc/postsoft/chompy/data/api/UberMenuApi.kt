package cc.postsoft.chompy.data.api

import cc.postsoft.chompy.data.api.model.ListUberMenuRequest
import cc.postsoft.chompy.data.api.model.ListUberMenuResponse
import retrofit2.adapter.rxjava.Result
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UberMenuApi {
    @POST("menus.get")
    fun listUberMenu(@Body request: ListUberMenuRequest): Observable<Result<ListUberMenuResponse>>
}

