package com.masglobal.mysong.app.di.connection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private var retrofit: Retrofit? = null


    public fun getClient():Retrofit{
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ApiEndpoint.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

}