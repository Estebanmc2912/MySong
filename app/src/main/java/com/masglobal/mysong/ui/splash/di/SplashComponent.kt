package com.masglobal.mysong.ui.splash.di

import com.masglobal.mysong.app.di.ActivityScope
import com.masglobal.mysong.app.di.AppComponent
import com.masglobal.mysong.ui.splash.SplashActivity
import dagger.BindsInstance
import dagger.Component

@ActivityScope
@Component(modules = [SplashModule::class], dependencies = [AppComponent::class])
interface SplashComponent {

    fun inject(target: SplashActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: SplashActivity): Builder

        fun appComponent(component: AppComponent): Builder

        fun plus(module: SplashModule): Builder

        fun build(): SplashComponent
    }

}