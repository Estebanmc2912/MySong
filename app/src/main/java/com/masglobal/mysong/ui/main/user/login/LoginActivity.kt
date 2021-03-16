package com.masglobal.mysong.ui.main.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.masglobal.mysong.R
import com.masglobal.mysong.app.database.UserDatabase
import com.masglobal.mysong.ui.main.MainActivity
import com.masglobal.mysong.ui.main.register.RegisterActivity
import com.masglobal.mysong.ui.main.user.UserOptions
import com.masglobal.mysong.ui.main.utils.UtilsPopUps
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity: AppCompatActivity()  {

    var context : Context = this

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

                UtilsPopUps.charging(this)



                var userDatabase = UserDatabase.getInstance(this)
                val userDao = userDatabase.userDao()

                val thread = Thread(Runnable(){
                    kotlin.run {
                        Looper.prepare()
                        var userEntity = userDao.loginUser(et_login_user.text.toString(),et_login_pass.text.toString())
                        if(userEntity == null){
                            Toast.makeText(this, "Invalid Credentials!",Toast.LENGTH_LONG).show()
                        }else{
                           Timer().schedule(object : TimerTask() {
                                override fun run() {
                                    Looper.prepare()
                                    Toast.makeText(context, "Login Succesful!",Toast.LENGTH_LONG).show()
                                    UserOptions.isLogged = true
                                    UserOptions.userId = userEntity.userId
                                    UserOptions.id = userEntity.id
                                    startActivity(Intent(context, MainActivity::class.java))
                                    (context as Activity).finish()
                                    Looper.loop()
                                }
                            }, 4000)

                        }
                        try {
                            Thread.sleep(10)
                        }catch (e : InterruptedException){
                            e.printStackTrace()
                        }
                        Looper.loop()
                    }
                })
                thread!!.start()

            }


        }

        cl_login_signup.setOnClickListener {
            UserOptions.isLogged = true
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

    }
}