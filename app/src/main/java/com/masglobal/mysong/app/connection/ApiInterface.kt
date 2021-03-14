package com.masglobal.mysong.app.connection

import com.masglobal.mysong.ui.main.entities.FeedResponse
import com.masglobal.mysong.ui.main.entities.SongResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiInterface {

    @GET("search")
    fun getSongDetails(@Query("term") songName: String?,
                        @Query("limit") limit : String?)
    : Call<SongResponse?>?


    @GET("api/v1/us/itunes-music/top-songs/all/{limit}/explicit.json")
    fun getTopHits(@Path("limit") limit: String?) : Call<FeedResponse?>?
    //https://rss.itunes.apple.com/api/v1/us/itunes-music/top-songs/all/10/explicit.json

}