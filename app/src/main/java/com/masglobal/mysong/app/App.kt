package com.masglobal.mysong.app

import android.app.Application
import com.masglobal.mysong.app.di.AppComponent
import com.masglobal.mysong.app.di.AppModule
import com.masglobal.mysong.app.di.DaggerAppComponent

class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .context(this)
            .plus(AppModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}
