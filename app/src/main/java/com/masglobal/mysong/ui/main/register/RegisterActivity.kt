package com.masglobal.mysong.ui.main.register

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.masglobal.mysong.R
import kotlinx.android.synthetic.main.activity_login.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setListeners()
    }

    private fun setListeners() {
        tv_login_title.text = "REGISTER"
        cl_login_signup.visibility = View.GONE
        iv_login_icon.background = ContextCompat.getDrawable(this, R.drawable.ic_user)
    }

    override fun onBackPressed() {
        this.finish()
    }
}