package com.masglobal.mysong.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.masglobal.mysong.R
import com.masglobal.mysong.app.database.UserDatabase
import com.masglobal.mysong.app.database.dao.UserDao
import com.masglobal.mysong.ui.main.user.edit.EditActivity
import com.masglobal.mysong.app.database.entitiy.SongEntity
import com.masglobal.mysong.app.database.entitiy.UserEntity
import com.masglobal.mysong.ui.main.favourite.FavouriteActivity
import com.masglobal.mysong.ui.main.home.admin.AdminFragment
import com.masglobal.mysong.ui.main.user.login.LoginActivity
import com.masglobal.mysong.ui.main.home.search.SearchFragment
import com.masglobal.mysong.ui.main.home.tophits.TopHitsFragment
import com.masglobal.mysong.ui.main.user.UserOptions
import com.masglobal.mysong.ui.main.user.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), IMainActivity {

    var context : Context = this
    lateinit var dao : UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dao = UserDatabase.getInstance(applicationContext).userDao()
        val searchFragment = SearchFragment()
        val topHitsFragment = TopHitsFragment()
        val adminFragment = AdminFragment()

        makeCurrentFragment(searchFragment)


        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_search -> makeCurrentFragment(searchFragment)
                R.id.ic_tophits -> makeCurrentFragment(topHitsFragment)
                R.id.ic_users -> makeCurrentFragment(adminFragment)
            }
            true
        }

        addAdmin()
        setUser()

    }

    private fun addAdmin() {

        val thread = Thread(Runnable() {
            kotlin.run {
                Looper.prepare()
                dao.registerUser(UserEntity(0,"admin_user","qwerty123$","","admin"))
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

    private fun setListenersNoLog() {
        iv_main_user.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        iv_main_favourites.setOnClickListener {
            Toast.makeText(this@MainActivity,"To see your favourites, please login", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListenersLog() {
        iv_main_user.setOnClickListener {
            startActivity(Intent(this@MainActivity, EditActivity::class.java))

        }

        iv_main_favourites.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavouriteActivity::class.java))
        }

        if(UserOptions.role == "admin"){
            bottom_navigation.menu.getItem(2).isVisible = true
        }

    }


    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_main, fragment)
            commit()
        }

    private fun setUser() {
        if(UserOptions.isLogged && (UserOptions.userId != "-1")){ chargeLogOptions() }
        else{ chargeNoLogOptions() }

    }

    private fun chargeNoLogOptions() {
        setListenersNoLog()
    }

    private fun chargeLogOptions() {
        setListenersLog()
        chargeProfilePicture()
    }


    private fun chargeProfilePicture() {

        var userDatabase = UserDatabase.getInstance(this)
        val userDao = userDatabase.userDao()
        val thread = Thread(Runnable() {
            kotlin.run {
                Looper.prepare()
                var userEntity = userDao.searchUser(UserOptions.userId)
                if (userEntity == null) {
                    Toast.makeText(this, "Login Corrupt", Toast.LENGTH_LONG).show()
                } else {

                    runOnUiThread {
                        if (userEntity.image.isNotEmpty()) {
                            iv_main_user.setImageURI(Uri.parse(userEntity.image))
                            iv_main_user.clipToOutline = true
                            iv_main_user.strokeWidth = 10f
                            iv_main_user.invalidate()
                        }

                    }
                    //iv_main_user.setImageURI(Uri.parse(userEntity.image))
                    iv_main_user.clipToOutline = true
                    iv_main_user.strokeWidth = 10f
                }
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

    override fun onClickAddSongFavourite(songEntity: SongEntity) {
        val thread = Thread(Runnable() {
            kotlin.run {
                Looper.prepare()
                songEntity.userId = UserOptions.userId
                dao.insertSong(songEntity)

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

    override fun onCLickDelSongFavourite(songEntity: SongEntity) {

    }

    override fun onCLickAddUserAdmin() {
        startActivity(Intent(this@MainActivity, RegisterActivity::class.java).putExtra("admin","admin"))
    }

    override fun onBackPressed() {
        this.finish()
    }
}