package com.masglobal.mysong.ui.splash

import com.masglobal.mysong.ui.common.BaseView

interface SplashContract {
    interface View : BaseView {
        fun packageName():String
    }

    interface Presenter {
        fun bindView(view: View)

        fun unbindView()

        fun onViewCreated()
    }

    interface Router {
        fun openMain()
    }
}