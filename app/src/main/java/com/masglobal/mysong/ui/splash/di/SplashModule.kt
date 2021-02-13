package com.masglobal.mysong.ui.splash.di

import com.masglobal.mysong.app.di.ActivityScope
import com.masglobal.mysong.ui.splash.SplashActivity
import com.masglobal.mysong.ui.splash.SplashContract
import com.masglobal.mysong.ui.splash.SplashPresenter
import com.masglobal.mysong.ui.splash.SplashRouter
import dagger.Module
import dagger.Provides

@Module
class SplashModule {

@Provides
@ActivityScope
fun router(activity : SplashActivity) : SplashContract.Router = SplashRouter(activity)

@Provides
@ActivityScope
fun presenter(router: SplashContract.Router) = SplashPresenter(router)

}