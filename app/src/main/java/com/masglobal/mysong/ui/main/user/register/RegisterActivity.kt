package com.masglobal.mysong.ui.main.register

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.masglobal.mysong.R
import com.masglobal.mysong.app.database.UserDatabase
import com.masglobal.mysong.app.database.entitiy.UserEntity
import com.masglobal.mysong.ui.main.login.LoginActivity
import com.masglobal.mysong.ui.main.utils.UtilsPopUps
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*


class RegisterActivity : AppCompatActivity() {

    var context : Context = this
    private val pickImage = 100
    private var imageUri = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setListeners()
    }

    private fun setListeners() {
        tv_login_title.text = "REGISTER"
        cl_login_signup.visibility = View.GONE
        iv_login_icon.background = ContextCompat.getDrawable(this, R.drawable.ic_user)

        iv_login_icon.setOnClickListener {
            val gallery = Intent(Intent.ACTION_OPEN_DOCUMENT)
            gallery.setType("image/*")
            startActivityForResult(gallery, pickImage)
        }


        btn_login_next.setOnClickListener {

            if (et_login_pass.text.isEmpty() && et_login_user.text.isEmpty()) {
                Toast.makeText(
                    this@RegisterActivity,
                    "Username & Password are empty",
                    Toast.LENGTH_LONG
                ).show()
            } else if (et_login_user.text.isEmpty() && !et_login_pass.text.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Username is empty", Toast.LENGTH_LONG).show()
            } else if (et_login_user.text.isEmpty() && !et_login_pass.text.isEmpty()) {
                Toast.makeText(this@RegisterActivity, "Password is empty", Toast.LENGTH_LONG).show()
            }

            if (!et_login_user.text.isBlank() && !et_login_pass.text.isBlank()) {

                UtilsPopUps.charging(this)


                var userEntity = UserEntity(
                    0,
                    et_login_user.text.toString(),
                    et_login_pass.text.toString(),
                    imageUri,
                )

                var userDatabase = UserDatabase.getInstance(applicationContext)
                var userDao = userDatabase.userDao()
                val thread = Thread(Runnable() {
                    kotlin.run {
                        Looper.prepare()
                        userDao.registerUser(userEntity)
                        Timer().schedule(object : TimerTask() {
                            override fun run() {
                                Looper.prepare()
                                Toast.makeText(context, "User Registered!", Toast.LENGTH_LONG)
                                    .show()
                                startActivity(Intent(context, LoginActivity::class.java))
                                (context as Activity).finish()
                                Looper.loop()
                            }
                        }, 4000)

                        try {
                            Thread.sleep(10)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                        Looper.loop()
                    }
                })
                thread!!.start()

            }

        }

    }

    fun validateInput(userEntity: UserEntity) : Boolean{
    return true
    }

    override fun onBackPressed() {
        this.finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {

            imageUri = ((data?.data)).toString()
            iv_login_icon.setImageURI(Uri.parse(imageUri))
            iv_login_icon.clipToOutline = true
            iv_login_icon.strokeWidth = 10f
        }
    }
}