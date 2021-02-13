package com.masglobal.mysong.app.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        private const val SERVER_URL = "https:"
    }

    @Provides
    @Singleton
    fun retrofit() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(SERVER_URL)
        .client(
            OkHttpClient.Builder()
                .build()
        )
        .build()
}