package com.masglobal.mysong.ui.splash

import android.os.Handler
import com.masglobal.mysong.R
import com.masglobal.mysong.ui.common.BasePresenter
import com.masglobal.mysong.ui.splash.SplashContract

class SplashPresenter(private val router: SplashContract.Router) : BasePresenter<SplashContract.View>(), SplashContract.Presenter {

    companion object {
        private const val SPLASH_DISPLAY_TIME : Long = 2000
    }

    override var view: SplashContract.View? = null

    override fun bindView(view: SplashContract.View) {
        this.view = view
    }

    override fun unbindView() {
        view = null
    }

    override fun onViewCreated() {

        Handler().postDelayed({
            router.openMain()
        }, SPLASH_DISPLAY_TIME)

    }


}