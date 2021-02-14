package com.masglobal.mysong.app.connection

import com.masglobal.mysong.ui.main.entities.SongResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("search")
    fun getSongDetails(@Query("term") songName: String?,
                        @Query("limit") limit : String?)
    : Call<SongResponse?>?

}