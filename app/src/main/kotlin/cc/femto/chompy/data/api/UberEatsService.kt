package cc.femto.chompy.data.api

import cc.femto.chompy.data.api.model.ListUberMenuRequest
import cc.femto.chompy.data.api.model.ListUberMenuResponse
import retrofit2.adapter.rxjava.Result
import retrofit2.http.Body
import retrofit2.http.POST
import rx.Observable

interface UberEatsService {
    @POST("menus.get")
    fun listUberMenu(@Body request: ListUberMenuRequest): Observable<Result<ListUberMenuResponse>>
}

