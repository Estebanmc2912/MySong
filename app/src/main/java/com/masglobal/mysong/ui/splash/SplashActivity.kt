package com.masglobal.mysong.ui.splash

import android.os.Bundle
import android.os.Handler
import com.masglobal.mysong.R
import com.masglobal.mysong.app.App
import com.masglobal.mysong.ui.common.BaseActivity
import com.masglobal.mysong.ui.splash.di.DaggerSplashComponent
import com.masglobal.mysong.ui.splash.di.SplashComponent
import com.masglobal.mysong.ui.splash.di.SplashModule
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_splashscreen.*

class SplashActivity : BaseActivity<SplashContract.View, SplashPresenter>(), SplashContract.View {

    @Inject
    override lateinit var presenter: SplashPresenter

    val component: SplashComponent by lazy {
        DaggerSplashComponent.builder()
            .appComponent((application as App).component)
            .activity(this)
            .plus(SplashModule())
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        component.inject(this)
        presenter.bindView(this)
        presenter.onViewCreated()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unbindView()
    }

    override fun packageName():String{return packageName}


}