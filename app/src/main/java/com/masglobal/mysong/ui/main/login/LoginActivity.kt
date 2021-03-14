package com.masglobal.mysong.ui.main.login

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.masglobal.mysong.R
import com.masglobal.mysong.ui.main.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setListeners()
    }

    private fun setListeners() {
        btn_login_next.setOnClickListener {

            if (et_login_pass.text.isEmpty() && et_login_user.text.isEmpty()) {
                Toast.makeText(
                    this@LoginActivity,
                    "Username & Password are empty",
                    Toast.LENGTH_LONG
                ).show()
            } else if (et_login_user.text.isEmpty() && !et_login_pass.text.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Username is empty", Toast.LENGTH_LONG).show()
            } else if (et_login_user.text.isEmpty() && !et_login_pass.text.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Password is empty", Toast.LENGTH_LONG).show()
            }

            if (!et_login_user.text.isBlank() && !et_login_pass.text.isBlank()) {


            }


        }

        cl_login_signup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

    }
}