package com.masglobal.mysong.ui.splash

import android.content.Intent
import com.masglobal.mysong.ui.main.MainActivity

class SplashRouter(private val activity: SplashActivity) : SplashContract.Router {

    override fun openMain() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
        activity.finish()

        //MainActivity.launch(activity)
        //MainActivity.launch(activity)
        //activity.finish()
    }
}